package hmi.javaFx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import bdd.ConnectionToBDD;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import core.forum.*;
import core.person.Person;

/**Class which allow the login
 * @author Thomas
 *
 */
public class ConnectionPage extends Application{
	
	private BorderPane borderPanRoot = new BorderPane();
	private Scene connectionToApp;
	public static String lastNameConnected;
	public static String firstNameConnected;
	public static String state;
	static Stage currentStage;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        launch(args);
    }
	

	/** Get the top with logo and the title
	 * @param nameTop 
	 * @param st 
	 * @return HBOX TOP
	 * @throws FileNotFoundException
	 */
	public static HBox getLogoTop(String nameTop,Stage st) throws FileNotFoundException {
		HBox topBox = new HBox();
		HBox topBox1 = new HBox();
		HBox topBox2 = new HBox();
		HBox topBox3 = new HBox();
		
		Label labelNewTitle = new Label(nameTop);
		labelNewTitle.setStyle("-fx-font: 24 arial;");
		labelNewTitle.prefWidthProperty().bind(topBox1.widthProperty());
		labelNewTitle.setAlignment(Pos.CENTER);
		topBox1.getChildren().add(labelNewTitle);
		topBox1.setAlignment(Pos.CENTER);
		topBox1.prefWidthProperty().bind(topBox.widthProperty().multiply(0.33));
		
		Image Logo = new Image(new FileInputStream("Resources" + System.getProperty("file.separator") + "Logo.png"));
		ImageView imageView = new ImageView(Logo); 
		imageView.setFitHeight(100); 
	    imageView.setFitWidth(300); 	
	    
	    topBox2.getChildren().add(imageView);
		topBox2.prefWidthProperty().bind(topBox.widthProperty().multiply(0.33));
		topBox1.setAlignment(Pos.CENTER_LEFT);
		
		if(nameTop.equals("Personnal Page")) {
			Button disconnectButton = new Button("Logout");
			disconnectButton.setOnAction((event) -> {
				st.close();
				ConnectionPage cp = new ConnectionPage();
				Stage sta = new Stage();
				try {
					cp.start(sta);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
				disconnectButton.setMinWidth(topBox3.getWidth());
				disconnectButton.setMinHeight(topBox3.getHeight());
				disconnectButton.setAlignment(Pos.CENTER);
				
			    topBox3.getChildren().add(disconnectButton);
				
		}
		else if(!nameTop.equals("LOGIN")) {
		    Button personalPageButton =  new Button("Personal Page");
		    personalPageButton.setOnAction((event) -> {
		    	if(lastNameConnected!=null && firstNameConnected!=null) {
		    		st.close();
		    		try {
						PersonalPageInterface pi = new PersonalPageInterface();
						pi.start(st);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    });
		    personalPageButton.setMinWidth(topBox3.getWidth());
		    personalPageButton.setMinHeight(topBox3.getHeight());
		    personalPageButton.setAlignment(Pos.CENTER);
			
		    topBox3.getChildren().add(personalPageButton);
		}
		
		topBox3.prefWidthProperty().bind(topBox.widthProperty().multiply(0.33));
	    topBox3.setAlignment(Pos.CENTER_RIGHT);
	    topBox3.setPadding(new Insets(0,50,0,0));

	    
	    topBox.getChildren().addAll(topBox2,topBox1,topBox3);
		return topBox;
		
	}
	

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ConnectionToBDD.createConnection();
		primaryStage.setTitle("Connection");
		this.connectionToApp = new Scene(this.borderPanRoot,900,400);
		primaryStage.setScene(this.connectionToApp);
		this.borderPanRoot.setTop(getLogoTop("LOGIN",primaryStage));
		this.borderPanRoot.setCenter(getCenterConnexion(primaryStage));
		primaryStage.show();

	}
	
	/** return the login  content
	 * @param prim 
	 * @return HBox
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * 
	 */
	public static VBox getCenterConnexion(Stage prim) throws FileNotFoundException, SQLException {
		VBox row = new VBox();
		HBox content1 = new HBox();
		HBox content2 = new HBox();
		HBox content3 = new HBox();
		
		// First texfield to register name
        TextField textField = new TextField();
        textField.setText("Last Name");
        textField.prefWidthProperty().bind(content1.widthProperty().multiply(0.2));
        content1.getChildren().add(textField);
        content1.setAlignment(Pos.CENTER);
		content1.setPadding(new Insets(20,0,0,0));

        
        //Second text field 
        TextField textField2 = new TextField();
        textField2.setText("Firstname");
        textField2.prefWidthProperty().bind(content2.widthProperty().multiply(0.2));
        content2.getChildren().add(textField2);
        content2.setAlignment(Pos.CENTER);
		content2.setPadding(new Insets(20,0,0,0));


        //Button to connect
        Button validate = new Button("Connection");
        validate.prefWidthProperty().bind(content3.widthProperty().multiply(0.2));
        validate.setOnAction((event) -> {
			// Button was clicked
        	try {
				if(ConnectionToBDD.isConnectionTrue(textField.getText(),textField2.getText())) {
					lastNameConnected = textField.getText();
					firstNameConnected = textField2.getText();
					Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle("Connection");
			 
			        // Header Text: null
			        alert.setHeaderText(null);
			        alert.setContentText("Connection Success");
			        alert.showAndWait();
			        prim.close();
			        PersonalPageInterface page = new PersonalPageInterface();
			        page.start(prim);
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle("Connection");
			        alert.setHeaderText(null);
			        alert.setContentText("Wrong LastName and FirstName, Try Again!");
			        alert.showAndWait();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        content3.getChildren().add(validate);
        content3.setAlignment(Pos.CENTER);
		content3.setPadding(new Insets(20,0,0,0));

        
        //add 3 elements to the row
        row.getChildren().addAll(content1,content2,content3);
		return row;
	}

}
	

