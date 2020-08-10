package hmi.javaFx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import bdd.ExamQuery;
import bdd.PersonalPageQuery;
import core.course.UV;
import core.exam.Exam;
import core.person.Student;
import core.person.Teacher;
import hmi.console.ExamOutPut;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
 
/**
 * @author audra
 *
 */
public class ExamList extends Application{

	private BorderPane borderPanRoot;
	Scene examListScene;

	
	/**
	 * Constructor
	 * @param top
	 */
	public ExamList() 
	{
		this.borderPanRoot = new BorderPane();
	}
	
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("EXAM LIST");
		this.examListScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.examListScene);
		this.borderPanRoot.setTop(ConnectionPage.getLogoTop("Exam", primaryStage));
		VBox mainContent = new VBox();
		HBox menu = HomeInterface.getButtonHBox(primaryStage);
		HBox boxList = new HBox();
		HBox boxButton = new HBox();
		
		ListView<String> examListView = new ListView<>();
		List<Exam> examList = ExamQuery.getListExamFromBDD(HomeInterface.getUv());
		for(Exam exam : examList)
			examListView.getItems().add(exam.getTitle());
		
		boxList.setPadding(new Insets(15));
		boxList.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.9));
		
		// Buttons
		if(ConnectionPage.state.equals("teacher"))
		{
			Button deleteAnExamButton = new Button("Delete");
			deleteAnExamButton.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.2));
			deleteAnExamButton.setAlignment(Pos.CENTER);
			deleteAnExamButton.setOnAction(e->{
					deleteAnExam(primaryStage, examListView, examList);
			});
			boxButton.getChildren().add(deleteAnExamButton);
		}
			
		Button submitButton = new Button("Start the exam");
		submitButton.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.2));
		submitButton.setAlignment(Pos.CENTER);
		submitButton.setOnAction(e->{
				submitAction(primaryStage, examListView, examList);
		});
		boxButton.getChildren().add(submitButton);
		
		if(ConnectionPage.state.equals("teacher"))
		{
			Button addAnExamButton = new Button("Add an exam");
			addAnExamButton.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.2));
			addAnExamButton.setAlignment(Pos.CENTER);
			addAnExamButton.setOnAction(e->{
				addAnExam(primaryStage);
			});
			boxButton.getChildren().add(addAnExamButton);
		}
		
		
		boxList.getChildren().add(examListView);
		boxList.setAlignment(Pos.CENTER);
		boxButton.setAlignment(Pos.CENTER);
		mainContent.getChildren().addAll(menu, boxList, boxButton);	
		this.borderPanRoot.setCenter(mainContent);
		primaryStage.show();
	}
	
	/** 
	 * Gives an action to submit button
	 * @param primaryStage 
	 * @param listView 
	 * @param examList 
	 */
	public static void submitAction(Stage primaryStage, ListView<String> listView, List<Exam> examList)
	{
		ObservableList<String> stringRegister = listView.getSelectionModel().getSelectedItems();
		if(stringRegister.size() != 0)
		{
			for(String m : stringRegister) 
			{
				for(Exam exam : examList)
				{
					if(m.equals(exam.getTitle())) 
					{
						Student student = new Student(ConnectionPage.lastNameConnected, ConnectionPage.firstNameConnected);
						if(ExamQuery.hasAlreadyMadeATry(exam, student))
						{
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("Connection");
					        alert.setHeaderText(null);
					        float mark = ExamQuery.getExamMark(exam, student);
					        alert.setContentText("You already have try this exam and got the mark " + mark + "/20");
					        alert.showAndWait();
						}
						else
						{
							ExamPage examPage = new ExamPage(exam, student, true);
							primaryStage.close();
							try 
							{
								examPage.start(new Stage());
							} 
							catch (Exception e) {e.printStackTrace();}
						}	
					}
				}
			}
		}		
	}
	
	/**
	 * Deletes the selected exam from the list
	 * @param primaryStage : Stage, the primary stage
	 * @param listView : ListView<String>, the listview that show the exam list
	 * @param examList : List<Exam>, the exam list
	 */
	public static void deleteAnExam(Stage primaryStage, ListView<String> listView, List<Exam> examList)
	{
		ObservableList<String> stringRegister = listView.getSelectionModel().getSelectedItems();
		if(stringRegister.size() != 0)
		{
			for(String m : stringRegister) 
			{
				for(Exam exam : examList)
				{
					if(m.equals(exam.getTitle())) 
					{
						ExamQuery.deteleAnExamFromDataBase(exam);
						primaryStage.close();
						try 
						{
							ExamList pageExamList = new ExamList();
							pageExamList.start(new Stage());
						} 
						catch (Exception e) {e.printStackTrace();}
					}
				}
			}
		}		
	}
	
	
	/**
	 * Create an dialog to get exam information
	 * @param primaryStage : Stage
	 */
	public static void addAnExam(Stage primaryStage)
	{
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Enter the exam information");
		
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Next", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField examTitleTF = new TextField();
		examTitleTF.setPromptText("Exam title");
		TextField timeLimiteTF = new TextField();
		timeLimiteTF.setPromptText("Time limite");

		grid.add(new Label("Exam title:"), 0, 0);
		grid.add(examTitleTF, 1, 0);
		grid.add(new Label("Time limite in sec:"), 0, 1);
		grid.add(timeLimiteTF, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		examTitleTF.textProperty().addListener((observable, oldValue, newValue) -> 
		{
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> examTitleTF.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(examTitleTF.getText(), timeLimiteTF.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		// Creation of the exam
		result.ifPresent(examInfo -> {
			String title =  examInfo.getKey().toString();
			if(isValidTimeLimit(examInfo.getValue().toString()))
			{
				long timeLimite =  Long.valueOf(examInfo.getValue().toString());

				Exam exam = new Exam(ExamQuery.getMaxExamId() + 1,
						HomeInterface.uv.getCode(),
						title,
						new Teacher(ConnectionPage.lastNameConnected, ConnectionPage.firstNameConnected),
						true,
						timeLimite);

				ExamPage examPage = new ExamPage(exam, null, false);
				primaryStage.close();
				try 
				{
					examPage.start(new Stage());
				} 
				catch (Exception e) {e.printStackTrace();}
			}
		});	
	}
	
	/**
	 * Indicates if the value entered in the text field time lime is possible
	 * @param value : String, the text in the textField
	 * @return boolean
	 */
	public static boolean isValidTimeLimit(String value)
	{
		try 
		{
			Long v = Long.parseLong(value);
			if(v < 0)
				return false;
		} 
		catch (NumberFormatException nfe) 
		{
			return false;
		}
		return true;
	}
	
}
