package components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MyButton extends Button implements EventHandler<ActionEvent> {
		
	public MyButton() {
		super();
		this.setOnAction(this);
	}
	
	public MyButton(String title) {
		super(title);
		this.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		System.out.println(this);
		
	}

}
