package core.exam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.person.Student;

/**
 * @version 1.0
 * @author loic
 * The is one try of a test per student
 */
public class Try 
{
	private Exam exam;
	private Student student;
	private float mark;
	private HashMap<Question, List<Choice>> answerList;
	private Date startingHour;
	private Date endingHour;
	
	/**
	 * Constructor
	 * @param _exam : Exam, the exam in which this belongs
	 * @param _student : Student, the student who is making this try
	 */
	public Try(Exam _exam, Student _student) 
	{
		this.exam = _exam;
		this.student = _student;
		this.startingHour = new Date();
		this.answerList = new HashMap();
		for(Question question : this.exam.getQuestionList())
		{
			this.answerList.put(question, new ArrayList<Choice>());
		}
	}
	
	/**
	 * Add a choice to an answer
	 * @param question : Question, the question the student is answering
	 * @param choice : Choice, a choice the student thinks is true
	 */
	public void addChoiceToAnAnswer(Question question, Choice choice)
	{
		List<Choice> choiceList = this.answerList.get(question);
		choiceList.add(choice);
		this.answerList.put(question, choiceList);
	}
	
	/**
	 * Removes a choice from an answer
	 * @param question : Question, the question the student is answering
	 * @param choice : Choice, a choice the student thinks is no longer true
	 */
	public void removeChoiceToAnAnswer(Question question, Choice choice)
	{
		List<Choice> choiceList = this.answerList.get(question);
		choiceList.remove(choice);
		this.answerList.put(question, choiceList);
	}
	
	/**
	 * End the student exam
	 */
	public void finishTry()
	{
		this.mark = 0;
		
		// Checks the time
		this.endingHour = new Date();
		
		for(Map.Entry mapEntry : this.answerList.entrySet())
		{
			Question question = (Question)mapEntry.getKey();
			this.mark += question.givePoint((List<Choice>)mapEntry.getValue());
		}
		
		this.exam.setMaxPointPossible();
		
		// Put the grade over 20
		this.mark = this.mark * 20 / this.exam.getMaximumPointPossible();
		this.mark = (float) ((float)Math.round(this.mark*100.0)/100.0);
		
		// Checks if the time limit is over
		if(!isInTimeLimit())
			this.mark = 0;
	}
	
	/**
	 * Return true if the time limit was respected
	 * @return boolean
	 */
	public boolean isInTimeLimit()
	{
		if(this.endingHour.getTime() > (this.startingHour.getTime() + this.exam.getTimeLimite()*1000))
			return false;
		return true;
	}

	public Exam getExam() {
		return this.exam;
	}

	public Student getStudent() {
		return this.student;
	}

	public float getMark() {
		return this.mark;
	}

	public HashMap<Question, List<Choice>> getAnswerList() {
		return this.answerList;
	}

	public Date getStartingHour() {
		return this.startingHour;
	}

	public Date getEndingHour() {
		return this.endingHour;
	}
	
}
