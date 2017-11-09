package vis.events;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import com.base.common.resources.Range;

import vis.frame.MainWindow;

public class TimelineChangeEvent extends AbstractChangeEvent {

	public TimelineChangeEvent(MainWindow window) {
		super(window);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		JSlider source = (JSlider) arg0.getSource();
		if (!source.getValueIsAdjusting()) {
			int value = (int) source.getValue();
			if (value > 0)
				this.window.app.displaySubData(new Range<Float>(0f, (float) value));
		}
	}

}
