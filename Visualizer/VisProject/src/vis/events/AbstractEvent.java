package vis.events;

import vis.frame.MainWindow;

public abstract class AbstractEvent {

	protected final MainWindow window;

	public AbstractEvent(final MainWindow window) {
		this.window = window;
	}

}
