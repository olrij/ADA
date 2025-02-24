package oracle;

import java.sql.Array;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;



public class AccesoTablaAnidada {
	public static void main(String[] args) {

		consulta4();
		//consultaTodoMalaga();		
		//consultaProyeccionTotal("select * from EJEMPLO_TABLA_ANIDADA");
	}
	
	public static void insertar() {
		// Insert

		String sql1="INSERT INTO GRUPOS2 VALUES(1,'INFORMÁTICA','21/01/2020',GRUPOS_T(PERSONA(1, 'Juan', Direccion('c/ Conde','Málaga',12001),'17/02/1990'),PERSONA(2, 'María', Direccion('c/ Amiel','Córdoba',16001),'21/10/1991'),PERSONA(3, 'Sofía', Direccion('c/ Castillo','Cádiz',15001),'1/09/1992'),PERSONA(4, 'Carla', Direccion('c/ Sin nombre','Cádiz',11001),'14/08/1990'),PERSONA(5, 'Manuel', Direccion('c/ Cuba','Málaga',10001),'27/02/1989')))";
		String sql2="INSERT INTO GRUPOS2 VALUES(2,'ADMINISTRACIÓN','2/02/2020',GRUPOS_T(PERSONA(6, 'Lucas', DirecciOn('c/ España','Granada',11001),'29/01/1988'),PERSONA(7, 'Marta', DirecciOn('c/ Conde','Málaga',12001),'30/07/1986'),PERSONA(8, 'Carmen', DirecciOn('c/ Mimbre','Granada',11001),'1/04/1990'),PERSONA(9, 'Milagros', DirecciOn('c/ Segura','Sevilla',16001),'7/01/1994')))";	
		String sql3="INSERT INTO GRUPOS2 VALUES(3,'DIRECCIÓN','10/03/2020',GRUPOS_T(PERSONA(10, 'José Miguel', DirecciOn('c/ Volga','Sevilla',15001),'5/12/1979'),PERSONA(11, 'Antonia', DirecciOn('c/ Tajo','Málaga',15001),'17/05/1980')))";
		
		
		operacionDML(sql1);
		operacionDML(sql2);
		operacionDML(sql3);
	}
	
	public static void operacionDML(String sql) {
		try {

			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system",
					"1234");
			conexion.setAutoCommit(true);
			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			int result = sentencia.executeUpdate(sql);


			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (SQLException e) {
			e.printStackTrace(); // Muestra el error exacto
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("ErrorCode: " + e.getErrorCode());
		}
		
	}
	
	
	public static void consultaTodoMalaga() {
		try {
			// Oracle
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			String sql="SELECT * FROM GRUPOS2 GR, TABLE(PERSONA_ANIDADA) P WHERE P.DIREC.CIUDAD='Málaga'";
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				int ID = resul.getInt(1);
				String NOMBRE = resul.getString(2);
				Date fecha = resul.getDate(3);
				System.out.println("ID: " + ID + ", Apellidos: " + NOMBRE + "FECHA: "+ fecha+" => ");
				// extraer columna DIREC TABLA_ANIDADA()
				try {
					
					Array personas = (Array) resul.getObject(4);
					Object[] personasAtributos = (Object[]) personas.getArray();
					if (personasAtributos.length == 0)
						System.out.printf("\tNO TIENE NINGUNA Persona - TABLA ANIDADA VACIA \n");
					else {
						for (int i = 0; i < personasAtributos.length; i++) {
							try {
								Struct persona = (Struct) personasAtributos[i];
								// Saco sus atributos CALLE, CIUDAD, //CODIGO_POST
								Object[] atributos = persona.getAttributes();
								
								java.math.BigDecimal cod=(java.math.BigDecimal ) atributos[0];
								String nombre = (String) atributos[1];
								
								Struct direccion = (Struct) atributos[2];
								
								Object[] atributos2 = direccion.getAttributes();
								String calle = (String) atributos2[0];
								String ciudad = (String) atributos2[1];
								java.math.BigDecimal codigo_post = (java.math.BigDecimal) atributos2[2];
								
								Timestamp fechaNac = (Timestamp) atributos[3];
								
								System.out.printf(cod.intValue()+" "+nombre+" "+" "+ciudad+" "+codigo_post.intValue()+" "+fechaNac+"\n");
								
							} catch (java.lang.NullPointerException n) {
								System.out.print("nula ");
							}
						}
						System.out.println();
					}
				} catch (java.lang.NullPointerException n) {
					System.out.printf("\tNO TIENE DIRECCIONES - TABLA ANIDADA NULL \n");
				}
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	// Muestras el grupo al que pertenecen y el nombre de las personas que han nacido antes de 1990.
	public static void consulta2() {
		try {
			// Oracle
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			String sql="SELECT GR.ID,P.NOMBRE,P.FECHA_NAC FROM GRUPOS2 GR, TABLE(PERSONA_ANIDADA) P WHERE P.FECHA_NAC<'1/1/1990'";
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				int ID = resul.getInt(1);
				String NOMBRE = resul.getString(2);
				Timestamp fechaNac = (Timestamp) resul.getTimestamp(3);
				Date fecha = resul.getDate(3);
				System.out.println(ID+" "+NOMBRE+" "+fechaNac+"\n");
				// extraer columna DIREC TABLA_ANIDADA()
				
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}	
	
	// Muestra la dirección de los empleados que pertenecen al grupo DIRECCIÓN.

	public static void consulta3() {
		try {
			// Oracle
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			String sql="SELECT P.DIREC.CALLE,P.DIREC.CIUDAD,P.DIREC.CODIGO_POST FROM GRUPOS2 GR, TABLE(PERSONA_ANIDADA) P WHERE GR.NOMBRE='DIRECCIÓN'";
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				String calle= resul.getString(1);
				String ciudad = resul.getString(2);
				java.math.BigDecimal codigo_post = (java.math.BigDecimal) resul.getBigDecimal(3);			
				System.out.println(calle+" "+ciudad+" "+codigo_post+" ");
				
				
				
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	
	// Muestra el nombre y la fecha de nacimiento de las personas que pertenezcan a un grupo creado antes de marzo de 2020.
	
	public static void consulta4() {
		try {
			// Oracle
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			String sql="SELECT GR.ID,P.NOMBRE,P.FECHA_NAC FROM GRUPOS2 GR, TABLE(PERSONA_ANIDADA) P WHERE GR.FECHA_CREACION<'1/3/2020'";
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				int ID = resul.getInt(1);
				String NOMBRE = resul.getString(2);
				Timestamp fechaNac = (Timestamp) resul.getTimestamp(3);
				System.out.println(ID+" "+NOMBRE+" "+fechaNac+"\n");
				// extraer columna DIREC TABLA_ANIDADA()
				
			}
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	
}

