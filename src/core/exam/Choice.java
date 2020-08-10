package core.exam;

/**
 * A question choice
 * @author loic
 * @version 1.0
 */
public class Choice 
{
	private int id;
	private String statement;
	private boolean isCorrect;
	
	/**
	 * Constructor
	 * @param _id : int, the ID
	 * @param _statement : String, the choice statement
	 * @param _isCorrect : boolean, indicates if this choice is correct
	 */
	public Choice(int _id, String _statement, boolean _isCorrect) 
	{
		this.id = _id;
		this.statement = _statement;
		this.isCorrect = _isCorrect;
	}

	public String getStatement() 
	{
		return this.statement;
	}

	public boolean isCorrect() {
		return this.isCorrect;
	}

	public int getId() {
		return this.id;
	}
}
