package application;
	

import java.util.ArrayList;

import components.MouseBox;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{
	
	Stage window;
	Scene sceneOne, sceneTwo;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.window = primaryStage;
			
			initStage();
			initSceneOne();
			initSceneTwo();
			
			
			
			
			primaryStage.setScene(sceneOne);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initStage(){
		this.window.setTitle("Lotto");
	}
	
	private void initSceneOne() {
		VBox root = new VBox(10);
		
		//Button
		Button button = new Button("Los");
		button.setOnAction(e -> {
			this.window.setScene(this.sceneTwo);
		});
		Button button2 = new Button("Los2");
		button2.setOnAction(e -> {
			ArrayList<Point2D> list = MouseBox.display("Mouse");
			System.out.println(list.size());
		});
		
		root.getChildren().addAll(button, button2);
		
		this.sceneOne = new Scene(root,400,400);
		this.sceneOne.getStylesheets().add(getClass().getResource("application.css").toExternalForm());		
	}

	private void initSceneTwo() {
		VBox root = new VBox(20);
		
		//Button
		Button button = new Button("Zurück");
		button.setOnAction(e -> {
			this.window.setScene(this.sceneOne);
		});
		Label label = new Label("Yeah");
		
		root.getChildren().addAll(button,label);
		
		this.sceneTwo = new Scene(root,200,200);
		this.sceneTwo.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	}

}
