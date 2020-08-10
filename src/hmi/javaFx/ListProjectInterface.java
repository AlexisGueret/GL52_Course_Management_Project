package hmi.javaFx;


import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import bdd.PersonalPageQuery;
import bdd.ProjectQuery;
import core.course.UV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**This class will allow a student without a project to register for one
 * @author Thomas
 *
 */
public class ListProjectInterface extends Application{
	
	private HBox topLayout = new HBox();
	private BorderPane borderPanRoot = new BorderPane();
	Scene projectListScene;
	
	/**Empty constructeur for now
	 * 
	 */
	public ListProjectInterface() {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Project List");
		this.projectListScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.projectListScene);
		this.topLayout = ConnectionPage.getLogoTop("Project List", primaryStage);
		this.borderPanRoot.setTop(this.topLayout);
		this.borderPanRoot.setCenter(getMainContent(primaryStage));
		primaryStage.show();
	}
	
	/**It will bet the main component of the page : the list of all project in a given UV
	 * @param primi
	 * @return the VBox element
	 * @throws SQLException 
	 */
	public static VBox getMainContent(Stage primi) throws SQLException {
		VBox row = new VBox();
		HBox titleListProjectBox = new HBox();
		HBox listSubjectsBox = new HBox();
		HBox buttonSubmitBox = new HBox();
		
		Label titleTop = new Label("Here is the list of subjects for the semester");
		titleTop.setStyle("-fx-font: 30 arial;");
		titleListProjectBox.getChildren().add(titleTop);
		titleListProjectBox.setAlignment(Pos.CENTER);
		titleListProjectBox.setPadding(new Insets(10,0,0,0));
		
		ListView<String> listSubject = new ListView<>();
		listSubject.prefWidthProperty().bind(listSubjectsBox.widthProperty());

		for(int i=1;i<=ProjectQuery.getNumberProject(HomeInterface.uv.getCode());i++) {
			String temp = Integer.toString(i) +" "+ProjectQuery.getTitleProject(i)+ "  "+Integer.toString(ProjectQuery.getNumberPersonInProject(HomeInterface.uv.getCode(),i))+ "/" +ProjectQuery.numberMaxStudent(i);
			listSubject.getItems().add(temp);
		}
		listSubjectsBox.getChildren().add(listSubject);
		listSubjectsBox.setAlignment(Pos.CENTER);
		listSubjectsBox.setPadding(new Insets(10,0,0,0));
		listSubjectsBox.prefHeightProperty().bind(row.heightProperty().multiply(0.50));

		Button submit = new Button("Submit your choice");
		submit.setOnAction(e->{
			try {
				submitAction(primi,listSubject);
			} catch (FileNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		if(ConnectionPage.state.equals("teacher")) {
			Button addProject = new Button("Add Project");
			addProject.setOnAction(e->{
				try {
					addProjectButtonPressed(primi);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			buttonSubmitBox.getChildren().addAll(submit,addProject);
			buttonSubmitBox.setAlignment(Pos.CENTER);
			buttonSubmitBox.setPadding(new Insets(10,0,0,0));
		}
		else {
			buttonSubmitBox.getChildren().add(submit);
			buttonSubmitBox.setAlignment(Pos.CENTER);
			buttonSubmitBox.setPadding(new Insets(10,0,0,0));
		}
		
		HBox selectionMenu = HomeInterface.getButtonHBox(primi);
		selectionMenu.setPadding(new Insets(0,0,20,0));
		row.getChildren().addAll(selectionMenu,titleListProjectBox,listSubjectsBox,buttonSubmitBox);
		return row;
		
	}
	
	/**This method will put 1 alerts asking for the 2 parameters
	 * @param prim 
	 * 
	 */
	public static void addProjectButtonPressed(Stage prim) {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Enter firstName lastName");
		
		// Set the button types.
		ButtonType addButton = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField title= new TextField();
		title.setPromptText("LastName");
		TextField numberInProject = new TextField();
		numberInProject.setPromptText("Number students");

		grid.add(new Label("Title:"), 0, 0);
		grid.add(title, 1, 0);
		grid.add(new Label("Number Students:"), 0, 1);
		grid.add(numberInProject, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(addButton);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		title.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> title.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == addButton) {
		        return new Pair<>(title.getText(), numberInProject.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(usernamePassword -> {
			String lName =  usernamePassword.getKey().toString();
			int	 fName =  Integer.parseInt(usernamePassword.getValue());
			try {
				ProjectQuery.createProjectQuery(lName,fName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});	
		try {
            prim.close();
            ListProjectInterface lpi = new ListProjectInterface();
            Stage newStage = new Stage();
            lpi.start(newStage);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
	}
	
	
	
	
	/**
	 * @param primi
	 * @param l
	 * @throws Exception 
	 */
	public static void submitAction(Stage primi, ListView<String> l ) throws Exception {
		ObservableList<String> stringRegister = l.getSelectionModel().getSelectedItems();
		if(stringRegister.size()!=0) {
			for(String m : stringRegister) {
				if(ConnectionPage.state.equals("teacher")) {
					primi.close();
					ProjectGroupInterface project = new ProjectGroupInterface(HomeInterface.getUv().getCode(),Character.getNumericValue(m.charAt(0)));
					Stage newStage = new Stage();
					project.start(newStage);
				}
				else {
					if(ProjectQuery.getNumberPersonInProject(HomeInterface.uv.getCode(),Character.getNumericValue(m.charAt(0)))<4) {
						ProjectQuery.addPersonToProject(Character.getNumericValue(m.charAt(0)));
						primi.close();
						ProjectGroupInterface project = new ProjectGroupInterface(HomeInterface.getUv().getCode(),Character.getNumericValue(m.charAt(0)));
						Stage newStage = new Stage();
						project.start(newStage);
					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION);
				        alert.setTitle("Invalid choice");
				 
				        // Header Text: null
				        alert.setHeaderText(null);
				        alert.setContentText("You cannot choose this subject, it is full");
				        alert.showAndWait();
					}
				}
			}
		}
	}
}
