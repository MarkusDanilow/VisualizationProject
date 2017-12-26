package vis.frame;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.base.engine.FrameRateUtil;

public class TimerThread extends Thread {

	protected boolean isRunning;

	protected JLabel dateTimeLabel, fpsLabel;

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd.MM.yyyy");
	protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public TimerThread(JLabel dateTimeLabel, JLabel fpsLabel) {
		this.dateTimeLabel = dateTimeLabel;
		this.fpsLabel = fpsLabel;
		this.isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Calendar currentCalendar = Calendar.getInstance();
					Date currentTime = currentCalendar.getTime();
					String date = dateFormat.format(currentTime);
					String time = timeFormat.format(currentTime);
					dateTimeLabel.setText("Datum: " + date + ", Uhrzeit: " + time);
					fpsLabel.setText("FPS: " + FrameRateUtil.getFps());
				}
			});

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
