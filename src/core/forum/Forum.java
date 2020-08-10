package core.forum;

import java.util.ArrayList;

import core.course.UV;
import core.person.Person;

public class Forum {

	private ArrayList<Topic> topicList;
	private String subject;
	private UV uv;
	private boolean isProjectForum=false;
	private String projectID="";
	
	/** constructor with setting parameters. 
	 * @param subject the subject of the forum
	 * @param uv 
	 */
	public Forum(String subject,UV uv)
	{
		this.uv=uv;
		this.subject=subject;
		this.topicList = new ArrayList<Topic>();
	}
	
	public Forum(String subject,UV uv,String projectID)
	{
		this.projectID=projectID;
		this.isProjectForum=true;
		this.uv=uv;
		this.subject=subject;
		this.topicList = new ArrayList<Topic>();
	}

	
	public void setTopicList(ArrayList<Topic> topicList)
	{
		this.topicList=topicList;
	}
	
	public ArrayList<Topic> getTopicList()
	{
		return this.topicList;
	}
	
	public void createTopic(Topic topic)
	{
		this.topicList.add(topic);
	}
	
	public boolean isProjectForum()
	{
		return this.isProjectForum;
	}
	public String getProjectID()
	{
		return this.projectID;
	}
	
	
	/** creates a topic and adds it to the list  
	 * @param id 
	 * @param author the author of the topic
	 * @param title the title of the topic
	 * @param firstMessage the first message of the topic added automatically to the topic
	 * @param uv 
	 * @return Topic created
	 */
	public Topic createTopic(int id,Person author,String title,UV uv)
	{
		Topic t =  new Topic(id,author,title,uv,this);
		this.topicList.add(t);
		return t;
	}
	
	public void removeTopic(Topic topic)
	{
		this.topicList.remove(topic);
	}	
	
	public String getSubject()
	{
		return this.subject;
	}
	
	public UV getUv()
	{
		return this.uv;
	}

}
