package core.course;

/**
 * 
 * @author loic & Thomas
 *
 */
public class Course {

		private UV uv;
		private String grade;
		
	/**Empty constructor
	 * 
	 */
	public Course() {
		// TODO Auto-generated constructor stub
	}
	
	/** constructor with setting parameters
	 * @param uv : nom de l'UV
	 * @param grade : grade de l'uv 
	 * 
	 */
	public Course(UV uv, String grade) {
		// TODO Auto-generated constructor stub
		this.uv = uv;
		this.grade = grade;
	}
	
	
	/** This function will return the uv variable
	 * @return UV
	 */
	public UV getUv() {
		return this.uv;
	}
	
	/** set the uv variable
	 * @param uv
	 */
	public void setUV(UV uv) {
		this.uv = uv;
	}
	
	/** get the Grade
	 * @return String
	 */
	public String getGrade() {
		return this.grade;
	}
	
	/** set the Grade
	 * @param grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**Print the UV value
	 * 
	 */
	public void PrintUv() {
		System.out.println(this.uv);
	}
	
	/**print the grade value
	 * 
	 */
	public void printGrade() {
		System.out.println(this.grade);
	}
}
