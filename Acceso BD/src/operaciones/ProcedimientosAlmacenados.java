package operaciones;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedimientosAlmacenados {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* create procedure pedidosCliente(idCliente INT)
	SELECT P.id,P.total,P.fecha FROM Cliente AS C, PEDIDO AS P WHERE P.id_cliente=C.id AND C.id=idCliente;
    */
		try {
			Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root",
						"123456789");
			String sql="CALL pedidosCliente(?)";
			CallableStatement cs = c.prepareCall(sql);
			
			cs.setInt(1, 1);
			
			ResultSet rs = cs.executeQuery();
			
			while(rs.next()) {
				System.out.print(rs.getInt(1)+" ");
				System.out.print(rs.getDouble(2)+" ");
				System.out.print(rs.getDate(3));
				System.out.println();
			}
			
			System.out.println("Segunda consulta");
			//Reutilización
			cs.setInt(1, 2);
			
			rs = cs.executeQuery();
			
			while(rs.next()) {
				System.out.print(rs.getInt(1)+" ");
				System.out.print(rs.getDouble(2)+" ");
				System.out.print(rs.getDate(3));
				System.out.println();
			}
			
			//Terminado
			c.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
