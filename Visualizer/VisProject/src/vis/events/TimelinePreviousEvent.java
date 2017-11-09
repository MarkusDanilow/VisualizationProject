package vis.events;

import java.awt.event.ActionEvent;

import vis.frame.MainWindow;

public class TimelinePreviousEvent extends AbstractActionEvent {

	public TimelinePreviousEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.window.changeTimelineValue(-(int) this.window.app.getTimelineStep());
	}

}
