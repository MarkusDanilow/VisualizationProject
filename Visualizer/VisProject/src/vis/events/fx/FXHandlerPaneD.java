package vis.events.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.frame.MainWindow;

public class FXHandlerPaneD extends FXEventHandler {

	public FXHandlerPaneD(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Action 2: " + event.getSource());
	}

}
