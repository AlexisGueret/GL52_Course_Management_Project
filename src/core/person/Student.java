package core.person;

import java.util.List;

import core.course.Course;

/**
 * 
 * @author lucas
 *
 */
public class Student extends Person {

	private List<Course> courseList;
	
	/**
	 * @param lName
	 * @param fName
	 */
	public Student(String lName, String fName) {
		super(lName, fName);

	}
	
	/**
	 * @return the last name of the person
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * @return the firsname
	 */
	public String getFirstName() {
		return this.firstName;
	}



}
