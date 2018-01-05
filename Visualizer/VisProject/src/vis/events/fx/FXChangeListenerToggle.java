package vis.events.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import vis.frame.MainWindow;

public abstract class FXChangeListenerToggle implements ChangeListener<Object> {

	protected final MainWindow wnd;

	public FXChangeListenerToggle(MainWindow wnd) {
		this.wnd = wnd;
	}
	

}
