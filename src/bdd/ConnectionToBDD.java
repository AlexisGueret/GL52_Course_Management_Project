package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.course.UV;
import core.person.Student;
import hmi.javaFx.ConnectionPage;
import hmi.javaFx.HomeInterface;

/** Link to the DataBase
 * @author audra
 *
 */
public class ConnectionToBDD{
	
	  static Connection connection;
	  private static Statement statement;
	  private static Statement statement2;
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws Exception
	 */
	public static void createConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection  = DriverManager.getConnection("jdbc:mysql://localhost/gl52_project ?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","");
			setStatement(connection.createStatement());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return statement
	 */
	public static Statement getStatement() {
		return statement;
	}
	
	
	/**
	 * @param statement
	 */
	public static void setStatement(Statement statement) {
		ConnectionToBDD.statement = statement;
	}
	
	
	/** this method will check if the name given is in database
	 * @param lastName 
	 * @param firstName 
	 * @return a boolean
	 * @throws SQLException 
	 */
	public static boolean isConnectionTrue(String lastName,String firstName) throws SQLException {
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from person where LAST_NAME ='"+lastName+"' AND FIRST_NAME= '"+firstName+"'");
		if(re.next()) {
			ConnectionPage.state = re.getString("ROLE");
			return true;
		}
		else {
			return false;
		}
	}

	
	
		
	}


