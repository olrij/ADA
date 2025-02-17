package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;

public class AccesoTabla {
	public static void main(String[] args) {

		//operacionDML("INSERT INTO ALUMNOS VALUES('95678123Q','Pepe...', DIRECCION('A',1,1), 5000.0)");
		consulta();
	}

	public static void consulta() {
		try {

			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system",
					"1234");
			System.out.println("-----");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "select * from alumnos";
			ResultSet resul = sentencia.executeQuery(sql);

			while (resul.next()) {
				String dni = resul.getString(1);
				String nombre = resul.getString(2);
				double sueldo = resul.getDouble(4);

				// Obtengo el objeto DIRECCION
				Struct objeto = (Struct) resul.getObject(3);

				// Saco sus atributos CALLE, CIUDAD, CODIGO_POST
				Object[] atributos = objeto.getAttributes();
				String calle = (String) atributos[0];
				java.math.BigDecimal numero = (java.math.BigDecimal) atributos[1];
				java.math.BigDecimal codigo_post = (java.math.BigDecimal) atributos[2];

				System.out.printf("dni:%s, Nombre:%s sueldo: %f %n \t Calle: %s, número: %d, CP: %d %n", dni, nombre,
						sueldo, calle, numero.intValue(), codigo_post.intValue());
			}

			resul.close(); // Cerrar ResultSet

			sentencia.close(); // Cerrar Statement

			conexion.close(); // Cerrar conexión
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
