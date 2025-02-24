package oracle;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoVarray {
	public static void main(String[] args) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "select * from AGENDA";
			ResultSet resul = sentencia.executeQuery(sql);
			while (resul.next()) {
				String NOMBRE = resul.getString(1);
				System.out.print("Nombre: " + NOMBRE + " => ");
				// Obtengo el array de los telefonos
				try {
					Array objeto = (Array) resul.getObject(2);
					Object[] telefonos = (Object[]) objeto.getArray();
					if (telefonos.length == 0)
						System.out.printf("NO TIENE NINGÚN TELÉFONO EN EL ARRAY \n");
					else {
						for (int i = 0; i < telefonos.length; i++) {
							try {
								System.out.printf("%s ", telefonos[i].toString());
							} catch (java.lang.NullPointerException n) {  
								System.out.print("nulo ");
							}
						}
						System.out.println();
					}
				} catch (java.lang.NullPointerException n) {
					System.out.printf("NO TIENE TELEFONOS - ARRAY NULL \n");
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
