package vis.events;

import javax.swing.event.ChangeListener;

import vis.frame.MainWindow;

public abstract class AbstractChangeEvent extends AbstractEvent implements ChangeListener {

	public AbstractChangeEvent(MainWindow window) {
		super(window);
	}

}
