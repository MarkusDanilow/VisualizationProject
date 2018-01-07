package vis.events;

import java.awt.event.ActionEvent;

import vis.frame.MainWindow;

public class ToggleCompleteParallelCoordinatesEvent extends AbstractActionEvent {

	public ToggleCompleteParallelCoordinatesEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.window.app.toggleCompleteParallelCoordinates(3);
	}

}
