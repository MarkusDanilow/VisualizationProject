package vis.events.fx;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXHandlerPaneA extends FXChangeListenerToggle {

	public FXHandlerPaneA(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
		System.out.println(observable);
		System.out.println(oldValue);
		System.out.println(newValue);
	}



}
