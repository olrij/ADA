package operaciones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Manipulacion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		borrar();

	}

	public static void insertar() {

		try {

			// 1. Conexi�n
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operaci�n
			Statement statement = conexion.createStatement();
			String sql1 = "INSERT INTO cliente VALUES (13,'Pepe','S�nchez','S�nchez','Madrid',200)";
			String sql2 = "INSERT INTO cliente VALUES (14,'Pepe','S�nchez','S�nchez','Madrid',200)";

			int result = statement.executeUpdate(sql1);

			if (result > 0) {
				System.out.println("Se ha ejecutado correctamente la inserci�n de " + result + " registros");
			} else {
				System.out.println("No se ha insetado ning�n registro");
			}

			result = statement.executeUpdate(sql2);

			if (result > 0) {
				System.out.println("Se ha ejecutado correctamente la inserci�n de " + result + " registros");
			} else {
				System.out.println("No se ha insertado ning�n registro");
			}

			// 3. Cerrar conexi�n
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void actualizar() {

		try {

			// 1. Conexi�n
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operaci�n
			Statement statement = conexion.createStatement();
			String sql = "UPDATE cliente SET apellido2='P�rez' where apellido2='S�nchez'";

			int result = statement.executeUpdate(sql);

			if (result > 0) {
				System.out.println("Se ha ejecutado correctamente la actualizaci�n de " + result + " registros");
			} else {
				System.out.println("No se ha actualizado ning�n registro");
			}

			// 3. Cerrar conexi�n
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void borrar() {

		try {

			// 1. Conexi�n
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operaci�n
			Statement statement = conexion.createStatement();
			String sql = "DELETE FROM pedido WHERE total>100";

			int result = statement.executeUpdate(sql);

			if (result > 0) {
				System.out.println("Se ha ejecutado correctamente el borrado de " + result + " registros");
			} else {
				System.out.println("No se ha borrado ning�n registro");
			}

			// 3. Cerrar conexi�n
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
