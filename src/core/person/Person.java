package core.person;

/**
 * 
 * @author lucas
 *
 */
public class Person {

	protected String lastName;
	protected String firstName;
	
	/**Constructor of the Persor class
	 * @param lName
	 * @param fName
	 */
	public Person(String lName, String fName) {
		this.lastName=lName;
		this.firstName=fName;
		
	}

	public String getLastName() {
		return this.lastName;
	}
	public String getFirstName() {
		return this.firstName;
	}
}
