package core.exam;

import java.util.ArrayList;
import java.util.List;

import core.person.Student;
import core.person.Teacher;

/**
 * @version 1.0
 * @author loic
 * A Test object is an exam created by a teacher in order to evaluate student
 */
public class Exam 
{
	private int id;
	private String title;
	private String code;
	private Teacher creator;
	private List<Question> questionList;
	private float maximumPointPossible;
	private long timeLimite;
	private boolean isVisible;
	private List<Try> tryList;
	
	/**
	 * Constructor
	 * @param _id : int
	 * @param _code : String
	 * @param _title : String
	 * @param _creator : Teacher, the teacher which creates the test
	 * @param _isVisible : boolean, indicates if this exam is visble by student
	 * @param _timeLimite : long, the maximum time to pass the test in second
	 */
	public Exam(int _id, String _code, String _title, Teacher _creator, boolean _isVisible, long _timeLimite) 
	{
		this.id = _id;
		this.code = _code;
		this.title = _title;
		this.creator = _creator;
		this.questionList = new ArrayList<Question>();
		this.isVisible = _isVisible;
		this.timeLimite = _timeLimite;
		this.tryList = new ArrayList<Try>();
	}
	
	/**
	 * Add a question to this exam
	 * @param question : Question, the question to add
	 */
	public void addAQuestion(Question question)
	{
		this.questionList.add(question);
	}
	
	/**
	 * Removes a question from the question list
	 * @param question : Question, the question to remove
	 */
	public void removeAQuestion(Question question)
	{
		this.questionList.remove(question);
	}
	
	/**
	 * Give the total of point it is possible to have at this exam
	 * 
	 */
	public void setMaxPointPossible()
	{
		float sum = 0;
		for(Question question : this.questionList)
			sum += question.getValue();
		this.maximumPointPossible = sum;
	}
	
	
	
	public String getCode() {
		return this.code;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Teacher getCreator() {
		return this.creator;
	}

	public void setCreator(Teacher creator) {
		this.creator = creator;
	}

	public List<Question> getQuestionList() {
		return this.questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public long getTimeLimite() {
		return this.timeLimite;
	}

	public void setTimeLimite(long timeLimite) {
		this.timeLimite = timeLimite;
	}

	public List<Try> getTryList() {
		return this.tryList;
	}

	public void setTryList(List<Try> tryList) {
		this.tryList = tryList;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public float getMaximumPointPossible() {
		return this.maximumPointPossible;
	}
	
	
}