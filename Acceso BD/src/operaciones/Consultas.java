package operaciones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// 1. Conexión
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");

			// 2. Operación (Consulta)
			String sql = "SELECT * FROM cliente WHERE categoría>200";
			Statement st = conexion.createStatement();
			

			ResultSet result =st.executeQuery(sql);

			// 3. Manipular los resultados
			
			
			while(result.next()) {
				System.out.print(result.getString(1)+" ");
				System.out.print(result.getString(2)+" ");
				System.out.print(result.getString(3)+" ");
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
