package vis.events.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FXHandlerPaneC implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Action 2: " + event.getSource());
	}

}
