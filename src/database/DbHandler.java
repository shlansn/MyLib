package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import reader.XmlGolemReader;
import reader.XmlHeiseReader;
import reader.XmlSpiegelReader;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DbHandler {

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
	
	static int globalCounter;
	static int globalCounterAfterGolem;
	static int globalCounterAfterSpiegel;
	static int globalCounterAfterHeise;
	static HashMap<String,String> golemMap;
	static HashMap<String,String> heiseMap;
	static HashMap<String,String> spiegelMap;
	
	//Database Variables
	final static String dbName = "rssproject.news";
	
	public static void main(String[] args) {
		
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String password = "";
//
//		try {
//		     con = DriverManager.getConnection(url, user, password);
//		     Statement st = (Statement) con.createStatement(); 
//
//		     int t = st.executeUpdate("INSERT INTO rssproject.news " + "VALUES (29,45)");
//		     System.out.println(t + "---");
//
//		     con.close();
//		}
//
//		catch (SQLException ex) {
//			ex.printStackTrace();
//
//		 }
		XmlGolemReader test = new XmlGolemReader();
		String buf = test.readNewData("");
		String bla = DbHandler.prepareStatementFromGolemMap(test.parseXML(buf));
		System.out.println(bla);
		
		XmlSpiegelReader test2 = new XmlSpiegelReader();
		String buf2 = test2.readNewData("");
		String bla2 = DbHandler.prepareStatementFromSpiegelMap(test2.parseXML(buf2));
		System.out.println(bla2);
		
		XmlHeiseReader test3 = new XmlHeiseReader();
		String buf3 = test3.readNewData("");
		String bla3 = DbHandler.prepareStatementFromHeiseMap(test3.parseXML(buf3));
		System.out.println(bla3);
		
		try {
		     con = DriverManager.getConnection(url, user, password);
		     Statement st = (Statement) con.createStatement(); 

		     int x = st.executeUpdate("DELETE FROM rssproject.news WHERE 1");
		     int t = st.executeUpdate(bla);
		     int s = st.executeUpdate(bla2);
		     int r = st.executeUpdate(bla3);
		     System.out.println("x = " + x);
		     System.out.println("t = " + t);
		     System.out.println("s = " + s);
		     System.out.println("s = " + r);
//		     System.out.println(t + "---");

		     con.close();
		}

		catch (SQLException ex) {
			ex.printStackTrace();

		 }
		
		
	}
	
	public static String prepareStatementFromGolemMap(HashMap<String,String> Map) {
		String sqlString = "INSERT INTO " + dbName + " VALUES (";
		golemMap = Map;
		globalCounter = golemMap.size();
		
		//get golem String
		for (int i = 1; i <= globalCounter; i++) {
			
			if ((!(golemMap.get("title-golem-"+i) == null)) && (!(golemMap.get("link-golem-"+i) == null)) &&
				(!golemMap.get("publ-golem-"+i).equals(null)) && (!golemMap.get("up-golem-"+i).equals(null)) &&
				(!golemMap.get("author-golem-"+i).equals(null)) && (!golemMap.get("sum-golem-"+i).equals(null))) {
				
				if (i == 1) {
					sqlString +=    i + "  , \"" + golemMap.get("title-golem-"+i) + "\" , \"" + golemMap.get("link-golem-"+i) +
									"\" , \"" + golemMap.get("publ-golem-"+i) + "\" , \"" + golemMap.get("up-golem-"+i) +
									"\" , \"" + golemMap.get("author-golem-"+i) + "\" , \"" + golemMap.get("sum-golem-"+i) +
									"\" , \"Golem\" ) ";
									globalCounterAfterGolem = i + 1;
				}
				else {
					sqlString +=	" , ( " + i + " , \"" + golemMap.get("title-golem-"+i) + "\" , \"" + golemMap.get("link-golem-"+i) +
									"\" , \"" + golemMap.get("publ-golem-"+i) + "\" , \"" + golemMap.get("up-golem-"+i) +
									"\" , \"" + golemMap.get("author-golem-"+i) + "\" , \"" + golemMap.get("sum-golem-"+i) +
									"\" , \"Golem\" ) ";
									globalCounterAfterGolem = i + 1;
				}
			}
		}
		
		sqlString +="; ";
		
		return sqlString;
	}
	
	public static String prepareStatementFromSpiegelMap(HashMap<String,String> Map) {
		String sqlString = "INSERT INTO " + dbName + " VALUES (";
		spiegelMap = Map;
		globalCounter = spiegelMap.size();
		
		//get golem String
		for (int i = 1; i <= globalCounter; i++) {
			
			if ((!(spiegelMap.get("title-spiegel-"+i) == null)) && (!(spiegelMap.get("link-spiegel-"+i) == null)) &&
				(!spiegelMap.get("publ-spiegel-"+i).equals(null)) && (!spiegelMap.get("up-spiegel-"+i).equals(null)) &&
				(!spiegelMap.get("author-spiegel-"+i).equals(null)) && (!spiegelMap.get("sum-spiegel-"+i).equals(null))) {
				
				if (i == 1) {
					sqlString +=    globalCounterAfterGolem + "  , \"" + spiegelMap.get("title-spiegel-"+i) + "\" , \"" + spiegelMap.get("link-spiegel-"+i) +
									"\" , \"" + spiegelMap.get("publ-spiegel-"+i) + "\" , \"" + spiegelMap.get("up-spiegel-"+i) +
									"\" , \"" + spiegelMap.get("author-spiegel-"+i) + "\" , \"" + spiegelMap.get("sum-spiegel-"+i) +
									"\" , \"Spiegel\" ) ";
									globalCounterAfterSpiegel = globalCounterAfterGolem++;
				}
				else {
					sqlString +=	" , ( " + globalCounterAfterGolem + " , \"" + spiegelMap.get("title-spiegel-"+i) + "\" , \"" + spiegelMap.get("link-spiegel-"+i) +
									"\" , \"" + spiegelMap.get("publ-spiegel-"+i) + "\" , \"" + spiegelMap.get("up-spiegel-"+i) +
									"\" , \"" + spiegelMap.get("author-spiegel-"+i) + "\" , \"" + spiegelMap.get("sum-spiegel-"+i) +
									"\" , \"Spiegel\" ) ";
									globalCounterAfterSpiegel = globalCounterAfterGolem++;
				}
			}
		}
		
		sqlString +="; ";
		globalCounterAfterSpiegel++;
		
		return sqlString;
	}
	
	public static String prepareStatementFromHeiseMap(HashMap<String,String> Map) {
		String sqlString = "INSERT INTO " + dbName + " VALUES (";
		heiseMap = Map;
		globalCounter = heiseMap.size();
		
		//get golem String
		for (int i = 1; i <= globalCounter; i++) {
			
			if ((!(heiseMap.get("title-heise-"+i) == null)) && (!(heiseMap.get("link-heise-"+i) == null)) &&
				(!(heiseMap.get("publ-heise-"+i) == null)) && (!(heiseMap.get("up-heise-"+i) == null)) &&
				(!(heiseMap.get("author-heise-"+i) == null)) && (!(heiseMap.get("sum-heise-"+i) == null))) {
				
				if (i == 1) {
					sqlString +=    globalCounterAfterSpiegel + "  , \"" + heiseMap.get("title-heise-"+i) + "\" , \"" + heiseMap.get("link-heise-"+i) +
									" , \"" + heiseMap.get("publ-heise-"+i) + "\" , \"" + heiseMap.get("up-heise-"+i) +
									"\" , \"" + heiseMap.get("author-heise-"+i) + "\" , \"" + heiseMap.get("sum-heise-"+i) +
									"\" , \"Heise\" ) ";
									globalCounterAfterHeise = globalCounterAfterSpiegel++;
				}
				else {
					sqlString +=	" , ( " + globalCounterAfterSpiegel + " , \"" + heiseMap.get("title-heise-"+i) + "\" , \"" + heiseMap.get("link-heise-"+i) +
									" , \"" + heiseMap.get("publ-heise-"+i) + "\" , \"" + heiseMap.get("up-heise-"+i) +
									"\" , \"" + heiseMap.get("author-heise-"+i) + "\" , \"" + heiseMap.get("sum-heise-"+i) +
									"\" , \"Heise\" ) ";
									globalCounterAfterHeise = globalCounterAfterSpiegel++;
				}
			}
		}
		
		sqlString +="; ";
		
		return sqlString;
	}
}
