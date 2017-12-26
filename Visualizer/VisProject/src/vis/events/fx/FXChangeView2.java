package vis.events.fx;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXChangeView2 extends FXChangeListener {

	public FXChangeView2(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		this.wnd.app.setView(2, newValue);
	}
 
}
