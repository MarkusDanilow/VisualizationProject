package vis.events;

import java.awt.event.ActionEvent;

import vis.frame.HelpWindow;
import vis.frame.MainWindow;

public class OpenHelpEvent extends AbstractActionEvent {

	public OpenHelpEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		new HelpWindow();
	}

}
