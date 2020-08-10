package core.person;

import java.util.List;

import core.course.UV;

/**
 * 
 * @author lucas
 *
 */
public class Teacher extends Person{

		private List<UV> uvList;
		
		/**
		 * @param lName
		 * @param fName
		 */
		public Teacher(String lName, String fName) {
			super(lName, fName);
		}
		
		/**
		 * @return the UV List
		 */
		public List<UV> getUvList()
		{
			return this.uvList;
		}

}
