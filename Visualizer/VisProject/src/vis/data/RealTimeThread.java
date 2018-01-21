package vis.data;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.base.common.resources.DataElement;

import vis.main.VisApplication;

public class RealTimeThread extends Thread {

	public static final int delay = 1000;

	private VisApplication application;
	private AtomicBoolean running = new AtomicBoolean(false);

	public RealTimeThread(VisApplication application) {
		this.application = application;
	}

	@Override
	public void run() {
		while (this.running.get()) {
			try {
				Map<Float, DataElement> mergedData = DataHandler.loadDataFromRemoteAPI(1, true);
				application.handleLoadedData(mergedData);
				Thread.sleep(delay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		running.set(true);
		super.start();
	}

	public void stopThread() {
		running.set(false);
	}

}
