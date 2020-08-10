package hmi.javaFx;

import java.sql.SQLException;
import java.util.Optional;

import bdd.TopicQuery;
import core.forum.Message;
import core.forum.Topic;
import core.person.Person;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TopicInterface extends Application {

	private HBox topLayout = new HBox();
	private BorderPane borderPanRoot = new BorderPane();
	private Scene messageScene;
	private Stage stage;
	private Topic topic;
	/**
	 * Creates a Topic Interface
	 * @param topic 
	 */
	public TopicInterface(Topic topic)
	{
		this.topic=topic;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.topLayout = ConnectionPage.getLogoTop(this.topic.getTitle(), primaryStage);
		BorderPane content = new BorderPane();
		HBox buttons = HomeInterface.getButtonHBox(primaryStage);		
		HBox messages = new HBox();
		HBox createMessageBox=new HBox();
		ListView<Message> listView = new ListView<Message>();
		this.messageScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.messageScene);		
		this.stage=primaryStage;
		primaryStage.setTitle(this.topic.getTitle());		
		primaryStage.setScene(this.messageScene);
		this.borderPanRoot.setTop(this.topLayout);		
		for(Message message : this.topic.getMessageList())
		{
			listView.getItems().add(message);
		}
		listView.prefWidthProperty().bind(messages.widthProperty().multiply(1));
		listView.prefHeightProperty().bind(messages.heightProperty().multiply(1));	
		messages.getChildren().add(listView);		
		messages.prefWidthProperty().bind(content.widthProperty().multiply(0.25));
		messages.prefHeightProperty().bind(content.heightProperty().multiply(0.5));
		content.setTop(buttons);	
		content.setCenter(messages);		
		Button createMessageButton = new Button("Create Message");
		createMessageButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	TextInputDialog messageDialog = new TextInputDialog();
            	messageDialog.setTitle("Message");
            	messageDialog.setHeaderText("Setup message");
            	messageDialog.setContentText("Please enter your message");

            	// Traditional way to get the response value.
            	Optional<String> result = messageDialog.showAndWait();
            	if (result.isPresent()){
            	    try {
						TopicInterface.this.createMessage(result.get());
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
            }
        });
		createMessageButton.setAlignment(Pos.CENTER);
		createMessageBox.getChildren().add(createMessageButton);
		 this.borderPanRoot.setCenter(content);
		 this.borderPanRoot.setBottom(createMessageBox);
		
		primaryStage.show();
	}
	/**
	 * Creates a Message on the current topic
	 * @param message
	 * @throws SQLException 
	 */
	public void createMessage(String message) throws SQLException
	{
		this.topic.createMessage(message,new Person(ConnectionPage.lastNameConnected,ConnectionPage.firstNameConnected));
		TopicQuery.createMessage(this.topic,ConnectionPage.lastNameConnected,ConnectionPage.firstNameConnected,message);
		try {
			ForumInterface.createTopicInterface(this.topic, this.stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}