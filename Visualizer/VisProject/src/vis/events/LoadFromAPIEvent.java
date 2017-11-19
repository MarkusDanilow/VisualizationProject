package vis.events;

import java.awt.event.ActionEvent;

import vis.frame.MainWindow;

public class LoadFromAPIEvent extends AbstractActionEvent {

	public LoadFromAPIEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.window.app.loadDataFromRemotAPI();
	}

}
