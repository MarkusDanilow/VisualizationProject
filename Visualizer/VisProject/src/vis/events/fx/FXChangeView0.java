package vis.events.fx;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXChangeView0 extends FXChangeListener {

	public FXChangeView0(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		this.wnd.app.setView(0, newValue);
	}
 
}