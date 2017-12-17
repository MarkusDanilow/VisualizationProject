package vis.main;

import java.awt.Canvas;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.LWJGLException;

import com.base.common.resources.DataElement;
import com.base.common.resources.Range;
import com.base.engine.Engine;
import com.base.engine.EngineInterfaces;
import com.base.engine.Settings;

import vis.data.DataHandler;
import vis.frame.LookAndFeel;
import vis.frame.MainWindow;
import vis.interfaces.AppInterface;
import vis.statistics.Statistic;

public class VisApplication implements AppInterface {

	/**
	 * 
	 * @param args
	 * @throws LWJGLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
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
	 * @throws LWJGLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public VisApplication() throws LWJGLException, FileNotFoundException, IOException, SQLException {

		this.window = new MainWindow(this, Settings.getApplicationTitle(), Settings.getDisplayWidth(),
				Settings.getDisplayHeight());
		Canvas c = this.window.getCanvasById(0);
		this.engine = new Engine(c, false);
	}

	/**
	 * 
	 */
	@Override
	public void loadDataFromFile(String fileName) throws Exception {
		this.handleLoadedData(DataHandler.loadDataFromFile(fileName));
	}

	/**
	 * 
	 */
	@Override
	public void loadDataFromRemotAPI() {
		this.handleLoadedData(DataHandler.loadDataFromRemoteAPI());
	}

	private void handleLoadedData(final Map<Float, DataElement> data) {
		DataElement[] bounds = DataHandler.getDataBounds(data);
		this.window.toggleTimeline(true, (int) bounds[0].getTime(), (int) bounds[1].getTime(),
				(int) bounds[1].getTime());
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
		return DataHandler.getCurrentBuffer().getStep();
	}

	@Override
	public void displaySubData(Range<Float> range) {
		Map<Float, DataElement> partialData = DataHandler.getPartialData(DataHandler.getCurrentBuffer().getData(),
				range);
		this.engine.setPointCloudData(DataHandler.convertToRenderableList(partialData));
		this.engine.setPointCloudClusters(DataHandler.getCurrentClusters());
		this.engine.setChartData(Statistic.getRenderableSampledList(partialData));
		this.engine.resetViewportDisplayList(0);
		this.engine.resetViewportDisplayList(2);
	}

}
