package core.forum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import core.course.UV;
import core.person.Person;

public class Topic {
	private ArrayList<Message> messageList;
	private String title;
	private Person author;
	private Date creationDate;
	private int id;
	private UV uv;
	private Forum forum;
	
	/**
	 * Creates a Topic
	 * @param id 
	 * @param author 
	 * @param title 
	 * @param uv 
	 * @param forum 
	 */
	public Topic(int id,Person author,String title,UV uv,Forum forum)
	{
		this.forum=forum;
		this.uv=uv;
		this.id=id;
		this.messageList=new ArrayList<Message>();
		this.author=author;
		this.title=title;
	}
	
	/** Creates a new message and adds it to the list of message of the topic
	 * @param content content of the message : a String
	 * @param author the author of the message
	 */	
	public void createMessage(String content,Person author)
	{
		this.messageList.add(new Message(content,author));
		
	}
	
	
	/** Removes a message from the topic
	 * @param message reference to the message to delete
	 */	
	public void removeMessage(Message message)
	{
		this.messageList.remove(message);
	}
	
	/**
	 * @return String : title
	 */
	public String getTitle()
	{
		return this.title;
	}
	
	/**
	 * @return Person : author
	 */
	public Person getAuthor()
	{
		return this.author;
	}
	
	public Date getCreationDate()
	{
		return this.creationDate;
	}
	
	public ArrayList<Message> getMessageList()
	{
		return this.messageList;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public void setID(int id)
	{
		this.id=id;
	}
	
	public UV getUV()
	{
		return this.uv;
	}
	
	public Forum getForum()
	{
		return this.forum;
	}
	
}
	