package operaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class ConsultasPreparadas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		consultaPedido();
		
		
	}
	
	
	public static void consultaPedido() {
		try {

			// 1. Conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operación (Consulta)
			String sql = "SELECT * FROM pedido WHERE cantidad>? AND fecha BETWEEN ? AND ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			
			//2.1. Fijar los parámetros
			
		
			ps.setDouble(1, 300);
			ps.setDate(2, Date.valueOf("2019-1-1"));
			ps.setDate(3, Date.valueOf("2019-12-31"));
			

			ResultSet result =ps.executeQuery();

			// 3. Manipular los resultados
			
			
			while(result.next()) {
				System.out.print(result.getInt("id")+" ");
				System.out.print(result.getDouble("cantidad")+" ");
				System.out.print(result.getDate("fecha")+" ");
				System.out.println();
				
			}
			
		
			// 4. Cerrar conexión
			conexion.close();

		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void consultaCliente() {
		try {

			// 1. Conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operación (Consulta)
			String sql = "SELECT * FROM cliente WHERE categoría>?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			
			//2.1. Fijar los parámetros
			
			ps.setInt(1, 100);
			

			ResultSet result =ps.executeQuery();

			// 3. Manipular los resultados
			
			
			while(result.next()) {
				System.out.print(result.getString(1)+" ");
				System.out.print(result.getString(2)+" ");
				System.out.print(result.getString(3)+" ");
				System.out.print(result.getString("categoría"));
				System.out.println();
				
			}
			
			// Reutilizamos la consulta preparada
			
			// Fijamos de nuevo los parámetros
			
			ps.setInt(1, 250);
			
			result=ps.executeQuery();
			
			while(result.next()) {
				System.out.print(result.getInt(1)+" ");
				System.out.print(result.getString("categoría"));
				System.out.println();
			}
			// 4. Cerrar conexión
			conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
