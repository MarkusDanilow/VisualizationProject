package vis.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.controller.*;

public class FXHandlerCommon implements EventHandler<ActionEvent> {

	
	@Override
	public void handle(ActionEvent arg0) {
		System.out.println("Test");
		VisController.getCanvas();
	}

}
