package vis.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.LWJGLException;

import com.base.common.DataElement;
import com.base.common.resources.Range;
import com.base.engine.Engine;
import com.base.engine.EngineInterfaces;
import com.base.engine.Settings;

import vis.data.DataBuffer;
import vis.data.DataHandler;
import vis.frame.LookAndFeel;
import vis.frame.MainWindow;
import vis.interfaces.AppInterface;

public class VisApplication implements AppInterface {

	/**
	 * 
	 * @param args
	 * @throws LWJGLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws LWJGLException, FileNotFoundException, IOException {
		new VisApplication();
	}

	/**
	 * Currently selected {@link LookAndFeel}
	 */
	private static LookAndFeel lookAndFeel = LookAndFeel.SYSTEM;

	/**
	 * 
	 * @return
	 */
	public static LookAndFeel getLookAndFeel() {
		return lookAndFeel;
	}

	/**
	 * 
	 * @param lookAndFeel
	 */
	public static void setLookAndFeel(LookAndFeel lookAndFeel) {
		VisApplication.lookAndFeel = lookAndFeel;
	}

	/**
	 * Initializes the {@link LookAndFeel}.
	 */
	private static final void initLookAndFeel() {
		try {
			UIManager.getLookAndFeel().uninitialize();
			UIManager.setLookAndFeel(getLookAndFeel().name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	static {
		initLookAndFeel();
	}

	/**
	 * 
	 */
	protected MainWindow window;

	/**
	 * 
	 */
	protected EngineInterfaces engine;

	/**
	 * 
	 */
	private DataBuffer buffer;

	/**
	 * 
	 * @throws LWJGLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public VisApplication() throws LWJGLException, FileNotFoundException, IOException {

		this.window = new MainWindow(this, Settings.getApplicationTitle(), Settings.getDisplayWidth(),
				Settings.getDisplayHeight());
		this.engine = new Engine(this.window.getCanvasById(0), false);

		this.buffer = new DataBuffer();

	}

	/**
	 * 
	 */
	@Override
	public void loadData(String fileName) throws Exception {
		final Map<Float, DataElement> data = DataHandler.parseDataFromFile(fileName);
		DataElement[] bounds = DataHandler.getFirstAndLastElement(data);

		this.window.toggleTimeline(true, (int) bounds[0].getTime(), (int) bounds[1].getTime(),
				(int) bounds[0].getTime());

		this.buffer.setData(data);
		engine.setRawRenderData(DataHandler.convertToRenderableList(data));
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private void startAnimation() {
		new Thread() {
			public void run() {
				float time = 1;
				while (time < 9000) {
					System.out.println(engine.isRunning());
					if (engine.isRunning()) {
						Map<Float, DataElement> tmpData = DataHandler.getPartialData(buffer.getData(),
								new Range<Float>(0f, time));
						engine.setRawRenderData(DataHandler.convertToRenderableList(tmpData));
						engine.resetDisplayLists();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						time += 20;
						System.out.println(time);
					}
				}
			};
		}.start();
	}

	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Message from system", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void displayError(Exception exception) {
		this.displayMessage(exception.getMessage());
	}

	@Override
	public void quit() {
		System.exit(0);
	}

	@Override
	public void quit(Exception exception) {
		this.displayError(exception);
		System.exit(-1);
	}

	public float getTimelineStep() {
		return buffer.getStep();
	}

	@Override
	public void displaySubData(Range<Float> range) {
		Map<Float, DataElement> partialData = DataHandler.getPartialData(buffer.getData(), range);
		this.engine.setRawRenderData(DataHandler.convertToRenderableList(partialData));
		this.engine.resetViewportDisplayList(0);
	}

}
