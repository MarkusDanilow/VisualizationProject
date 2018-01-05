package vis.events.fx;

import javafx.beans.value.ChangeListener;
import vis.frame.MainWindow;

public abstract class FXChangeListenerObject implements ChangeListener<Object> {

	protected final MainWindow wnd;

	public FXChangeListenerObject(MainWindow wnd) {
		this.wnd = wnd;
	}
	

}
