package components;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MouseBox implements EventHandler<MouseEvent> {

	public static ArrayList<Point2D> display(String title){
		Stage window = new Stage();
		
		ArrayList<Point2D> list = new ArrayList<>();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		AnchorPane pane = new AnchorPane();
			
	
		Scene scene = new Scene(pane,100,100);
		scene.setOnMouseMoved(e -> {
			Rectangle rect = new Rectangle(e.getX(), e.getY(), 1, 1);
			list.add(new Point2D(rect.getX(), rect.getY()));
			rect.setFill(Color.BLACK);
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
