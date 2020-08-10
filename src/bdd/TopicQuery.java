package bdd;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import core.course.UV;
import core.forum.Forum;
import core.forum.Topic;
import core.person.Person;
import hmi.javaFx.ConnectionPage;

public class TopicQuery {
	
	private static final String Null = null;
	/**
	 * Gets the topic list associated to a project forum
	 * @param uv : UV
	 * @param projectID 
	 * @param forum 
	 * @return ArrayList<Topic>
	 * @throws SQLException 
	 */
	public static ArrayList<Topic> getTopicList(UV uv,String projectID,Forum forum) throws SQLException
	{
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		ArrayList<Integer> tempoNum=new ArrayList<Integer>();
		int NUM_FORUM= getForumId(uv,projectID);
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from topic where CODE_UV ='"+ uv.getCode()+projectID+"'"
		);
		
		while(re.next())
		{				
			Topic topic = new Topic(re.getInt("NUM_TOPIC"),new Person(re.getString("AUTHOR_LAST_NAME"),re.getString("AUTHOR_FIRST_NAME")),re.getString("TITLE"),uv,forum);
			topicList.add(topic);
		}
		for(Topic topic : topicList)
		{
			ResultSet re2 = ConnectionToBDD.getStatement().executeQuery("SELECT * from message where NUM_TOPIC ='"+ topic.getID()+ "'"
					);
			while(re2.next())
			{
				if(re2.getInt("NUM_FORUM")==NUM_FORUM)
				{
					String content = re2.getString("CONTENT");
					String lastName= re2.getString("LAST_NAME");
					String firstName = re2.getString("FIRST_NAME");
					
						topic.createMessage(content, new Person(lastName,firstName));
				}
			}
		}
		
		return topicList;
	}
	
	/**
	 * Gets the topic list associated to a forum (non-project)
	 * @param uv : UV
	 * @param forum 
	 * @return ArrayList<Topic>
	 * @throws SQLException 
	 */
	public static ArrayList<Topic> getTopicList(UV uv,Forum forum) throws SQLException
	{
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		ArrayList<Integer> tempoNum=new ArrayList<Integer>();
		int NUM_FORUM= getForumId(uv);
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from topic where CODE_UV ='"+ uv.getCode()+"'"
		);
		while(re.next())
		{
			Topic topic = new Topic(re.getInt("NUM_TOPIC"),new Person(re.getString("AUTHOR_LAST_NAME"),re.getString("AUTHOR_FIRST_NAME")),re.getString("TITLE"),uv,forum);
			topicList.add(topic);
		}
		for(Topic topic : topicList)
		{
			ResultSet re2 = ConnectionToBDD.getStatement().executeQuery("SELECT * from message where NUM_TOPIC ='"+ topic.getID()+ "'"
					);
			while(re2.next())
			{
				if(re2.getInt("NUM_FORUM")==NUM_FORUM)
				{
					String content = re2.getString("CONTENT");
					String lastName= re2.getString("LAST_NAME");
					String firstName = re2.getString("FIRST_NAME");
					
						topic.createMessage(content, new Person(lastName,firstName));
				}
			}
		}
		return topicList;
	}

	
	/**
	 * Creates a topic in the database
	 * @param forum
	 * @param authorLastName 
	 * @param authorFirstName 
	 * @param title 
	 * @throws SQLException
	 */
	public static void createTopic(Forum forum,String authorLastName,String authorFirstName,String title) throws SQLException
	{
		int NUM_TOPIC = forum.getTopicList().size();
		int NUM_FORUM;
		String CODE_UV;
		if(!forum.isProjectForum())
		{
			NUM_FORUM = getForumId(forum.getUv());
			CODE_UV = forum.getUv().getCode();	
		}			
		else
		{
			NUM_FORUM = getForumId(forum.getUv(),forum.getProjectID());
			CODE_UV = forum.getUv().getCode()+forum.getProjectID();	
		}
			
			
		
		java.util.Date date = new java.util.Date();
		Timestamp datesql = new java.sql.Timestamp(date.getTime());
		int re = ConnectionToBDD.getStatement().executeUpdate(" INSERT INTO topic (NUM_TOPIC,NUM_FORUM,CODE_UV,TITLE,CREATION_DATE,AUTHOR_LAST_NAME,AUTHOR_FIRST_NAME) VALUES("+NUM_TOPIC+",'"+NUM_FORUM+"','"+CODE_UV+"','"+title+"','"+datesql+"','"+authorLastName+"','"+authorFirstName+"')"	);
		
	}
	
	/**
	 * Creates a message in the database
	 * @param topic
	 * @param AUTHOR_LAST_NAME
	 * @param AUTHOR_FIRST_NAME
	 * @param MESSAGE
	 * @throws SQLException
	 */
	public static void createMessage(Topic topic,String AUTHOR_LAST_NAME,String AUTHOR_FIRST_NAME,String MESSAGE) throws SQLException
	{
		int NUM_MESSAGE = topic.getMessageList().size();
		int NUM_TOPIC = topic.getID();
		int NUM_FORUM;
		System.out.println("ID /:::::::/ " +topic.getID() );
		if(!topic.getForum().isProjectForum())
		{
			NUM_FORUM = getForumId(topic.getUV());
		}			
		else
		{
			NUM_FORUM = getForumId(topic.getForum().getUv(),topic.getForum().getProjectID());	
		}		
		java.util.Date date = new java.util.Date();
		Timestamp datesql = new java.sql.Timestamp(date.getTime());
		int re = ConnectionToBDD.getStatement().executeUpdate(" INSERT INTO message (LAST_NAME,FIRST_NAME,NUM_TOPIC,NUM_MESSAGE,CONTENT,SENDING_DATE,NUM_FORUM) VALUES('"+AUTHOR_LAST_NAME+"','"+AUTHOR_FIRST_NAME+"','"+NUM_TOPIC+"','"+NUM_MESSAGE+"','"+MESSAGE+"','"+datesql+"','"+NUM_FORUM+"')"	);
		
	}
	
	
	/**
	 * gets The Id of a forum from it's UV.
	 * @param uv
	 * @return  forum ID
	 * @throws SQLException
	 */
	public static int getForumId(UV uv) throws SQLException
	{
		int result=-1;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from forum where CODE_UV ='"+ uv.getCode()+"'"
				);
		while(re.next())
		{
			result= re.getInt("NUM_FORUM");
		}
		assert result!=-1;
		return result;
	}
	
	/**
	 * gets The Id of a project forum from it's UV and it's projectID.
	 * @param uv
	 * @param projectID
	 * @return forum ID
	 * @throws SQLException
	 */
	public static int getForumId(UV uv,String projectID) throws SQLException
	{
		int result=-1;
		ResultSet re = ConnectionToBDD.getStatement().executeQuery("SELECT * from forum where CODE_UV ='"+ uv.getCode()+projectID+"'"
				);
		while(re.next())
		{
			result= re.getInt("NUM_FORUM");
		}
		assert result!=-1;
		return result;
	}
	
}
