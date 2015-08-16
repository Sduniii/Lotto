package application;
	

import components.MouseBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application{
	
	Stage window;
	Scene sceneOne;
	double xOffset, yOffset;
	@Override
	public void start(Stage primaryStage) {
		try {
			this.window = primaryStage;
			
			initStage();
			initSceneOne();
			
			
			
			
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
		this.window.initStyle(StageStyle.TRANSPARENT);
	}
	
	private void initSceneOne() {
		BorderPane root = new BorderPane();
		root.getStyleClass().add("main-style");
		
		//Top Menu
		HBox menu = new HBox();
		menu.setPrefSize(this.window.getWidth(), 0);
		menu.setPadding(new Insets(2, 2, 2, 10));
		menu.setSpacing(10);
		menu.getStyleClass().add("hbox-top");
		menu.setOnMousePressed(event -> {
			xOffset = this.window.getX() - event.getScreenX();
            yOffset = this.window.getY() - event.getScreenY();
		});
		menu.setOnMouseDragged(event -> {
			this.window.setX(event.getScreenX() + xOffset);
			this.window.setY(event.getScreenY() + yOffset);
		});
		
		Button closeButton = new Button("X");
		closeButton.setOnAction(e -> this.window.close());
		closeButton.getStyleClass().add("close-button");
		
		Label name =  new Label("LottoZahlen by Sduniii");
		name.setId("label-name");
		final ImageView logo = new ImageView();
        Image image1 = new Image(getClass().getResource("../assets/logo.png").toExternalForm(), 20, 20, false, false);
        logo.setImage(image1);
		menu.getChildren().addAll(logo, name);
		
		StackPane stack = new StackPane();
		stack.getChildren().add(closeButton);
		stack.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		menu.getChildren().add(stack);
		
		root.setTop(menu);
		//END Top Menu
		
		
		
		GridPane contentPane = new GridPane();
		contentPane.setPadding(new Insets(10, 10, 10, 10));
		contentPane.setVgap(8);
		contentPane.setHgap(10);
		
		Label labelNormal = new Label("6 aus 49 Felder: ");
		GridPane.setConstraints(labelNormal, 0, 0);
		
		TextField textFieldNormal = new TextField();
		textFieldNormal.setPromptText("4");
		GridPane.setConstraints(textFieldNormal, 1, 0);
		
		Label labelEuro = new Label("Euro Jackpot Felder: ");
		GridPane.setConstraints(labelEuro, 0, 1);
		
		TextField textFieldEuro = new TextField();
		textFieldEuro.setPromptText("4");
		GridPane.setConstraints(textFieldEuro, 1, 1);
		
		Button startButton = new Button("Los");
		startButton.setOnAction(e -> MouseBox.display("Bewege die Maus!"));
		GridPane.setConstraints(startButton, 3, 1);
		
		contentPane.getChildren().addAll(labelNormal,textFieldNormal,labelEuro,textFieldEuro, startButton);
		
		root.setCenter(contentPane);
		
		
		//TextArea
		StackPane consolePane = new StackPane();
		TextArea console = new TextArea();
		console.setEditable(false);
		consolePane.getChildren().add(console);
		
		root.setBottom(consolePane);
		
		this.sceneOne = new Scene(root);
		this.sceneOne.setFill(Color.TRANSPARENT);
		
		this.sceneOne.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	}

}
