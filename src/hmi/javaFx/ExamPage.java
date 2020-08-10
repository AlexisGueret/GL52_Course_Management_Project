package hmi.javaFx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bdd.ExamQuery;
import core.exam.Choice;
import core.exam.Exam;
import core.exam.Question;
import core.exam.Try;
import core.person.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author loic
 * Page that show an exam
 */
public class ExamPage extends Application
{
	
	private BorderPane borderPanRoot;
	private BorderPane borderPanRoot2;
	private Scene connectionToApp;
	private Exam exam;
	private QuestionVBox currentQuestionBox;
	private List<QuestionVBox> listQuestionBox;
	private Try examTry;
	private boolean isATry;
	
	/**
	 * Constructor
	 * @param _exam : Exam, the Exam to print
	 * @param _student 
	 * @param _isATry : boolean, indicates if this page is used to pass the exam or to add and exam
	 */
	public ExamPage(Exam _exam, Student _student, boolean _isATry) 
	{
		this.borderPanRoot = new BorderPane();
		this.exam = _exam;
		this.isATry = _isATry;
		if(_isATry)
			this.examTry = new Try(this.exam, _student);
		else
			this.examTry = null;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Examen");
		showExam(primaryStage);
	}
	
	 /**
	  * Show the exam
	  * @param primaryStage : Stage
	  */
	public void showExam(Stage primaryStage)
	{
		this.connectionToApp = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.connectionToApp);
		VBox mainContent = new VBox();
		
