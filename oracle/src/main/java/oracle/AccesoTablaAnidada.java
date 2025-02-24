package oracle;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;



public class AccesoTablaAnidada {
	public static void main(String[] args) {
		
		operacionDML("INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES(7,'Pérez',TABLA_ANIDADA(DIRECCION('Calle 1',1,10000),DIRECCION('Calle 2',2,20000)))");
		consultaProyeccionTotal("select * from EJEMPLO_TABLA_ANIDADA");
	}
	
	public static void operacionDML(String sql) {
		try {

			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system",
					"1234");
			//conexion.setAutoCommit(true);
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
	
	
	public static void consultaProyeccionTotal(String sql) {
		try {
			// Oracle
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				int ID = resul.getInt(1);
				String APELLIDOS = resul.getString(2);
				System.out.println("ID: " + ID + ", Apellidos: " + APELLIDOS + " => ");
				// extraer columna DIREC TABLA_ANIDADA()
				try {
					Array DIREC = (Array) resul.getObject(3);
					Object[] direcciones = (Object[]) DIREC.getArray();
					if (direcciones.length == 0)
						System.out.printf("\tNO TIENE NINGUNA DIRECCIÓN - TABLA ANIDADA VACIA \n");
					else {
						for (int i = 0; i < direcciones.length; i++) {
							try {
								Struct unadireccion = (Struct) direcciones[i];
								// Saco sus atributos CALLE, CIUDAD, //CODIGO_POST
								Object[] atributos = unadireccion.getAttributes();
								String calle = (String) atributos[0];
								java.math.BigDecimal numero = (java.math.BigDecimal) atributos[1];
								java.math.BigDecimal codigo_post = (java.math.BigDecimal) atributos[2];
								System.out.printf("\t Calle: %s, Ciudad: %s, CP: %d %n", calle, numero.intValue(),
										codigo_post.intValue());
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
	
	
	
	
	
}

