package vis.events.fx;

import javafx.beans.value.ChangeListener;
import vis.frame.MainWindow;

public abstract class FXChangeListener implements ChangeListener<String> {

	protected final MainWindow wnd;

	public FXChangeListener(MainWindow wnd) {
		this.wnd = wnd;
	}

}
