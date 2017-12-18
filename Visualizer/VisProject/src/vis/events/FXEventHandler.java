package vis.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.frame.*;

public abstract class FXEventHandler implements EventHandler<ActionEvent> {
	
	RightFXPanel rightFX;
	
	public FXEventHandler (RightFXPanel rightFX) {
		this.rightFX = rightFX;
	}
	
	
	
	@Override
	public void handle (ActionEvent event){
		
	}

}
