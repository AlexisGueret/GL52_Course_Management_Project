package hmi.javaFx;

import core.exam.Choice;
import core.exam.Question;
import core.exam.Try;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Class to print an exam question in ExamPage
 * @author loic
 *
 */
public class QuestionVBox extends VBox 
{
	Question currentQuestion;

	/**
	 * The VBox
	 * @param mainContent : Vbox
	 * @param _currentQuestion : Question
	 * @param examTry : Try
	 * @param isATry : boolean, indicates if it a try or an exam creation
	 */
	public QuestionVBox(VBox mainContent, Question _currentQuestion, Try examTry, boolean isATry) 
	{
		this.currentQuestion = _currentQuestion;
		this.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.9));
		
		// Title
		Label questionStatement = new Label(this.currentQuestion.getStatement() 
				+ " (" + this.currentQuestion.getValue() + " pts)");
		questionStatement.setStyle("-fx-font: 20 arial;");
		questionStatement.setPadding(new Insets(20));
		this.getChildren().add(questionStatement);
		
		// Choices
		for(Choice choice : this.currentQuestion.getChoiceList())
		{
			HBox choiceBox = new HBox();
			//Radio button
			CheckBox checkBox = new CheckBox();
			if(ConnectionPage.state.equals("teacher"))
				checkBox.setText(choice.getStatement() + " - " + choice.isCorrect());
			else
				checkBox.setText(choice.getStatement());
			checkBox.setStyle("-fx-font: 16 arial;");
			checkBox.setPadding(new Insets(15));
			if(isATry)
			{
				checkBox.setOnAction(e->{
					if(checkBox.isSelected())
						examTry.addChoiceToAnAnswer(this.currentQuestion, choice);
					else
						examTry.removeChoiceToAnAnswer(this.currentQuestion, choice);
				});
			}
			choiceBox.getChildren().add(checkBox);
			this.getChildren().add(choiceBox);
		}
	}

	public Question getCurrentQuestion() {
		return this.currentQuestion;
	}
	
	

}
