package hmi.javaFx;

import java.io.FileNotFoundException;

import bdd.ProjectQuery;
import core.document.Document;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Thomas
 *
 */
public class CourseInterface extends Application {
	
	private HBox topLayout = new HBox();
	private BorderPane borderPanRoot = new BorderPane();
	Scene projectListScene;
	
	
	/**Empty Constructor
	 * 
	 */
	public CourseInterface() {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Course List");
		this.projectListScene = new Scene(this.borderPanRoot,800,600);
		primaryStage.setScene(this.projectListScene);
		this.topLayout = ConnectionPage.getLogoTop("Course List", primaryStage);
		this.borderPanRoot.setTop(this.topLayout);
		if(ConnectionPage.state.equals("teacher")) {
			this.borderPanRoot.setCenter(getMainContentTeacher(primaryStage));
		}
		else {
			this.borderPanRoot.setCenter(getMainContentStudent(primaryStage));

		}
		primaryStage.show();
	}
	
	/** get the Main Content
	 * @param prim
	 * @return VBOX
	 */
	public static VBox getMainContentTeacher(Stage prim) {
		VBox row = new VBox();
		HBox topRow = new HBox();
		HBox botRow = new HBox();
		
		ListView<String> listDoc = new ListView<>();
		listDoc.prefWidthProperty().bind(topRow.widthProperty().multiply(0.8));
		if (Document.SeeRepositorycourse(HomeInterface.uv.getCode())!=null)
		{
			for(String s : Document.SeeRepositorycourse(HomeInterface.uv.getCode())) {
				listDoc.getItems().add(s);
			}
		}
		
		topRow.getChildren().add(listDoc);
		topRow.prefWidthProperty().bind(row.widthProperty());
		topRow.prefWidthProperty().bind(row.heightProperty());

		topRow.setAlignment(Pos.CENTER);
		
		Button openCourse = new Button("Open Course");
		openCourse.setAlignment(Pos.CENTER);
		openCourse.prefWidthProperty().bind(botRow.widthProperty().multiply(0.4));
		openCourse.setOnAction(e->{
			try {
				submitAction(prim,listDoc);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button addCourse = new Button("Add Course");
		addCourse.setAlignment(Pos.CENTER);
		addCourse.prefWidthProperty().bind(botRow.widthProperty().multiply(0.4));
		addCourse.setOnAction(e->{
			try {
				addAction();
				try {
					prim.close();
					CourseInterface courseInterface = new CourseInterface();
					Stage newStage = new Stage();
					courseInterface.start(newStage);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		botRow.getChildren().add(openCourse);
		botRow.getChildren().add(addCourse);
		botRow.prefWidthProperty().bind(row.widthProperty().multiply(0.33));
		botRow.setAlignment(Pos.CENTER);
		botRow.setPadding(new Insets(10,0,0,0));
		
		HBox selectionMenu = HomeInterface.getButtonHBox(prim);
		selectionMenu.setPadding(new Insets(0,0,20,0));
		row.getChildren().addAll(selectionMenu,topRow,botRow);
		
		return row;
	}
		
	
	/**Return the main content of the page
	 * @param prim 
	 * @return the VBox
	 * 
	 */
	public static VBox getMainContentStudent(Stage prim) {
		VBox row = new VBox();
		HBox topRow = new HBox();
		HBox botRow = new HBox();
		
		ListView<String> listDoc = new ListView<>();
		listDoc.prefWidthProperty().bind(topRow.widthProperty().multiply(0.4));
		if (Document.SeeRepositorycourse(HomeInterface.uv.getCode())!=null)
		{
			for(String s : Document.SeeRepositorycourse(HomeInterface.uv.getCode())) {
				listDoc.getItems().add(s);
			}
		}
		
		topRow.getChildren().add(listDoc);
		topRow.prefWidthProperty().bind(row.widthProperty());
		topRow.prefWidthProperty().bind(row.heightProperty());

		topRow.setAlignment(Pos.CENTER);
		
		Button openCourse = new Button("Open Course");
		openCourse.setAlignment(Pos.CENTER);
		openCourse.prefWidthProperty().bind(botRow.widthProperty().multiply(0.4));
		openCourse.setOnAction(e->{
			try {
				submitAction(prim,listDoc);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		botRow.getChildren().add(openCourse);
		botRow.prefWidthProperty().bind(row.widthProperty().multiply(0.33));
		botRow.setAlignment(Pos.CENTER);
		botRow.setPadding(new Insets(10,0,0,0));
		
		HBox selectionMenu = HomeInterface.getButtonHBox(prim);
		selectionMenu.setPadding(new Insets(0,0,20,0));
		row.getChildren().addAll(selectionMenu,topRow,botRow);
		
		return row;
	}
		
		/**
		 * @param primi
		 * @param l
		 * @throws Exception 
		 */
		public static void submitAction(Stage primi, ListView<String> l ) throws Exception {
			ObservableList<String> stringRegister = l.getSelectionModel().getSelectedItems();
				for(String m : stringRegister) {
						Document.openpdfcourse(HomeInterface.uv.getCode(),m);
					}
				}

		/**
		 * @param primi
		 * @param l
		 * @throws Exception 
		 */
		public static void addAction() throws Exception {
			Document.saveFilecourse(HomeInterface.uv.getCode());
		}
}
