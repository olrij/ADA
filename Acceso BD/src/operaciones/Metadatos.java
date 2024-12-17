package operaciones;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Metadatos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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

}