		// Title
		VBox titleBox = new VBox();
		Label labelNewTitle = new Label();
		if(this.isATry)
		{
			// Show time limit
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date maxEndDate = new Date();
			maxEndDate.setTime(this.examTry.getStartingHour().getTime() + this.exam.getTimeLimite()*1000);
			// Rest of the title
			labelNewTitle.setText(this.exam.getTitle() + "(Until: " + simpleDateFormat.format(maxEndDate) + ")");
		}
		else
		{
			labelNewTitle.setText(this.exam.getTitle() + " - Time limit: " + this.exam.getTimeLimite() + "s");
		}
		labelNewTitle.setStyle("-fx-font: 26 arial;");
		labelNewTitle.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(labelNewTitle);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(20));
		
		
		// Box and label question list
		HBox questionButtonBoxCentered = new HBox();
		HBox questionButtonBox = new HBox();
		Label labelQuestionList = new Label("Question list: ");
		labelQuestionList.setPadding(new Insets(10));
		labelQuestionList.setStyle("-fx-font: 18 arial;");
		questionButtonBox.getChildren().add(labelQuestionList);
		
		// Creation of the button and questions
		createQuestionButtonAndBox(questionButtonBox, mainContent);
		
		// Positioning the buttons
		questionButtonBox.setMaxSize(140 + 60 * this.exam.getQuestionList().size(), 40);
		questionButtonBox.setAlignment(Pos.CENTER);
		questionButtonBox.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.9));
		questionButtonBoxCentered.setAlignment(Pos.CENTER);
		questionButtonBoxCentered.setPadding(new Insets(30));
		questionButtonBoxCentered.getChildren().add(questionButtonBox);
		titleBox.getChildren().add(questionButtonBoxCentered);
		this.borderPanRoot.setTop(titleBox);
		
		if(this.currentQuestionBox != null)
			mainContent.getChildren().add(this.currentQuestionBox);
		mainContent.setAlignment(Pos.CENTER);
		this.borderPanRoot.setCenter(mainContent);
		
		// Submit button
		HBox bottomBox = new HBox();
		
		if(this.isATry)
		{
			Button submitButton = new Button("Submit");
			submitButton.setStyle("-fx-font: 20 arial;");
			submitButton.setPadding(new Insets(15));
			submitButton.setAlignment(Pos.CENTER);
			submitButton.setOnAction(e->{
				submitAnswer(primaryStage);
			});
			bottomBox.getChildren().add(submitButton);
		}
		else
		{
			Button addQuestionButton = new Button("Add a question");
			addQuestionButton.setStyle("-fx-font: 20 arial;");
			addQuestionButton.setPadding(new Insets(15));
			addQuestionButton.setAlignment(Pos.CENTER);
			addQuestionButton.setOnAction(e->{
				addQuestion(primaryStage);
			});

			Button submitButton = new Button("Finish creation");
			submitButton.setStyle("-fx-font: 20 arial;");
			submitButton.setPadding(new Insets(15));
			submitButton.setAlignment(Pos.CENTER);
			submitButton.setOnAction(e->{
				finishExamCreation(primaryStage);
			});
			
			Button deleteQuestionButton = new Button("Delete the question");
			deleteQuestionButton.setStyle("-fx-font: 20 arial;");
			deleteQuestionButton.setPadding(new Insets(15));
			deleteQuestionButton.setAlignment(Pos.CENTER);
			deleteQuestionButton.setOnAction(e->{
				deleteQuestion(primaryStage);
			});
			
			bottomBox.getChildren().addAll(addQuestionButton, deleteQuestionButton, submitButton);
		}
		
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15));
		this.borderPanRoot.setBottom(bottomBox);
		
		primaryStage.show();
	}

	
	/**
	 * Creates the buttons to switch between questions and QuestionVBoxes to print the questions
	 * @param questionButtonBox : HBox, HBox where the question buttons are created
	 * @param mainContent : VBox, scene main part, the center
	 */
	public void createQuestionButtonAndBox(HBox questionButtonBox, VBox mainContent)
	{
		this.listQuestionBox = new ArrayList<QuestionVBox>();
		
		for(Question question : this.exam.getQuestionList())
		{
			QuestionVBox questionBox = new QuestionVBox(mainContent, question, this.examTry, this.isATry); 
			this.listQuestionBox.add(questionBox);
			
			Button button = new Button(String.valueOf(question.getId()));
			button.setMaxSize(60, 20);
			button.setPadding(new Insets(10));
			button.setOnAction(e->{
				mainContent.getChildren().remove(this.currentQuestionBox);
				this.currentQuestionBox = questionBox;
				mainContent.getChildren().add(questionBox);
			});
			questionButtonBox.getChildren().add(button);
		}
		
		if(this.listQuestionBox.isEmpty())
			this.currentQuestionBox = null;
		else
			this.currentQuestionBox = this.listQuestionBox.get(0);
	}
	
	
	/**
	 * Action when submit button is pushed
	 * @param primaryStage : Stage
	 */
	public void submitAnswer(Stage primaryStage)
	{
		this.examTry.finishTry();
		if(!ConnectionPage.state.equals("teacher"))
			ExamQuery.addTryToDatabase(this.examTry);
		showEndResult(primaryStage);
	}
	
	/**
	 * Action when the button to finish the exam creation is pushed
	 * @param primaryStage : Stage
	 */
	public void finishExamCreation(Stage primaryStage)
	{
		if(this.exam.getQuestionList().size() == 0)
		{
			showAlertError("You must create at least one question");
		}
		else
		{
			ExamQuery.addAnExamToDataBase(this.exam);
			ExamList examList = new ExamList();
			primaryStage.close();
			try 
			{
				examList.start(new Stage());
			} 
			catch (Exception er) {er.printStackTrace();}
		}
	}
	
	
	/**
	 * Shows the exam results
	 * @param primaryStage
	 */
	public void showEndResult(Stage primaryStage)
	{
		this.borderPanRoot2 = new BorderPane();
		Scene sceneResult = new Scene(this.borderPanRoot2,800,600);
		primaryStage.setScene(sceneResult);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		// Title
		VBox titleBox = new VBox();
		Label labelNewTitle = new Label(this.exam.getTitle());
		labelNewTitle.setStyle("-fx-font: 26 arial;");
		labelNewTitle.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(labelNewTitle);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(20));
		this.borderPanRoot2.setTop(titleBox);
		
		// Starting hour
		VBox vbox = new VBox();
		HBox boxStartingHour = new HBox();
		Label labelStartingHour = new Label("Starting hour: " + simpleDateFormat.format(this.examTry.getStartingHour()));
		labelStartingHour.setStyle("-fx-font: 20 arial;");
		labelStartingHour.setAlignment(Pos.CENTER);
		boxStartingHour.getChildren().add(labelStartingHour);
		boxStartingHour.setAlignment(Pos.CENTER);
		boxStartingHour.setPadding(new Insets(15));
		
		// Ending hour 
		HBox boxEndingHour = new HBox();
		Label labelEndingHour = new Label("Ending hour: " + simpleDateFormat.format(this.examTry.getEndingHour()));
		labelEndingHour.setStyle("-fx-font: 20 arial;");
		labelEndingHour.setAlignment(Pos.CENTER);
		boxEndingHour.getChildren().add(labelEndingHour);
		boxEndingHour.setAlignment(Pos.CENTER);
		boxEndingHour.setPadding(new Insets(15));
		
		// Mark
		HBox boxMark = new HBox();
		Label labelMark = new Label("Your mark: " + String.valueOf(this.examTry.getMark()) + "/20");
		labelMark.setStyle("-fx-font: 20 arial;");
		labelMark.setAlignment(Pos.CENTER);
		boxMark.getChildren().add(labelMark);
		boxMark.setAlignment(Pos.CENTER);
		boxMark.setPadding(new Insets(15));
		
		vbox.getChildren().addAll(boxStartingHour, boxEndingHour, boxMark);
		
		if(!this.examTry.isInTimeLimit())
		{
			// If its too late
			HBox boxTooLate = new HBox();
			Label labelToolate = new Label("You got a 0 because the time was over, sorry");
			labelToolate.setStyle("-fx-font: 20 arial;");
			labelToolate.setAlignment(Pos.CENTER);
			boxTooLate.getChildren().add(labelToolate);
			boxTooLate.setAlignment(Pos.CENTER);
			boxTooLate.setPadding(new Insets(15));	
			vbox.getChildren().add(boxTooLate);
		}
		
		vbox.setAlignment(Pos.CENTER);
		this.borderPanRoot2.setCenter(vbox);
		
		// Submit button
		HBox bottomBox = new HBox();
		Button submitButton = new Button("Return home page");
		submitButton.setStyle("-fx-font: 24 arial;");
		submitButton.setPadding(new Insets(15));
		submitButton.setAlignment(Pos.CENTER);
		submitButton.setOnAction(e->{
			ExamList examList = new ExamList();
			primaryStage.close();
			try 
			{
				examList.start(new Stage());
			} 
			catch (Exception er) {er.printStackTrace();}
		});

		bottomBox.getChildren().add(submitButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15));
		this.borderPanRoot2.setBottom(bottomBox);
	}
	
	
	/**
	 * Show a new Scene to enter a question
	 * @param primaryStage
	 */
	public void addQuestion(Stage primaryStage)
	{
		this.borderPanRoot2 = new BorderPane();
		Scene sceneResult = new Scene(this.borderPanRoot2,800,600);
		primaryStage.setScene(sceneResult);
		List<Choice> choiceList = new ArrayList<Choice>();
		
		// Title
		VBox titleBox = new VBox();
		Label labelNewTitle = new Label("Enter your question information");
		labelNewTitle.setStyle("-fx-font: 26 arial;");
		labelNewTitle.setAlignment(Pos.CENTER);
		titleBox.getChildren().add(labelNewTitle);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(20));
		this.borderPanRoot2.setTop(titleBox);
		
		// Question title and value
		VBox vbox = new VBox();
		HBox boxTitle = new HBox();
		
		Label labelTitle = new Label("Title:");
		labelTitle.setPadding(new Insets(10));
		labelTitle.setStyle("-fx-font: 20 arial;");
		labelTitle.setAlignment(Pos.CENTER);
		TextField textFieldTitle = new TextField();
		textFieldTitle.setPadding(new Insets(10));
		textFieldTitle.prefWidthProperty().bind(boxTitle.widthProperty().multiply(0.5));
		
		Label labelValue = new Label("Value:");
		labelValue.setPadding(new Insets(10));
		labelValue.setStyle("-fx-font: 20 arial;");
		labelValue.setAlignment(Pos.CENTER);
		TextField textFieldValue = new TextField();
		textFieldValue.setPadding(new Insets(10));
		textFieldValue.prefWidthProperty().bind(boxTitle.widthProperty().multiply(0.1));
		
		boxTitle.getChildren().addAll(labelTitle, textFieldTitle, labelValue, textFieldValue);
		boxTitle.setAlignment(Pos.CENTER);
		boxTitle.setPadding(new Insets(15));
		
		// Add a choice 
		HBox boxChoiceOutput = new HBox();
		boxChoiceOutput.setPadding(new Insets(20));
		boxChoiceOutput.setAlignment(Pos.CENTER);
		VBox vBoxChoiceOutput = new VBox();
		vBoxChoiceOutput.setAlignment(Pos.CENTER);
		HBox boxChoiceInput = new HBox();
		VBox vBoxChoice = new VBox();
		HBox hBox1 = new HBox();
		HBox hBox2 = new HBox();
		
		Label labelChoiceTitle = new Label("Choice statement:");
		labelChoiceTitle.setPadding(new Insets(10));
		labelChoiceTitle.setStyle("-fx-font: 20 arial;");
		labelChoiceTitle.setAlignment(Pos.CENTER);
		TextField textFieldChoice = new TextField();
		textFieldChoice.setPadding(new Insets(10));
		textFieldChoice.prefWidthProperty().bind(boxTitle.widthProperty().multiply(0.6));
		hBox1.setAlignment(Pos.CENTER);
		hBox1.getChildren().addAll(labelChoiceTitle, textFieldChoice);
		
		ToggleGroup tg = new ToggleGroup();
		RadioButton radioTrue = new RadioButton("True");
		radioTrue.setStyle("-fx-font: 16 arial;");
		radioTrue.setPadding(new Insets(15));
		radioTrue.setToggleGroup(tg);
		radioTrue.setSelected(true);
		RadioButton radioFalse = new RadioButton("False");
		radioFalse.setStyle("-fx-font: 16 arial;");
		radioFalse.setPadding(new Insets(15));
		radioFalse.setToggleGroup(tg);
		
		Button buttonAddChoice = new Button("Add a choice");
		boxChoiceInput.getChildren().add(buttonAddChoice);
		buttonAddChoice.setOnAction(e->{
			if(choiceList.size() < 4)
			{
				if(textFieldChoice.getText() != "")
				{
					Choice choice = null;
					if(radioTrue.isSelected())
						choice = new Choice(choiceList.size() + 1, textFieldChoice.getText(), true);
					else
						choice = new Choice(choiceList.size() + 1, textFieldChoice.getText(), false);
					textFieldChoice.setText("");
					radioTrue.setSelected(true);
					radioFalse.setSelected(false);
					choiceList.add(choice);
					Label labelChoiceOutput = new Label(choice.isCorrect() + " - " + choice.getStatement());
					labelChoiceOutput.setPadding(new Insets(10));
					labelChoiceOutput.setStyle("-fx-font: 20 arial;");
					labelChoiceOutput.setAlignment(Pos.CENTER);
					vBoxChoiceOutput.getChildren().add(labelChoiceOutput);
				}
			}
			else
				showAlertError("Maximum 4 choice per question");
		});
		hBox2.getChildren().addAll(radioTrue, radioFalse, buttonAddChoice);
		hBox2.setAlignment(Pos.CENTER);
		vBoxChoice.getChildren().addAll(hBox1, hBox2);
		
		boxChoiceInput.getChildren().add(vBoxChoice);
		boxChoiceInput.setAlignment(Pos.CENTER);
		boxChoiceInput.setPadding(new Insets(15));
		boxChoiceOutput.getChildren().add(vBoxChoiceOutput);
		
		vbox.getChildren().addAll(boxTitle, boxChoiceInput, boxChoiceOutput);
		vbox.setAlignment(Pos.CENTER);
		this.borderPanRoot2.setCenter(vbox);
		
		// Submit and return buttons
		HBox bottomBox = new HBox();
		
		Button returnButton = new Button("Return");
		returnButton.setStyle("-fx-font: 24 arial;");
		returnButton.setPadding(new Insets(15));
		returnButton.setAlignment(Pos.CENTER);
		returnButton.setOnAction(e->{
			restartExamPageAddingAQuestion(primaryStage);
		});
		
		Button submitButton = new Button("Submit");
		submitButton.setStyle("-fx-font: 24 arial;");
		submitButton.setPadding(new Insets(15));
		submitButton.setAlignment(Pos.CENTER);
		submitButton.setOnAction(e->{
			if(textFieldTitle.getText().length() != 0)
			{
				if(isValidValue(textFieldValue.getText()))
				{
					if(choiceList.size() > 0)
					{
						Question question = new Question(this.exam.getQuestionList().size() + 1, textFieldTitle.getText(), Float.valueOf(textFieldValue.getText()));
						for(Choice choice : choiceList)
							question.addAChoice(choice);
						this.exam.getQuestionList().add(question);
						restartExamPageAddingAQuestion(primaryStage);
					}
					else
						showAlertError("At least one choice must be entered");
				}
				else
					showAlertError("Incorrect question value");	
			}
			else
				showAlertError("Question statement must be filled");	
		});

		bottomBox.getChildren().addAll(returnButton, submitButton);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15));
		this.borderPanRoot2.setBottom(bottomBox);
	}
	
	/**
	 * Removes the printed question from this exam
	 * @param primaryStage : Stage
	 */
	public void deleteQuestion(Stage primaryStage)
	{
		if(this.currentQuestionBox != null)
		{
			this.exam.getQuestionList().remove(this.currentQuestionBox.getCurrentQuestion());
			restartExamPageAddingAQuestion(primaryStage);
		}
	}
	
	/**
	 * Indicates if the value entered in the text field question value is possible
	 * @param value : String, the text in the textField
	 * @return boolean
	 */
	public static boolean isValidValue(String value)
	{
		try 
		{
			float v = Float.parseFloat(value);
			if(v < 0)
				return false;
		} 
		catch (NumberFormatException nfe) 
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * Restart the exam page when adding a question
	 * @param primaryStage : Stage
	 */
	public void restartExamPageAddingAQuestion(Stage primaryStage)
	{
		ExamPage examPage = new ExamPage(this.exam, null, false);
		primaryStage.close();
		try 
		{
			examPage.start(new Stage());
		} 
		catch (Exception er) {er.printStackTrace();}
	}
	
	/**
	 * Show an Alert with a message
	 * @param message : String, the message to print
	 */
	public static void showAlertError(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();	
	}

}
