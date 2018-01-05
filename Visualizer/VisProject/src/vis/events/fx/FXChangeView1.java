package vis.events.fx;

import javafx.beans.value.ObservableValue;
import vis.controller.VisController;
import vis.frame.MainWindow;

public class FXChangeView1 extends FXChangeListener {

	public FXChangeView1(MainWindow wnd) {
		super(wnd);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		this.wnd.app.setView(1, newValue);
		
		
		//TODO: Prüfen Reihenfolge der Canvas
		//Accordion anpassen
//		this.wnd.getFxPanelObjectRight().changeAccordion(newValue, "C", wnd);
		VisController.changeAccordion(newValue, "B", wnd);
	}
 
}
