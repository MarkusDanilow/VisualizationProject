package vis.events;

import java.awt.event.ActionListener;

import vis.frame.MainWindow;

public abstract class AbstractActionEvent extends AbstractEvent implements ActionListener {

	public AbstractActionEvent(MainWindow window) {
		super(window);
	}

}
