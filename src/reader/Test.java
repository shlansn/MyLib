package reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;





public class Test {

	/**
	 * @param args
	 */
	static String title;
	static String link;
	static String publ;
	//TODO decide if needed
	static String up;
	static String author;
	static String sum;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSetImpl rs = null;
		String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String password = "";

		try {
		     con = DriverManager.getConnection(url, user, password);
		     Statement st = (Statement) con.createStatement(); 

		     int t = st.executeUpdate("INSERT INTO uni.a1 " + "VALUES (29,45)");
		     System.out.println(t + "---");

		     con.close();
		}

		catch (SQLException ex) {
			ex.printStackTrace();

		 } 
		
		
	}
}
