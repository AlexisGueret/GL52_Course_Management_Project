package hmi.console;

import core.exam.Choice;
import core.exam.Question;
import core.exam.Try;
import core.exam.Exam;

/**
 * Used to print in the console information about tests
 * @author loic
 * @version 1.0
 */
public abstract class ExamOutPut 
{
	/**
	 * Prints the main information about an exam
	 * @param exam
	 */
	public static void printExam(Exam exam)
	{
		System.out.println("*************************");
		System.out.println("Exam: " + exam.getTitle());
		System.out.println("*************************");
		System.out.println("Creator: " + exam.getCreator());
		if(exam.isVisible())
			System.out.println("This exam is visible by students");
		else
			System.out.println("This exam isn't visible by students");
		System.out.println("Time limite in sec : " + exam.getTimeLimite());
		System.out.println(System.lineSeparator() + "Question list: ");
		for(Question question : exam.getQuestionList())
			printQuestion(question);
	}
	
	/**
	 * Prints a question choice
	 * @param choice : Choice, the choice to print
	 */
	public static void printChoice(Choice choice)
	{
		System.out.println(choice.getStatement());
	}
	
	/**
	 * Prints a question
	 * @param question : Question, the question to print
	 */
	public static void printQuestion(Question question)
	{
		System.out.println(question.getValue() + " pts | " + question.getStatement());
		for(Choice choice : question.getChoiceList())
		{
			if(choice.isCorrect())
				System.out.print("	| True: ");
			else
				System.out.print("	| False: ");
			printChoice(choice);
		}
	}
	
	/**
	 * Prints a exam try
	 * @param aTry : Try, a student try in an exam
	 */
	public static void printTry(Try aTry)
	{
		//
	}
}
