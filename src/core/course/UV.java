package core.course;
import bdd.ConnectionToBDD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bdd.ConnectionToBDD;
import core.document.Document;
import core.forum.Forum;
import core.person.Student;

/**
 * 
 * @author Thomas
 *
 */
public class UV {
	
	private List<Student> studentList;
	private Document subject;
	private Forum forum;
	private float mark;
	private List<Document> fileList;
	private String code;
	private String title;
	
	/** Constructor empty
	 * 
	 */
	public UV() {
		// TODO Auto-generated constructor stub
		this.studentList = new ArrayList<Student>();
		this.fileList = new ArrayList<Document>();
	}
	
	/**
	 * @param code
	 * @param title
	 * @param _studentList 
	 */
	public UV(String code, String title, List<Student> _studentList ) {
		this.code = code;
		this.title = title;
		this.studentList = _studentList;
	}
	
	/** Constructor with all parameters to setup the UV
	 * @param _studentList : list of the students
	 * @param _subject : the subject of the UV
	 * @param _forum : Forum related to the UV
	 * @param _mark : mark associated 
	 * @param _fileList : list of documents related to the UV
	 */
	public UV(List<Student> _studentList, Document _subject, Forum _forum, float _mark, List<Document> _fileList ) {
		this.studentList = _studentList;
		this.subject = _subject;
		this.forum = _forum;
		this.mark = _mark;
		this.fileList = _fileList;
	}
	
	
	/**
	 * @return List<Student>
	 */
	public List<Student> getListStudent(){
		return this.studentList;
	}
	
	/**
	 * @return Document
	 */
	public Document getSubjec(){
		return this.subject;
	}
	
	/**
	 * @return Forum
	 */
	public Forum getForum(){
		return this.forum;
	}

	/**
	 * @return the valeu of the mark
	 */
	public float getMark(){
		return this.mark;
	}
	
	/**
	 * @return List<Document>
	 */
	public List<Document> getListDocument(){
		return this.fileList;
	}
	
	/**
	 * @param _subject : new subject
	 */
	public void setSubject(Document _subject) {
		this.subject = _subject;
	}
	
	/**
	 * @param _forum : new forum
	 */
	public void setForum(Forum _forum) {
		this.forum = _forum;
	}
	
	/**
	 * @param _mark : new mark
	 */
	public void setMark(float _mark) {
		this.mark = _mark;
	}
	
	/** add one student to the list for one UV
	 * @param stud
	 */
	public void addStudentList(Student stud) {
		this.studentList.add(stud);
	}
	

	/** Add a Document to the list
	 * @param newDocument
	 */
	public void addFileList(Document newDocument) {
		this.fileList.add(newDocument);
	}
	
	/**print the list of students
	 * 
	 */
	public void printStudentList() {
		this.studentList.forEach((n)->System.out.println(n.getLastName()));
	}
	
	/** print the subject value
	 * 
	 */
	public void printSubject() {
		System.out.println(this.subject);
	}
	
	/** print the list for files
	 * 
	 */
	public void printFileList() {
		this.fileList.forEach((n)->System.out.println(n));
	}
	
	/**
	 * @return the title of the uv
	 */
	public String getTitle() {
		return this.title;
	}
	
	public List<Student> getStudentList(){
		return this.studentList;
	}
	
	/**
	 * @return code of the uv
	 */
	public String getCode() {
		return this.code;
	}

}
