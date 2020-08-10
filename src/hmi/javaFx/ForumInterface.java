package hmi.javaFx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import bdd.TopicQuery;
import core.forum.Forum;
import core.forum.Topic;
import core.person.Person;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class ForumInterface extends Application{

	private HBox topLayout = new HBox();
	private BorderPane borderPanRoot = new BorderPane();
	private Scene forumScene;
	protected Stage stage;
	private Forum forum;
	/**
	 * Constructor for ForumInterface
	 * @param forum 
	 */
	public ForumInterface(Forum forum ) {		
		this.forum=forum;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.topLayout = ConnectionPage.getLogoTop("Forum " + this.forum.getUv().getCode(), primaryStage);
		this.stage=primaryStage;
		primaryStage.setTitle("Forum " + this.forum.getUv().getCode());
		this.forumScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.forumScene);
		this.borderPanRoot.setTop(this.topLayout);
		
		HBox topics = new HBox();
		BorderPane content = new BorderPane();
		HBox buttons = HomeInterface.getButtonHBox(primaryStage);
		

		
		ListView<Topic> listView = new ListView<Topic>();
		
		for(Topic t : this.forum.getTopicList())
		{
			listView.getItems().add(t);
		}
		listView.prefWidthProperty().bind(topics.widthProperty().multiply(1));
		listView.prefHeightProperty().bind(topics.heightProperty().multiply(1));
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {

	        	try {
	        		if(listView.getSelectionModel().getSelectedItem()!=null)
	        			ForumInterface.createTopicInterface(listView.getSelectionModel().getSelectedItem(),ForumInterface.this.stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            
	        }
	    });
		
		// Set the List of topics' appearance 
		listView.setCellFactory(new Callback<ListView<Topic>, ListCell<Topic>>() {
		    @Override
		    public ListCell<Topic> call(ListView<Topic> param) {
		         ListCell<Topic> cell = new ListCell<Topic>() {
		             @Override
		            protected void updateItem(Topic item, boolean empty) {
		                super.updateItem(item, empty);
		                if(item != null) {
		                    setText(item.getTitle() + "     -     author  : " + item.getAuthor().getLastName() + " " + item.getAuthor().getFirstName());
		                } else {
		                    setText(null);
		                }
		            }
		         };
		        return cell;
		    }
		});
		
		topics.getChildren().add(listView);		
		topics.prefWidthProperty().bind(content.widthProperty().multiply(0.25));
		topics.prefHeightProperty().bind(content.heightProperty().multiply(0.5));
		content.setTop(buttons);	
		content.setCenter(topics);
	    content.prefWidthProperty().bind(this.borderPanRoot.widthProperty().multiply(0.25));
	    content.prefHeightProperty().bind(this.borderPanRoot.heightProperty().multiply(0.25));	    
	    Button createTopicButton = new Button("Create Topic");
	    
	    // Topic creation Button event Handler
	    createTopicButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	String topicSubject="";
            	TextInputDialog topicSubjectDialog = new TextInputDialog();
            	topicSubjectDialog.setTitle("Topic");
            	topicSubjectDialog.setHeaderText("Create a topic");
            	topicSubjectDialog.setContentText("Please enter the subject of your topic");

            	// Traditional way to get the response value.
            	Optional<String> result = topicSubjectDialog.showAndWait();
            	if (result.isPresent()){
            		topicSubject=result.get();
            	}
            	
            	if(topicSubject.length() > 0 )
            		ForumInterface.this.createTopic(topicSubject);
            	else
            	{
            		Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error ");
            		alert.setHeaderText("Creation Error");
            		alert.setContentText("The topic title is empty");

            		alert.showAndWait();
            	}
            }
        });
	    this.borderPanRoot.setCenter(content);
		this.borderPanRoot.setBottom(createTopicButton);
		primaryStage.show();
	}
	
	/**
	 * Creates topic interface
	 * Called when the user clicks on an topic 
	 * @param topic
	 * @param stage
	 * @throws Exception
	 */
	public static void createTopicInterface(Topic topic,Stage stage) throws Exception
	{
		Stage newStage = new Stage();
		TopicInterface newTopic = new TopicInterface(topic);
		newTopic.start(newStage);
		stage.close();
	}
	     
	/**
	 * Creates a topic
	 * Called when the user presses the topic creation button
	 * @param subject
	 */
	public  void createTopic(String subject)
	{
		try {
			Person author = new Person(ConnectionPage.lastNameConnected,ConnectionPage.firstNameConnected);
			Topic newTopic =this.forum.createTopic(this.forum.getTopicList().size(),new Person(ConnectionPage.lastNameConnected,ConnectionPage.firstNameConnected), subject,this.forum.getUv());
			TopicQuery.createTopic(this.forum,ConnectionPage.lastNameConnected,ConnectionPage.firstNameConnected, subject);		
			Stage newStage = new Stage();
			if(!this.forum.isProjectForum()) 
			{
				this.forum.setTopicList(TopicQuery.getTopicList(this.forum.getUv(),this.forum));
			}
			else
			{
				this.forum.setTopicList(TopicQuery.getTopicList(this.forum.getUv(),this.forum.getProjectID(),this.forum));
			}
			
			ForumInterface newForum= new ForumInterface(this.forum);
			newForum.start(newStage);
			this.stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}