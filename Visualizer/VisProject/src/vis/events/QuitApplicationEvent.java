package vis.events;

import java.awt.event.ActionEvent;

import vis.frame.MainWindow;

public class QuitApplicationEvent extends AbstractActionEvent {

	public QuitApplicationEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.window.app.quit();
	}

}
