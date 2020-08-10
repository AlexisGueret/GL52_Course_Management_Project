package core.course;


import java.util.ArrayList;
import java.util.List;

import core.document.Document;
import core.exam.Exam;
import core.forum.Forum;
import core.person.Student;
import core.person.Teacher;

/**
 * @author Thomas
 *
 */
public class ProjectGroup {

	private List <Student> studentList;
	private List<Teacher> teacherList;
	private List<Exam> testList;
	private Forum forum;
	private List<Document> documentList;
	private List<ProjectGroup> projectGroupList;
	private int mark;
	
	/**Constructor empty
	 * 	
	 */
	public ProjectGroup() {
		// TODO Auto-generated constructor stub
		this.studentList = new ArrayList<Student>();
		this.teacherList = new ArrayList<Teacher>();
		this.testList = new ArrayList<Exam>();
		this.documentList = new ArrayList<Document>();
		this.projectGroupList = new ArrayList<ProjectGroup>();
		
	}
	
	/**Temporary constructor
	 * @param _studentList
	 * @param mark 
	 */
	public ProjectGroup(List<Student> _studentList, int mark) {
		this.studentList = _studentList;
		this.mark = mark;
	}
	
	
	
	/**
	 * @param _studentList : list of students
	 * @param _teacherList : list of teachers
	 * @param _testList : list of tests
	 * @param _forum : object forum 
	 * @param _DocumentList : list of documents
	 * @param _projectGroupList : list of project group
	 */
	public ProjectGroup(List<Student> _studentList, List<Teacher> _teacherList, List<Exam> _testList, Forum _forum, List<Document> _DocumentList, List<ProjectGroup> _projectGroupList) {
		this.studentList = _studentList;
		this.teacherList = _teacherList;
		this.testList = _testList;
		this.forum = _forum;
		this.documentList = _DocumentList;
		this.projectGroupList = _projectGroupList;
	}
	
	/**
	 * @return List<Student>
	 */
	public List<Student> getStudentList(){
		return this.studentList;
	}
	
	/**
	 * @return List<Teacher>
	 */
	public List<Teacher> getTeachertList(){
		return this.teacherList;
	}
	
	/**
	 * @return List<Test>
	 */
	public List<Exam> getTestList(){
		return this.testList;
	}
	
	/**
	 * @return Forum
	 */
	public Forum getForum(){
		return this.forum;
	}

	/**
	 * @return List<Document>
	 */
	public List<Document> getDocumentList(){
		return this.documentList;
	}
	
	/**
	 * @return List<ProjectGroup>
	 */
	public List<ProjectGroup> getProjectGroupList(){
		return this.projectGroupList;
	}
	
	/**
	 * @param newStudent in the list
	 */
	public void addStudentList(Student newStudent) {
		this.studentList.add(newStudent);
	}
	
	/**
	 * @param newTeacher : new teacher in the list
	 */
	public void addTeacherList(Teacher newTeacher) {
		this.teacherList.add(newTeacher);
	}
	
	/**
	 * @param newTest : new test in the list
	 */
	public void addTest(Exam newTest ) {
		this.testList.add(newTest);
	}
	
	/**
	 * @param newForum : add a new forum
	 */
	public void setForum(Forum newForum) {
		this.forum = newForum;
	}
	
	/**
	 * @param newDocument : add a new document to the list
	 */
	public void addDocumentList(Document newDocument) {
		this.documentList.add(newDocument);
	}
	
	/**
	 * @param newProjectGroup : add a new project group to the list
	 */
	public void addProjectGroupList(ProjectGroup newProjectGroup) {
		this.projectGroupList.add(newProjectGroup);
	}
	
	/**Print the studentList
	 * 
	 */
	public void printStudentList() {
		this.studentList.forEach((n)->System.out.println(n));
	}
	
	/**Print the list of teachers
	 * 
	 */
	public void printTeacherList() {
		this.teacherList.forEach((n)->System.out.println(n));
	}
	
	/**Print the list of exams
	 * 
	 */
	public void printExamList() {
		this.testList.forEach((n)->System.out.println(n));
	}

	/**print forum value
	 * 
	 */
	public void printForum() {
		System.out.println(this.forum);
	}
	
	/**
	 * 
	 */
	public void printDocumentList() {
		this.documentList.forEach((n)->System.out.println(n));
	}
	
	/**
	 * 
	 */
	public void printProjectGroupList() {
		this.projectGroupList.forEach((n)->System.out.println(n));
	}
	
	/**
	 * @return the mark
	 */
	public int getMark() {
		return this.mark;
	}


}
