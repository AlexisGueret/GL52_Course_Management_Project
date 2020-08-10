package core.forum;

import java.util.Date;

import core.person.Person;

public class Message {
	private String content;
	private Person author;
	private Date date;
	
	/** constructor with setting parameters. 
	 * The date is initialized automatically at the date of instantiation
	 * @param author author of the message
	 * @param content content of the message : a String
	 */
	public Message(String content,Person author)
	{
		this.author=author;
		this.content=content;
		this.date=new Date();
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	public void setContent(String newContent)
	{
		this.content=newContent;
	}
	
	public Person getAuthor()
	{
		return this.author;
	}
	
	public String toString()
	{
		return this.author.getLastName() +" " + this.author.getFirstName() +  " : " + this.content;
	}

}
