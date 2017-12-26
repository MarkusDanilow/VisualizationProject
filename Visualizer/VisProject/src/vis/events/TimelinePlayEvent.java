package vis.events;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import vis.frame.MainWindow;

public class TimelinePlayEvent extends AbstractActionEvent {

	public TimelinePlayEvent(MainWindow window) {
		super(window);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton) arg0.getSource();
		String state = btn.getText();
		if (state.toLowerCase().equals("abspielen")) {
			this.window.startTimeline();
		} else if (state.toLowerCase().equals("pausieren")) {
			this.window.stopTimeline();
		}
	}

}
