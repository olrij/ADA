package operaciones;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pruebas {

	public static void main(String[] args) {

		// consultaNormal("SELECT * FROM cliente");
		//operacionDML("INSERT INTO pedido VALUES(18, 2389.23, '2019-03-11', 1, 5)");
		//clientesPorCiudad("Sevilla");
		//consultarTodoComercialesPA("CALL todoComercial();");
		//insertarClienteYPedidos();
		consultarMetadatos();

	}
	
	public static void consultarMetadatos() {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			
			DatabaseMetaData meta = conexion.getMetaData();
			
			//Información sobre la BD
			System.out.println("SGBD: "+meta.getDatabaseProductName()+" versión:"+meta.getDatabaseProductVersion() );
			System.out.println("url: "+meta.getURL());
			System.out.println("Driver: "+meta.getDriverName()+" versión: "+meta.getDriverVersion());
			
			System.out.println();
			//Información sobre las tablas y columnas
			ResultSet rs = meta.getTables("ventas", null, null, new String[] {"TABLE"});
			
			
			while(rs.next()) {
				String nombreTabla=rs.getString("TABLE_NAME");
				System.out.println(nombreTabla);
				ResultSet rs2 = meta.getColumns("ventas", null, nombreTabla, null);
				System.out.println("Columnas: ");
				while(rs2.next()) {
					System.out.println("\tNombre columna: "+rs2.getString("COLUMN_NAME")+" tipo: "+rs2.getString("TYPE_NAME")+" Tamaño: "+rs2.getString("COLUMN_SIZE"));
				}
				rs2.close();
				System.out.println();
				
			}
			rs.close();
			
			conexion.close();
			
			
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertarClienteYPedidos() {
		Connection conexion=null;
		try {
			// 1. Abrir conexión
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			// Cambiar el valor de autocommit
			conexion.setAutoCommit(false);
			// 2. Realizar operación (Consulta)
			Statement st = conexion.createStatement();

			boolean result = st.execute("INSERT INTO cliente VALUES(11, 'Pepe', 'Santana', 'Loyola', 'Sevilla', 125);");
			result=st.execute("INSERT INTO pedido VALUES(16, 2389.23, '2019-03-11', 1, 5);");

			// 3. Manipular los resultados


			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			//Hacer el rollback
			try {
				conexion.rollback();
				System.out.println("Rollback realizado");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

	private static void consultarTodoComercialesPA(String sql) {
		try {
			// 1. Abrir conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			// 2. Realizar operación (Consulta)
			CallableStatement cs = conexion.prepareCall(sql);

			//Preparar la operación 
			//...
			

			ResultSet result = cs.executeQuery();

			// 3. Manipular los resultados

			while (result.next()) {
				System.out.print(result.getString("id") + " ");
				System.out.print(result.getString("nombre") + " ");
				System.out.print(result.getString("apellido1") + " ");
				System.out.println();

			}
			// cerrar ResultSet
			result.close();

			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void consultaNormal(String consulta) {

		try {
			// 1. Abrir conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			// 2. Realizar operación (Consulta)
			Statement st = conexion.createStatement();

			ResultSet result = st.executeQuery(consulta);

			// 3. Manipular los resultados

			while (result.next()) {
				System.out.print(result.getString("id") + " ");
				System.out.print(result.getString("nombre") + " ");
				System.out.print(result.getString("apellido1") + " ");
				System.out.println();

			}
			// cerrar ResultSet
			result.close();

			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void operacionDML(String op) {

		try {
			// 1. Abrir conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			// 2. Realizar operación (Consulta)
			Statement st = conexion.createStatement();

			int result = st.executeUpdate(op);

			// 3. Manipular los resultados

			if (result > 0) {
				System.out.println("Se ha realizado la operación correctamente");
			}

			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clientesPorCiudad(String ciudad) {

		try {
			// 1. Abrir conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			// 2. Realizar operación (Consulta)
			String sql="Select * from cliente where ciudad=?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			
			//Preparar consulta
			ps.setString(1, ciudad);

			ResultSet result = ps.executeQuery();

			// 3. Manipular los resultados

			while (result.next()) {
				System.out.print(result.getString("id") + " ");
				System.out.print(result.getString("nombre") + " ");
				System.out.print(result.getString("ciudad") + " ");
				System.out.println();

			}
			// cerrar ResultSet
			result.close();

			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
