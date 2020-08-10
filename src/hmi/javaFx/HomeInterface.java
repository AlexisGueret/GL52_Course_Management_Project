package hmi.javaFx;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.course.UV;
import core.forum.Forum;
import core.forum.Topic;
import core.person.Person;
import core.person.Student;
import bdd.ConnectionToBDD;
import bdd.PersonalPageQuery;
import bdd.ProjectQuery;
import bdd.TopicQuery;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * @author audra
 *
 */
public class HomeInterface extends Application {
	
	private Scene scene;
	private BorderPane borderPanRoot = new BorderPane();
	private VBox mainContent = new VBox();
	public static UV uv;
	static Stage homeStage ;
	private static Forum forum;
	
	/**Constructor
	 * @param uv 
	 * 
	 */
	public HomeInterface(UV uv) {
		HomeInterface.setUv(uv);
	}
	
	
	/**
	 * @param prim 
	 * @param primaryStage 
	 * @return HBox of buttons
	 */
	public static HBox getButtonHBox(Stage prim) {
		HBox listButton = new HBox();
		
		Button button1 = new Button("UV");	
		button1.setOnAction((event) -> {
			// Button was clicked
			prim.close();
			try {
				HomeInterface hI= new HomeInterface(HomeInterface.uv);
				Stage newStage = new Stage();
				hI.start(newStage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		button1.setAlignment(Pos.CENTER);
		button1.prefWidthProperty().bind(listButton.widthProperty());
		
		Button button2 = new Button("Exam List");
		button2.prefWidthProperty().bind(listButton.widthProperty());
		button2.setAlignment(Pos.CENTER);
		button2.setOnAction((event) -> {
			// Button was clicked
			prim.close();
			try {
				ExamList pageExamList = new ExamList();
				Stage newStage = new Stage();
				pageExamList.start(newStage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		Button button3 = new Button("Forum");
		button3.prefWidthProperty().bind(listButton.widthProperty());
		button3.setAlignment(Pos.CENTER);
		button3.setOnAction((event) -> {
			// Button was clicked
			prim.close();
			try {
				createForum();
				ForumInterface forumInterface = new ForumInterface(forum);
				Stage newStage = new Stage();
				forumInterface.start(newStage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		Button button4 = new Button("Project Group");
		button4.prefWidthProperty().bind(listButton.widthProperty());
		button4.setAlignment(Pos.CENTER);
		button4.setOnAction((event) -> {
			// Button was clicked
			prim.close();
			try {
				if(ProjectQuery.hasAProject(getUv().getCode())){
					try {
						ProjectGroupInterface project = new ProjectGroupInterface(getUv().getCode(),ProjectQuery.getNumberProjectIn(HomeInterface.uv.getCode()));
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
		});
		Button button5 = new Button("Course");
		button5.prefWidthProperty().bind(listButton.widthProperty());
		button5.setAlignment(Pos.CENTER);
		button5.setOnAction((event) -> {
			// Button was clicked
			try {
				prim.close();
				CourseInterface courseInterface = new CourseInterface();
				Stage newStage = new Stage();
				courseInterface.start(newStage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		listButton.getChildren().addAll(button1,button5,button3,button4,button2);
		return listButton;
		
	}
	
	/**
	 * Creates a forum by getting all the topics and messages associated with the current UV
	 * @throws SQLException
	 */
	public static void createForum() throws SQLException
	{
		forum = new Forum("Forum : " + getUv().getCode(),getUv());
		forum.setTopicList(TopicQuery.getTopicList(getUv(),forum));
	}
    
	/**
	 * @param prim 
	 *  
	 * @throws SQLException 
	 * @return VBox of the content
	 * @throws SQLException 
	 * @throws 	 
	 */
	public static VBox getListAndDescriptionHB(Stage prim) throws SQLException {
		
		// The description and list of students
		VBox row = new VBox();
		HBox listAndDescription = new HBox();
		HBox list = new HBox();
		HBox descriptionLayer = new HBox();
		
		listAndDescription.setPadding(new Insets(20,0,0,0));
		Label description = new Label(getUv().getTitle());
		description.setStyle("-fx-font: 14 Arial;");
		description.prefWidthProperty().bind(descriptionLayer.widthProperty());
		description.setWrapText(true);
		descriptionLayer.getChildren().add(description);
		descriptionLayer.prefWidthProperty().bind(listAndDescription.widthProperty().multiply(0.75));
		descriptionLayer.setPadding(new Insets(0,0,0,10));
		
		ListView<String> students = new ListView<>();
		students.setMinWidth(list.getWidth()/4);
		for(Student st : PersonalPageQuery.getStudentList(getUv().getCode()) ) {
			String s = st.getLastName()+" " +st.getFirstName();
			students.getItems().add(s);
		}
		students.prefWidthProperty().bind(list.widthProperty());
		list.getChildren().add(students);
		list.prefWidthProperty().bind(listAndDescription.widthProperty().multiply(0.25));
		listAndDescription.getChildren().addAll(list,descriptionLayer);

		
		if(ConnectionPage.state.equals("teacher")) {
			HBox addStudentBox = new HBox();
			
			Button button = new Button("add person");
			button.setAlignment(Pos.CENTER);
			button.setOnAction(e->{
				insertNameAndSurname(prim,"add");
			});
			button.prefWidthProperty().bind(addStudentBox.widthProperty().multiply(0.4));
			Button removeButton = new Button("remove person");
			removeButton.setAlignment(Pos.CENTER);
			removeButton.prefWidthProperty().bind(addStudentBox.widthProperty().multiply(0.4));
			removeButton.setOnAction(e->{
				insertNameAndSurname(prim,"remove");
			});
			addStudentBox.getChildren().addAll(button,removeButton);
			addStudentBox.prefWidthProperty().bind(listAndDescription.widthProperty().multiply(0.33));
			addStudentBox.setAlignment(Pos.CENTER);
			addStudentBox.setPadding(new Insets(10,0,20,0));

			row.getChildren().addAll(listAndDescription,addStudentBox);
		}
		else {
			row.getChildren().addAll(listAndDescription);
		}
		
		return row;

	}
	
	/**It will ask for names and call the indert method in database
	 * @param prim 
	 * @param removeOrAdd 
	 * 
	 */
	public static void insertNameAndSurname(Stage prim,String removeOrAdd) {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Enter firstName lastName");
		
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Next", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField lastName = new TextField();
		lastName.setPromptText("LastName");
		TextField firstName = new TextField();
		firstName.setPromptText("FirstName");

		grid.add(new Label("LastName:"), 0, 0);
		grid.add(lastName, 1, 0);
		grid.add(new Label("FirstName:"), 0, 1);
		grid.add(firstName, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		lastName.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> lastName.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(lastName.getText(), firstName.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			String lName =  usernamePassword.getKey().toString();
			String fName =  usernamePassword.getValue().toString();
			if(removeOrAdd.equals("add")) {
				try {
					if(PersonalPageQuery.isInDataBase(lName,fName)) {
					PersonalPageQuery.addStudentBase(lName,fName);
				}
					else {
						Alert alert = new Alert(AlertType.INFORMATION);
				        alert.setTitle("Student not found");
				 
				        // Header Text: null
				        alert.setHeaderText(null);
				        alert.setContentText("The Student isn't in the System");
				        alert.showAndWait();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					PersonalPageQuery.removeStudentBase(lName,fName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		try {
            prim.close();
            HomeInterface homeInterface = new HomeInterface(HomeInterface.uv);
            Stage newStage = new Stage();
            homeInterface.start(newStage);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
	}
	
	
	
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException,SQLException {
			// TODO Auto-generated catch block
        primaryStage.setTitle("Home Page");        
        this.scene= new Scene(this.borderPanRoot,800,600);
        primaryStage.setScene(this.scene);
        this.borderPanRoot.setTop(ConnectionPage.getLogoTop(getUv().getCode(), primaryStage));
        this.mainContent.getChildren().addAll(getButtonHBox(primaryStage), getListAndDescriptionHB(primaryStage));
        this.borderPanRoot.setCenter(this.mainContent);
        primaryStage.show();
        homeStage = primaryStage;
    }


	public static UV getUv() {
		return uv;
	}


	public static void setUv(UV uv) {
		HomeInterface.uv = uv;
	}
}
