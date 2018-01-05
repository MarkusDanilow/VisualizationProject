package vis.events.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FXHandlerPaneB implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Action 2: " + event.getSource());
		
		if(event.getSource().toString().contains("2b2948e2"));
			System.out.println("Es ist: show x");
		if (event.getSource().toString().contains("6ddf90b0")) 
			System.out.println("Es ist: show y");
		if (event.getSource().toString().contains("6ddf90b0")) 
			System.out.println("Es ist: show y");
	
	
		
		
		
		System.out.println(event.getTarget().toString());
	}

}
