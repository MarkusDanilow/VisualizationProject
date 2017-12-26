package vis.events;

import com.base.engine.Settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.frame.MainWindow;

public class FXHandlerInvertColors implements EventHandler<ActionEvent> {

	protected MainWindow wnd;

	public FXHandlerInvertColors(MainWindow wnd) {
		super();
		this.wnd = wnd;
	}

	@Override
	public void handle(ActionEvent arg0) {
		wnd.app.invertColors();
	}

}
