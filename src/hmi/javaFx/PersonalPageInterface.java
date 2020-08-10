package hmi.javaFx;

import javafx.geometry.Insets;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import bdd.PersonalPageQuery;
import core.course.UV;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Thomas
 *
 */
public class PersonalPageInterface extends Application {
	private HBox topBox;
	private BorderPane borderPanRoot = new BorderPane();
	Scene personalPageScene;
	
	/**Constructor with the top HBox
	 * @param top
	 */
	public PersonalPageInterface(HBox top) {
		this.topBox = top;
	}
	
	
	/**Empty Constructor
	 * 
	 */
	public  PersonalPageInterface() {}
	
	
	/** give an action to submit button
	 * @param primi 
	 * @param l 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void submitAction(Stage primi, ListView<String> l ) throws SQLException, FileNotFoundException {
		ObservableList<String> stringRegister = l.getSelectionModel().getSelectedItems();
		for(String m : stringRegister) {
			for(UV uv : PersonalPageQuery.getListUv()) {
				if(m.equals(uv.getCode())) {
					HomeInterface hi= new HomeInterface(uv);
					primi.close();
					hi.start(primi);
				}
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Personal Page");
		this.topBox = ConnectionPage.getLogoTop("Personnal Page", primaryStage);
		this.personalPageScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.personalPageScene);
		this.borderPanRoot.setTop(this.topBox);
		this.borderPanRoot.setCenter(getCenterPersonalPageInterface(primaryStage));
		primaryStage.show();
	}
	
	/**This method will allow us to draw the main content of the page
	 * @param st
	 * @return the VBox
	 */
	public static VBox getCenterPersonalPageInterface(Stage st) {
		VBox row = new VBox();
		HBox hboxTop = new HBox();
		HBox hboxBot = new HBox();
		HBox buttonSubmit = new HBox();
		
		Label firstNameAndLast = new Label(ConnectionPage.lastNameConnected + " " + ConnectionPage.firstNameConnected);
		firstNameAndLast.setStyle("-fx-font: 30 arial;");
		firstNameAndLast.setAlignment(Pos.CENTER);
		firstNameAndLast.prefWidthProperty().bind(hboxTop.widthProperty());
		hboxTop.getChildren().add(firstNameAndLast);
		hboxTop.setAlignment(Pos.CENTER);
		hboxTop.prefWidthProperty().bind(row.widthProperty().multiply(0.33));
		
		
		ListView<String> listUV = new ListView<>();
		try {
			for(UV uv : PersonalPageQuery.getListUv()) {
				listUV.getItems().add(uv.getCode());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listUV.prefWidthProperty().bind(hboxBot.widthProperty().multiply(0.6));
		hboxBot.getChildren().add(listUV);
		hboxBot.prefWidthProperty().bind(row.widthProperty().multiply(0.33));
		hboxBot.setAlignment(Pos.CENTER);
		
		Button button = new Button("Submit");
		button.setAlignment(Pos.CENTER);
		button.setOnAction(e->{
			try {
				submitAction(st,listUV);
			} catch (FileNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		button.prefWidthProperty().bind(buttonSubmit.widthProperty().multiply(0.4));
		buttonSubmit.getChildren().add(button);
		buttonSubmit.prefWidthProperty().bind(row.widthProperty().multiply(0.33));
		buttonSubmit.setAlignment(Pos.CENTER);
		buttonSubmit.setPadding(new Insets(10,0,0,0));
		
		row.getChildren().addAll(hboxTop,hboxBot,buttonSubmit);
		
		
		return row;
	}
}
