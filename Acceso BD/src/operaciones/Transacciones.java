package operaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Transacciones {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection c=null;
		try {
			c=DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
					"123456789");
			Statement st = c.createStatement();
			
			c.setAutoCommit(false);
			
			st.execute("INSERT INTO cliente VALUES (30,'Nombre','Apellido1','Apellido2','Córdoba',250)");
			st.execute("INSERT INTO cliente VALUES (31,'Nombre','Apellido1','Apellido2','Córdoba',250)");
			st.execute("INSERT INTO cliente VALUES (32,'Nombre','Apellido1','Apellido2','Córdoba',250)");
			st.execute("INSERT INTO cliente VALUES (33,'Nombre','Apellido1','Apellido2','Córdoba',250)");
			
			//Terminamos de definir la operaciones a ejecutar
			c.commit();
			
			//Si queremos volver al modo normal de funcionamiento
			
			c.setAutoCommit(true);
			
			//Terminamos
			c.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			try {
				c.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	

	}

}
