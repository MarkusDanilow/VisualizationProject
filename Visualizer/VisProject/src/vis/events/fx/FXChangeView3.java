package vis.events.fx;

import javafx.beans.value.ObservableValue;
import vis.frame.MainWindow;

public class FXChangeView3 extends FXChangeListener {

	public FXChangeView3(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		this.wnd.app.setView(3, newValue);
		
		//Accordion anpassen
		this.wnd.getFxPanelObjectRight().changeAccordion(newValue, "D");
	}
 
}
