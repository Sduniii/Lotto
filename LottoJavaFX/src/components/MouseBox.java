package components;

import java.util.ArrayList;

import application.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MouseBox implements EventHandler<MouseEvent> {

	public static ArrayList<Point2D> display(String title){
		final BooleanProperty shiftPressed = new SimpleBooleanProperty(false);
		final DoubleProperty xOffset = new SimpleDoubleProperty();
		final DoubleProperty yOffset = new SimpleDoubleProperty();
		Stage window = new Stage();
		window.initStyle(StageStyle.TRANSPARENT);
		ArrayList<Point2D> list = new ArrayList<>();
		window.initModality(Modality.APPLICATION_MODAL);
		
		BorderPane root = new BorderPane();
		root.getStyleClass().add("main-style");
		root.setOnMousePressed(event -> {
			xOffset.set(window.getX() - event.getScreenX());
            yOffset.set(window.getY() - event.getScreenY());
		});
		root.setOnMouseDragged(event -> {
			window.setX(event.getScreenX() + xOffset.get());
			window.setY(event.getScreenY() + yOffset.get());
		});
		
		AnchorPane pane = new AnchorPane();
		root.setCenter(pane);
	
		Scene scene = new Scene(root,200,200);
		scene.setOnKeyPressed(e->{
			if(shiftPressed.getValue() && e.getCode() == KeyCode.X){
				window.close();
			}else if(e.getCode() == KeyCode.SHIFT){
				shiftPressed.setValue(true);
			}
		});
		scene.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.SHIFT){
				shiftPressed.setValue(false);
			}
		});
		
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		scene.setOnMouseMoved(e -> {
			Rectangle rect = new Rectangle(e.getX(), e.getY(), 1, 1);
			list.add(new Point2D(rect.getX(), rect.getY()));
			rect.getStyleClass().add("rect");
			pane.getChildren().add(rect);
		});
		window.setScene(scene);
		window.showAndWait();
		
		return list;
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
}
