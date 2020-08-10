package hmi.javaFx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import bdd.ProjectQuery;
import bdd.TopicQuery;
import core.course.ProjectGroup;
import core.document.Document;
import core.forum.Forum;
import core.person.Student;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
/**
 * @author audra
 *
 */
public class ProjectGroupInterface extends Application{

	private HBox topLayout = new HBox();
	private BorderPane borderPanRoot = new BorderPane();
	Scene projectListScene;
	private ProjectGroup projectGroup;
	static String num;
	
	/** constructor with the projectGroup related to the student
	 * @param code 
	 * @param nb_groupe 
	 * @param pg 
	 * @throws SQLException 
	 * 
	 * 
	 */
	public ProjectGroupInterface(String code,int nb_groupe) throws SQLException {
		num = Integer.toString(nb_groupe);
		this.setProjectGroup(ProjectQuery.getProjectInformations(code,nb_groupe));
	}
	
	/**it will return the graphical main content of the page with the rows
	 * @param prim 
	 *  
	 * @return the VBox total row
	 * @throws SQLException 
	 * 
	 * 
	 */
	public VBox getMainContent(Stage prim) throws SQLException {
		VBox row = new VBox();
		HBox tHbox = new HBox();
		HBox tHboxLeft = new HBox();
		HBox tHboxRight = new HBox();
		HBox botHoxLeft = new HBox();
		HBox botHoxRight = new HBox();
		HBox botHBox = new HBox();

		
		ListView<String> listStudent = new ListView<>();
		listStudent.prefWidthProperty().bind(tHboxLeft.widthProperty().multiply(0.6));
		listStudent.setMaxHeight(115);
		for(Student st : getProjectGroup().getStudentList()) {
			String nameAndFirstName = st.getLastName()+" "+st.getFirstName();
			listStudent.getItems().add(nameAndFirstName);
		}
		tHboxLeft.getChildren().add(listStudent);
		tHboxLeft.prefWidthProperty().bind(tHbox.widthProperty().multiply(0.3));
		tHboxLeft.setAlignment(Pos.CENTER);
		Button openPDF = new Button("Subject");
		openPDF.setAlignment(Pos.CENTER);
		openPDF.setOnAction(e->{
			try {
				Document.openpdfproject(HomeInterface.uv.getCode(),num,num+".pdf");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		openPDF.setStyle("-fx-font: 14 arial;");
		
		openPDF.prefWidthProperty().bind(tHboxRight.widthProperty().multiply(0.2));
		openPDF.prefHeightProperty().bind(tHboxRight.heightProperty().multiply(0.3));
		Button buttonForum = new Button("Forum");
		buttonForum.setOnAction((event) -> {
			// Button was clicked
			prim.close();
			try {
				Forum forum = new Forum("Forum : " + HomeInterface.getUv().getCode(),HomeInterface.getUv(),num);
				forum.setTopicList(TopicQuery.getTopicList(HomeInterface.getUv(),num,forum));
				ForumInterface fI= new ForumInterface(forum);
				Stage newStage = new Stage();
				fI.start(newStage);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		buttonForum.setAlignment(Pos.CENTER);
		buttonForum.prefWidthProperty().bind(tHboxRight.widthProperty().multiply(0.2));
		buttonForum.prefHeightProperty().bind(tHboxRight.heightProperty().multiply(0.3));
		buttonForum.setStyle("-fx-font: 14 arial;");
		
		Button buttonAdd = new Button("Add file");
		buttonAdd.setOnAction((event) -> {
			// Button was clicked
			try {
				Document.saveFileproject(HomeInterface.uv.getCode(), num);
				prim.close();
				try {
					if(ProjectQuery.hasAProject(HomeInterface.uv.getCode())){
						try {
							ProjectGroupInterface project = new ProjectGroupInterface(HomeInterface.uv.getCode(),Integer.parseInt(num));
							Stage newStage = new Stage();
							project.start(newStage);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						ListProjectInterface pgr = new ListProjectInterface();
						Stage newStage = new Stage();
						pgr.start(newStage);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		buttonAdd.setAlignment(Pos.CENTER);
		buttonAdd.prefWidthProperty().bind(tHboxRight.widthProperty().multiply(0.2));
		buttonAdd.prefHeightProperty().bind(tHboxRight.heightProperty().multiply(0.3));
		buttonAdd.setStyle("-fx-font: 14 arial;");

		if(ConnectionPage.state.equals("teacher")) {
			Button addNote = new Button("Grade");
			addNote.setStyle("-fx-font: 14 arial;");

			addNote.setOnAction(e->{
				try {
					alertGradeMethod(prim);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			Button buttonAddSubject = new Button("Add subject");
			buttonAddSubject.setOnAction((event) -> {
				// Button was clicked
				try {
					Document.saveFileprojectsubject(HomeInterface.uv.getCode(), num,num);
					prim.close();
					try {
						if(ProjectQuery.hasAProject(HomeInterface.uv.getCode())){
							try {
								ProjectGroupInterface project = new ProjectGroupInterface(HomeInterface.uv.getCode(),Integer.parseInt(num));
								Stage newStage = new Stage();
								project.start(newStage);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else {
							ListProjectInterface pgr = new ListProjectInterface();
							Stage newStage = new Stage();
							pgr.start(newStage);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			buttonAddSubject.setAlignment(Pos.CENTER);
			buttonAddSubject.prefWidthProperty().bind(tHboxRight.widthProperty().multiply(0.2));
			buttonAddSubject.prefHeightProperty().bind(tHboxRight.heightProperty().multiply(0.3));
			buttonAddSubject.setStyle("-fx-font: 14 arial;");
			
			addNote.setAlignment(Pos.CENTER);
			addNote.prefWidthProperty().bind(tHboxRight.widthProperty().multiply(0.2));
			addNote.prefHeightProperty().bind(tHboxRight.heightProperty().multiply(0.3));
			tHboxRight.getChildren().addAll(openPDF,buttonForum,buttonAddSubject, buttonAdd,addNote);
			tHboxRight.prefWidthProperty().bind(tHbox.widthProperty().multiply(0.6));
			tHboxRight.setAlignment(Pos.CENTER_RIGHT);
		}
		else {
			tHboxRight.prefWidthProperty().bind(tHbox.widthProperty().multiply(0.6));
			tHboxRight.getChildren().addAll(openPDF,buttonForum,buttonAdd);
			tHboxRight.setAlignment(Pos.CENTER_RIGHT);
		}
		
		tHboxRight.setPadding(new Insets(0,0,20,0));
		tHbox.getChildren().addAll(tHboxLeft,tHboxRight);
		//bottom part of the main content
		
		Label markLabel = new Label(""+getProjectGroup().getMark()+"/20");
		markLabel.setStyle("-fx-font: 25 arial;");
		markLabel.setAlignment(Pos.CENTER);
		markLabel.prefWidthProperty().bind(botHoxLeft.widthProperty());
		botHoxLeft.getChildren().add(markLabel);

		botHoxLeft.prefWidthProperty().bind(botHBox.widthProperty().multiply(0.25));
		
		ListView<String> listDoc = new ListView<String>();
		for(String s : Document.seeRepositoryproject(HomeInterface.uv.getCode(),num)) {
				String r = num+".pdf";
				if (!s.equals(r))
					listDoc.getItems().add(s);
			}

		listDoc.prefWidthProperty().bind(botHoxRight.widthProperty().multiply(0.6));
		listDoc.setMinHeight(150);
		botHoxRight.getChildren().add(listDoc);
		botHoxRight.prefWidthProperty().bind(botHBox.widthProperty().multiply(0.75));
		botHoxRight.prefHeightProperty().bind(botHBox.heightProperty());
		botHoxRight.setAlignment(Pos.CENTER);
		botHBox.getChildren().addAll(botHoxLeft,botHoxRight);
		tHbox.setPadding(new Insets(0,0,50,0));
		botHBox.prefWidthProperty().bind(row.widthProperty());
		tHbox.prefWidthProperty().bind(row.widthProperty());
		HBox selectionMenu = HomeInterface.getButtonHBox(prim);
		selectionMenu.setPadding(new Insets(0,0,20,0));
		row.getChildren().addAll(selectionMenu,tHbox,botHBox);
		listDoc.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {

	        	try {
	        		if(listDoc.getSelectionModel().getSelectedItem()!=null)
	        			Document.openpdfproject(HomeInterface.uv.getCode(), num, listDoc.getSelectionModel().getSelectedItem());
				} catch (Exception e) {
					e.printStackTrace();
				}
	            
	        }
	    });
		return row;
	}
	
	/**This method will allow the user the add a grade to the project
	 * @param prim 
	 * @throws Exception 
	 * 
	 */
	public static void alertGradeMethod(Stage prim) throws Exception {
		TextInputDialog messageDialog = new TextInputDialog();
    	messageDialog.setTitle("Add the Grade");
    	messageDialog.setHeaderText("Setup the grade");
    	messageDialog.setContentText("Please enter the value");

    	// Traditional way to get the response value.
    	Optional<String> result = messageDialog.showAndWait();
    	if (result.isPresent()){
    		ProjectQuery.addGradeDataBase(Integer.parseInt(num),Integer.parseInt(result.get()));
    		prim.close();
    		ProjectGroupInterface pgi = new ProjectGroupInterface(HomeInterface.uv.getCode(),Integer.parseInt(num));
            Stage newStage = new Stage();
            pgi.start(newStage);
    	}
	}
	
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Project Group");
		this.projectListScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.projectListScene);
		this.topLayout = ConnectionPage.getLogoTop("Project Group", primaryStage);
		this.borderPanRoot.setTop(this.topLayout);
		this.borderPanRoot.setCenter(getMainContent(primaryStage));
		primaryStage.show();
	}

	/**
	 * @return the project Group
	 */
	public ProjectGroup getProjectGroup() {
		return this.projectGroup;
	}

	/**
	 * @param projectGroup
	 */
	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

}
