package bdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Result;

import core.course.UV;
import core.exam.Exam;
import core.person.Student;
import hmi.javaFx.ConnectionPage;
import hmi.javaFx.HomeInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author audra
 *
 */
public class PersonalPageQuery {
	
	/**
	 * @return list of UVs
	 * @throws SQLException
	 */
	public static List<UV> getListUv() throws SQLException{
		List<UV> listUV = new ArrayList<UV>();
		List<String> tempoCode = new ArrayList<>();
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from to_follow where LAST_NAME ='"+ConnectionPage.lastNameConnected+"' AND FIRST_NAME= '"+ConnectionPage.firstNameConnected+"'");
		while(re.next()) {
			tempoCode.add(re.getString("CODE"));
		}
		for(String s : tempoCode) {
			UV uv = new UV(s,getTitleUV(s),getStudentList(s));
			listUV.add(uv);
		}
		return listUV;	
	}
	
	/**Take and return the sutdent list of a current UV
	 * @param uvName 
	 * @return the list of student for a given list 
	 * @throws SQLException 
	 */
	public static List<Student> getStudentList(String uvName) throws SQLException{
		
		List<Student> list = new ArrayList<Student>();
		ResultSet res = ConnectionToBDD.getStatement().executeQuery("SELECT * from to_follow WHERE CODE = '"+uvName+"'");
		while(res.next()) {
			list.add(new Student(res.getString("LAST_NAME"),res.getString("FIRST_NAME")));
		}
		return list;
	}
	
	/**It will search in the database the Title of an un given in parameter
	 * @param uvName
	 * @return a string
	 * @throws SQLException
	 */	
	public static String getTitleUV(String uvName) throws SQLException {
		String title = null;
		ResultSet rest = ConnectionToBDD.getStatement().executeQuery("SELECT * from uv WHERE CODE = '"+uvName+"'");
		while(rest.next()){
			
			title = rest.getString("TITLE");
		}
		return title;
	}
	
	/**It will create the new student in the table
	 * @param lastName 
	 * @param firstName 
	 * @throws SQLException 
	 * 
	 */
	public static void addStudentBase(String lastName,String firstName) throws SQLException {
		
		int re = ConnectionToBDD.getStatement().executeUpdate(" INSERT INTO to_follow (LAST_NAME,FIRST_NAME,CODE) VALUES('"+lastName+"','"+firstName+"','"+HomeInterface.uv.getCode()+"')");

	}
	
	/**Will return if the student or teacher exist
	 * @param lastName
	 * @param firstName
	 * @return a boolean
	 * @throws SQLException 
	 */
	public static boolean isInDataBase(String lastName,String firstName) throws SQLException {
		ResultSet res = ConnectionToBDD.getStatement().executeQuery("SELECT * from person WHERE LAST_NAME = '"+lastName+"' AND FIRST_NAME='"+firstName+"'");
		if(res.next()) {
			return true;	
		}
		else {
			return false;
		}
	}
	/**
	 * @param lastName
	 * @param firsName
	 * @throws SQLException 
	 */
	public static void removeStudentBase(String lastName,String firsName) throws SQLException {
		int res = ConnectionToBDD.getStatement().executeUpdate("DELETE from is_part_of WHERE CODE = '"+HomeInterface.uv.getCode()+"' AND LAST_NAME='"+lastName+"' AND FIRST_NAME='"+firsName+"'");
		int re = ConnectionToBDD.getStatement().executeUpdate("DELETE from to_follow WHERE CODE = '"+HomeInterface.uv.getCode()+"' AND LAST_NAME='"+lastName+"' AND FIRST_NAME='"+firsName+"'");
		if(re>0) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Delete");
	        alert.setHeaderText(null);
	        alert.setContentText("The student has been removed");
	        alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Delete");
	        alert.setHeaderText(null);
	        alert.setContentText("The registered student doesn't exist");
	        alert.showAndWait();
		}
	}
}
	
	
