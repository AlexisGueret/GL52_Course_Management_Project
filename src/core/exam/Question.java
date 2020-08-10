package core.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A question of a Test
 * @author loic
 * @version 1.0
 */
public class Question 
{
	private int id;
	private String statement;
	private float value;
	private List<Choice> choiceList; //Maximum 10 choices
	
	/**
	 * Constructor
	 * @param _id : int, the id
	 * @param _statement : String, the question statement
	 * @param _value : float, the number of points that this question can give
	 */
	public Question(int _id, String _statement, float _value) 
	{
		this.id = _id;
		this.statement = _statement;
		this.value = _value;
		this.choiceList = new ArrayList<Choice>();
	}
	
	/**
	 * Add a choice to this question, 10 choices per question maximum
	 * @param choice : Choice, the choice to add
	 */
	public void addAChoice(Choice choice)
	{
		if(this.choiceList.size() < 10)
			this.choiceList.add(choice);
	}

	/**
	 * Removes a choice from choiceList and correctChoiceList 
	 * @param choice : Choice, the choice to remove
	 */
	public void removeAChoice(Choice choice)
	{
		if(this.choiceList.contains(choice))
			this.choiceList.remove(choice);
	}
	
	/**
	 * Give the question points if all the answer are correct
	 * @param answer : List<Choice>, the choices the student thinks are true
	 * @return float : 0 if the answer is incorrect, value if its correct
	 */
	public float givePoint(List<Choice> answer)
	{
		if(answer.isEmpty())
			return 0;
		
		// Checks the answers
		boolean testChoices = true;
		for(Choice choice : answer)
		{
			if(!choice.isCorrect())
				testChoices = false;
		}
		for(Choice choice : this.choiceList)
		{
			if(choice.isCorrect() && !answer.contains(choice))
				testChoices = false;
		}
		
		// Gives the mark
		if(testChoices)
			return this.value;
		else
			return 0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatement() {
		return this.statement;
	}

	public float getValue() {
		return this.value;
	}

	public List<Choice> getChoiceList() {
		return this.choiceList;
	}
}
