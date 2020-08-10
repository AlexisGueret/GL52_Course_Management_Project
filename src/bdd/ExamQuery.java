package bdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.course.UV;
import core.exam.Choice;
import core.exam.Exam;
import core.exam.Question;
import core.exam.Try;
import core.person.Student;
import core.person.Teacher;
import hmi.console.ExamOutPut;

/**
 * Class to interact with the data base and exam management
 * @author loic
 * @version 1.0
 */
public abstract class ExamQuery 
{
	/**
	 * Gets the exam list for one UV
	 * @param uv : UV
	 * @return List<Exam>, the list of exam of this UV
	 */
	public static List<Exam> getListExamFromBDD(UV uv)
	{
		List<Exam> listExam = new ArrayList<Exam>();
		String query = "SELECT * FROM exam WHERE CODE = '" + uv.getCode() + "';";
		
		try 
		{
			ResultSet result;
			result = ConnectionToBDD.getStatement().executeQuery(query);
			while(result.next())
			{
				int id = result.getInt("NUM_EXAM");
				String code = result.getString("CODE");
				String title = result.getString("Title");
				boolean isVisible = result.getBoolean("IS_VISIBLE");
				long timeLimite = result.getLong("TIME_LIMITE");
				String lastName = result.getString("LAST_NAME");
				String firstName = result.getString("FIRST_NAME");
				
				Teacher creator = new Teacher(lastName, firstName);
				Exam exam = new Exam(id, code, title, creator, isVisible, timeLimite);
				listExam.add(exam);
			}
			for(Exam exam : listExam)
				ExamQuery.getListQuestionFromBDD(exam);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return listExam;
	}
	
	/**
	 * Gets the list of question for a specific exam
	 * @param exam : Exam
	 */
	public static void getListQuestionFromBDD(Exam exam)
	{
		String query = "SELECT * FROM question WHERE question.num_exam = " + exam.getId() + ";";
		
		try 
		{
			ResultSet result = ConnectionToBDD.getStatement().executeQuery(query);
			while(result.next())
			{
				int id = result.getInt("NUM_QUESTION");
				float value = result.getFloat("VALUE");
				String statement = result.getString("STATEMENT");
				
				exam.addAQuestion(new Question(id, statement, value));
			}
			for(Question question : exam.getQuestionList())
				ExamQuery.getChoiceListFromBDD(question, exam.getId());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of choices for a question
	 * @param question : Question
	 * @param examId : int
	 * @param questionId : int
	 */
	public static void getChoiceListFromBDD(Question question, int examId)
	{
		String query = "SELECT * FROM choice WHERE choice.num_exam = " + examId + " AND choice.num_question = " + question.getId() + ";";
		
		try 
		{
			ResultSet result = ConnectionToBDD.getStatement().executeQuery(query);
			while(result.next())
			{
				int id = result.getInt("NUM_CHOICE");
				boolean isCorrect = result.getBoolean("IS_CORRECT");
				String statement = result.getString("STATEMENT");
				
				Choice choice = new Choice(id, statement, isCorrect);
				
				question.addAChoice(choice);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a try to the data base
	 * @param examTry : Try, the try to add
	 */
	public static void addTryToDatabase(Try examTry)
	{
		Timestamp sqlStartingHour = new Timestamp(examTry.getStartingHour().getTime());
		Timestamp sqlEndingHour = new Timestamp(examTry.getEndingHour().getTime());
		String query = "INSERT INTO to_pass (NUM_EXAM, LAST_NAME, FIRST_NAME, MARK, STARTING_HOUR, ENDING_HOUR) VALUES "
				+"("+examTry.getExam().getId()+", '"+examTry.getStudent().getLastName()+"', '"
				+examTry.getStudent().getFirstName()+"', "+examTry.getMark()+", '"+sqlStartingHour
				+"', '"+sqlEndingHour+"')"  ;
		
		try 
		{
			ConnectionToBDD.getStatement().executeUpdate(query);
			for(Map.Entry mapEntry : examTry.getAnswerList().entrySet())
			{
				Question question = (Question)mapEntry.getKey();
				List<Choice> choiceList = (List<Choice>)mapEntry.getValue();
				for(Choice choice : choiceList)
				{
					String query2 = "INSERT INTO TO_MAKE_A_CHOICE (LAST_NAME, FIRST_NAME, NUM_EXAM, NUM_QUESTION, NUM_CHOICE) "
							+ "VALUES ('" + examTry.getStudent().getLastName() + "', '"
							+ examTry.getStudent().getFirstName() + "', " + examTry.getExam().getId() + ", "
							+ question.getId() + ", " + choice.getId() + ")";
					ConnectionToBDD.getStatement().executeUpdate(query2);
				}
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	/**
	 * Indicates if a student has already passed an Exam
	 * @param exam : Exam
	 * @param student : Student
	 * @return boolean
	 */
	public static Boolean hasAlreadyMadeATry(Exam exam, Student student)
	{
		try 
		{
			ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from to_pass WHERE NUM_EXAM = "+exam.getId()+" AND LAST_NAME ='"
					+student.getLastName()+"' AND FIRST_NAME = '"+student.getFirstName()+"';");
			if(re.next()) 
				return true;
		} 
		catch (SQLException e) {e.printStackTrace();}
		return false;
	}
	
	/**
	 * Return the mark the student had at an Exam
	 * @param exam : Exam
	 * @param student : Student
	 * @return float
	 */
	public static float getExamMark(Exam exam, Student student)
	{
		float mark = 0;
		try 
		{
			ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from to_pass WHERE NUM_EXAM = "+exam.getId()+" AND LAST_NAME ='"
					+student.getLastName()+"' AND FIRST_NAME = '"+student.getFirstName()+"';");
			if(re.next()) 
				mark = re.getFloat("MARK");
		}
		catch (SQLException e) {e.printStackTrace();}
		return mark;
	}
	
	
	/**
	 * Add an exam to the data base
	 * @param exam : Exam
	 */
	public static void addAnExamToDataBase(Exam exam)
	{
		List<String> queryList = new ArrayList<String>();
		
		int bool1 = 0;
		if(exam.isVisible())
			bool1 = 1;
		
		queryList.add("INSERT INTO exam VALUES ("+exam.getId()+",'"+exam.getCode()+"','"+exam.getCreator().getLastName() + "','" + exam.getCreator().getFirstName()
				+ "','" + exam.getTitle() + "'," +bool1+ "," + exam.getTimeLimite() + ");");
		
		for(Question question : exam.getQuestionList())
		{
			queryList.add("INSERT INTO question VALUES ("+exam.getId()+","+question.getId()+",'"+question.getStatement()+"',"+question.getValue()+");");
			for(Choice choice : question.getChoiceList())
			{
				int bool = 0;
				if(choice.isCorrect())
					bool = 1;
				queryList.add("INSERT INTO choice VALUES ("+exam.getId()+","+question.getId()+","+choice.getId()+",'"+choice.getStatement()+"',"+bool+");");
			}
		}
		
		try
		{
			for(String query : queryList)
				ConnectionToBDD.getStatement().executeUpdate(query);
		}
		catch(SQLException e) {e.printStackTrace();}
	}
	
	/**
	 * Delete an Exam from the BDD
	 * @param exam : Exam
	 */
	public static void deteleAnExamFromDataBase(Exam exam)
	{
		List<String> queryList = new ArrayList<String>();
		queryList.add("DELETE FROM exam WHERE NUM_EXAM = " + exam.getId() + ";");
		queryList.add("DELETE FROM question WHERE NUM_EXAM = " + exam.getId() + ";");
		queryList.add("DELETE FROM choice WHERE NUM_EXAM = " + exam.getId() + ";");
		queryList.add("DELETE FROM to_make_a_choice WHERE NUM_EXAM = " + exam.getId() + ";");
		queryList.add("DELETE FROM to_pass WHERE NUM_EXAM = " + exam.getId() + ";");
		
		try
		{
			for(String query : queryList)
				ConnectionToBDD.getStatement().execute(query);
		}
		catch(SQLException e) {e.printStackTrace();}
	}
	
	
	/**
	 * Get the maximum id for an exam in the data base
	 * @return int 
	 */
	public static int getMaxExamId()
	{
		int max = 0;
		try 
		{
			ConnectionToBDD.createConnection();
			ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT MAX(NUM_EXAM) FROM exam;");
			if(re.next()) 
				max = re.getInt(1);
		}
		catch (SQLException e) {e.printStackTrace();}
		return max;
	}
}
