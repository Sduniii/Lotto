package application;

import java.util.ArrayList;

import org.fxmisc.richtext.StyleClassedTextArea;

import components.MouseBox;
import core.Core6Aus49;
import core.CoreEuro;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tsdun.sdunzehmke.de.SduniRandom;

public class Main extends Application {

	Stage window;
	Scene sceneOne;
	double xOffset, yOffset;
	StyleClassedTextArea console;
	final BooleanProperty debug = new SimpleBooleanProperty(false);
	final BooleanProperty shiftPressed = new SimpleBooleanProperty(false);
	TextField textFieldNormal, textFieldEuro;
	Spinner<Integer> spinnerNormal, spinnerEuro;
	SduniRandom rand;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.window = primaryStage;
			this.window.getIcons().add(new Image(getClass().getResource("logo.png").toExternalForm()));

			initStage();
			initSceneOne();

			primaryStage.setScene(sceneOne);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initStage() {
		this.window.setTitle("Lotto");
		this.window.initStyle(StageStyle.TRANSPARENT);
	}

	private void initSceneOne() throws Exception {
		StackPane uRoot = new StackPane();
		BorderPane root = new BorderPane();
		root.getStyleClass().add("main-style");

		// Top Menu
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

		Label name = new Label("LottoZahlen by Sduniii");
		name.setId("label-name");
		final ImageView logo = new ImageView();
		Image image1 = new Image(getClass().getResource("logo.png").toExternalForm(), 20, 20, false, false);
		logo.setImage(image1);
		menu.getChildren().addAll(logo, name);

		StackPane stack = new StackPane();
		stack.getChildren().add(closeButton);
		stack.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(stack, Priority.ALWAYS);

		menu.getChildren().add(stack);

		root.setTop(menu);
		// END Top Menu

		GridPane contentPane = new GridPane();
		contentPane.setPadding(new Insets(10, 10, 10, 10));
		contentPane.setVgap(8);
		contentPane.setHgap(10);

		Label labelNormal = new Label("6 aus 49 Felder: ");
		GridPane.setConstraints(labelNormal, 0, 0);

		textFieldNormal = new TextField();
		textFieldNormal.setPromptText("4");
		GridPane.setConstraints(textFieldNormal, 1, 0);
		
		Label labelWaveNormal = new Label("Reihe:");
		GridPane.setConstraints(labelWaveNormal, 2, 0);
		
		spinnerNormal = new Spinner<>(3, 6, 4);
		spinnerNormal.setPrefWidth(60);
		GridPane.setConstraints(spinnerNormal, 3, 0);

		Label labelEuro = new Label("Euro Jackpot Felder: ");
		GridPane.setConstraints(labelEuro, 0, 1);

		textFieldEuro = new TextField();
		textFieldEuro.setPromptText("0");
		GridPane.setConstraints(textFieldEuro, 1, 1);
		
		Label labelWaveEuro = new Label("Reihe:");
		GridPane.setConstraints(labelWaveEuro, 2, 1);
		
		spinnerEuro = new Spinner<>(2, 5, 2);
		spinnerEuro.setPrefWidth(60);
		GridPane.setConstraints(spinnerEuro, 3, 1);

		Button startButton = new Button("Los");
		startButton.setOnAction(e -> {
			ArrayList<Point2D> points = MouseBox.display("Bewege die Maus!");
			long seed = System.currentTimeMillis();
			for (Point2D p : points) {
				if (seed < Long.MAX_VALUE - p.getX() - p.getY()) {
					seed += (long) (p.getX() + p.getY());
					seed = (long) (seed % 2147483648L);
				} else {
					seed = (long) (seed % 2147483648L);
				}
			}

			rand = new SduniRandom(seed);
			new Thread(createTask()).start();
		});
		GridPane.setConstraints(startButton, 4, 1);

		contentPane.getChildren().addAll(labelNormal, textFieldNormal, labelWaveNormal, spinnerNormal, labelEuro, labelWaveEuro, spinnerEuro, textFieldEuro, startButton);

		root.setCenter(contentPane);

		// TextArea
		StackPane consolePane = new StackPane();
		consolePane.setPrefHeight(200);
		this.console = new StyleClassedTextArea();
		this.console.getStyleClass().add("console");
		this.console.setEditable(false);
		consolePane.getChildren().add(this.console);

		root.setBottom(consolePane);
		
		uRoot.getChildren().add(root);
		
		BorderPane load = new BorderPane();
		load.getStyleClass().add("loading");
		load.setVisible(false);
		ProgressIndicator progress = new ProgressIndicator();
		load.setCenter(progress);

		uRoot.getChildren().add(load);
		
		this.sceneOne = new Scene(uRoot);
		this.sceneOne.setFill(Color.TRANSPARENT);
		this.sceneOne.setOnKeyPressed(e -> {
			if (shiftPressed.getValue() && e.getCode() == KeyCode.D) {
				if (this.debug.getValue()) {
					this.debug.setValue(false);
					this.console.clearStyle(0, this.console.getText().length());
					this.console.deleteText(0, this.console.getText().length());
					this.console.appendText("debug aus!");
				} else {
					this.debug.setValue(true);
					this.console.clearStyle(0, this.console.getText().length());
					this.console.deleteText(0, this.console.getText().length());
					this.console.appendText("debug an!");
				}
			} else if (e.getCode() == KeyCode.SHIFT) {
				this.shiftPressed.setValue(true);
			}
		});
		this.sceneOne.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.SHIFT) {
				this.shiftPressed.setValue(false);
			}
		});

		this.sceneOne.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}
	
	public Task<Void> createTask(){
            return new Task<Void>() {           
                @Override
                protected Void call() throws Exception {
                	Main.this.console.clearStyle(0, Main.this.console.getText().length());
                	Main.this.console.deleteText(0, Main.this.console.getText().length());
        			int count49 = 0;
        			int countEur = 0;
        			try {
        				if (!Main.this.textFieldNormal.getText().equals("")) {
        					count49 = Integer.parseInt(Main.this.textFieldNormal.getText());
        				} else {
        					count49 = Integer.parseInt(Main.this.textFieldNormal.getPromptText());
        				}
        				if (!Main.this.textFieldEuro.getText().equals("")) {
        					countEur = Integer.parseInt(Main.this.textFieldEuro.getText());
        				} else {
        					countEur = Integer.parseInt(Main.this.textFieldEuro.getPromptText());
        				}
        			} catch (NumberFormatException ex) {
        				Main.this.console.clearStyle(0, Main.this.console.getText().length());
        				Main.this.console.deleteText(0, Main.this.console.getText().length());
        				Main.this.console.appendText("Falsche eingabe?");
        			}
        			
        			ArrayList<String> result = Core6Aus49.get(Main.this.rand, count49, Main.this.spinnerNormal.getValue(), Main.this.debug.getValue());
        			if (result != null && result.size() > 0) {
        				String temp = "6 aus 49:" + System.lineSeparator();
        				Main.this.console.appendText(temp);
        				Main.this.console.setStyleClass(Main.this.console.getText().length() - temp.length(), Main.this.console.getText().length() - 1, "green");
        				for (String string : result) {
        					Main.this.console.appendText(string + System.lineSeparator());
        					if (string.contains("Zahlen schon enthalten:")) {
        						Main.this.console.setStyleClass(Main.this.console.getText().length() - string.length() - 2, Main.this.console.getText().length() - 1, "red");
        					}
        				}
        			}
        			result = CoreEuro.get(Main.this.rand, countEur, Main.this.spinnerEuro.getValue(), Main.this.debug.getValue());
        			if (result != null && result.size() > 0) {
        				String temp = "Eurojackpot:" + System.lineSeparator();
        				Main.this.console.appendText(temp);
        				Main.this.console.setStyleClass(Main.this.console.getText().length() - temp.length(), Main.this.console.getText().length() - 1, "green");
        				for (String string : result) {
        					Main.this.console.appendText(string + System.lineSeparator());
        					if (string.contains("Zahlen schon enthalten:")) {
        						Main.this.console.setStyleClass(Main.this.console.getText().length() - string.length() - 2, Main.this.console.getText().length() - 1, "red");
        					}
        				}
        			}
                   
                    return null;
                }
            };
    };

}
