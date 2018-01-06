package vis.events.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.frame.MainWindow;

public abstract class FXEventHandler implements EventHandler<ActionEvent> {

	protected final MainWindow wnd;

	public FXEventHandler(MainWindow wnd) {
		this.wnd = wnd;
	}

}
