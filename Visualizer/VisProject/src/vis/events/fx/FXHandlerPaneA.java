package vis.events.fx;

import javafx.event.ActionEvent;
import vis.frame.MainWindow;

public class FXHandlerPaneA extends FXEventHandler {

	public FXHandlerPaneA(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Action 2: " + event.getSource());
		
		System.out.println(event.getEventType().getName());
		System.out.println(event.getSource().getClass());
		
		if(event.getSource() == "plot-between") {
			
			
		}

		
	}

}
