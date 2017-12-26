package vis.events.fx;

import com.base.engine.Settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vis.frame.MainWindow;

public class FXInvertColors implements EventHandler<ActionEvent> {

	protected MainWindow wnd;

	public FXInvertColors(MainWindow wnd) {
		super();
		this.wnd = wnd;
	}

	@Override
	public void handle(ActionEvent arg0) {
		wnd.app.invertColors();
	}

}
