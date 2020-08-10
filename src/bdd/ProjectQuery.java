package bdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.course.ProjectGroup;
import core.person.Student;
import hmi.javaFx.ConnectionPage;
import hmi.javaFx.HomeInterface;

/**
 * @author audra
 *
 */
public class ProjectQuery {

	
	/**This method will return a project group object
	 * @param code 
	 * @param nb 
	 * @return project Group
	 * @throws SQLException 
	 * 
	 */
	public static ProjectGroup getProjectInformations(String code,int nb) throws SQLException { 
		
		ProjectGroup pg = new ProjectGroup(getListStudentInProject(code,nb),getMarkOfTheGroup(code,nb));
		
		return pg;
		
	}
	
	/** This method will get all the student in the given project group in certain uv
	 * @param code
	 * @param nbProject
	 * @return a studentList
	 * @throws SQLException 
	 */
	public static List<Student> getListStudentInProject(String code,int nbProject) throws SQLException {
		List<Student> listStudentInGroup = new ArrayList<>();
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from is_part_of where CODE='"+code+"' AND NUM_PROJECT='"+nbProject+"'");
		while(re.next()) {
			listStudentInGroup.add(new Student(re.getString("LAST_NAME"),re.getString("FIRST_NAME")));
		}
		return listStudentInGroup;

	}
	
	/** This method will return the mark of the group 
	 * @param code
	 * @param nbProject
	 * @return mark
	 * @throws SQLException 
	 */
	public static int getMarkOfTheGroup(String code, int nbProject) throws SQLException {
		int mark =0;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from project where CODE='"+code+"' AND NUM_PROJECT='"+nbProject+"'");
		if(re.next()) {
			mark = re.getInt("MARK");
		}
		return mark;
	}
	
	/**This method will check if a student already has a project group or not
	 * @param code 
	 * @return true or false depending on the result of the query
	 * @throws SQLException 
	 */
	public static Boolean hasAProject(String code) throws SQLException {
			ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from is_part_of where LAST_NAME ='"+ConnectionPage.lastNameConnected+"' AND FIRST_NAME= '"+ConnectionPage.firstNameConnected+"' AND CODE='"+code+"'");
			if(re.next()) {
				return true;
			}
			else {
				return false;
			}
	}
	
	/**This method will return the list of all project adding the number of people inside
	 * @param code
	 * @return the listString
	 * @throws SQLException 
	 */
	public static int getNumberProject(String code) throws SQLException {
		int numb = 0;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from project where CODE='"+code+"'");
		while(re.next()) {
			numb++;
		}
		return numb;
	}
	
	/**This method will allow us to get the number of students inside one project
	 * @param code
	 * @param num
	 * @return an integer
	 * @throws SQLException 
	 */
	public static int getNumberPersonInProject(String code, int num) throws SQLException {
		int numb = 0;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from is_part_of where CODE='"+code+"' AND NUM_PROJECT='"+num+"'");
		while(re.next()) {
			numb++;
		}
		return numb;
	}
	
	/**Will add a student to a project group
	 * @param numb
	 * @throws SQLException 
	 */
	public static void addPersonToProject(int numb) throws SQLException {
		int re = ConnectionToBDD.getStatement().executeUpdate(" INSERT INTO is_part_of (LAST_NAME,FIRST_NAME,CODE,NUM_PROJECT) VALUES('"+ConnectionPage.lastNameConnected+"','"+ConnectionPage.firstNameConnected+"','"+HomeInterface.uv.getCode()+"','"+numb+"')");

	}
	
	/** get the number of project
	 * @param code
	 * @return a string
	 * @throws SQLException 
	 */
	public static int getNumberProjectIn(String code) throws SQLException{
		int number = 0;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from is_part_of where CODE='"+code+"' AND  LAST_NAME ='"+ConnectionPage.lastNameConnected+"' AND FIRST_NAME= '"+ConnectionPage.firstNameConnected+"'");
		if(re.next()) {
			number = re.getInt("NUM_PROJECT");
		}
		return number;
	}
	
	/**This method will create a new project in the database
	 * @param code
	 * @param title
	 * @param number
	 * @throws SQLException 
	 */
	public static void createProjectQuery(String title,int number) throws SQLException{
		int numberProject = 1;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from project where CODE='"+HomeInterface.uv.getCode()+"'");
		while(re.next()) {
			numberProject++;
		}
		int res = ConnectionToBDD.getStatement().executeUpdate(" INSERT INTO project(CODE,NUM_PROJECT,TITLE,NUMBER_IN_PROJECT) VALUES('"+HomeInterface.uv.getCode()+"','"+numberProject+"','"+title+"','"+number+"')");
	}
	
	/**Get the title of a given project
	 * @param numberProject
	 * @return String
	 * @throws SQLException 
	 */
	public static String getTitleProject(int numberProject) throws SQLException {
		String title = "" ;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from project where NUM_PROJECT='"+numberProject+"' AND CODE='"+HomeInterface.uv.getCode()+"'");
		if(re.next()) {
			title = re.getString("TITLE");
		}
		return title;
		
	}
	
	/**This method will return the max number of student inside one given project
	 * @param numberProject
	 * @return an integer
	 * @throws SQLException
	 */
	public static int numberMaxStudent(int numberProject) throws SQLException {
		int numb = 0;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from project where NUM_PROJECT='"+numberProject+"' AND CODE='"+HomeInterface.uv.getCode()+"'");
		if(re.next()) {
			numb = re.getInt("NUMBER_IN_PROJECT");
		}
		return numb;
	}
	
	/**Send the mark value in the base
	 * @param num_project
	 * @param grade
	 * @throws SQLException
	 */
	public static void addGradeDataBase(int num_project,int grade) throws SQLException {
		int re = ConnectionToBDD.getStatement().executeUpdate(" UPDATE project SET MARK = '"+grade+"' WHERE CODE = '"+HomeInterface.uv.getCode()+"' AND NUM_PROJECT= '"+num_project+"' ");
	}
}


