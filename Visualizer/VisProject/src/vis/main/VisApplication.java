package vis.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.LWJGLException;

import com.base.common.resources.DataElement;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.Range;
import com.base.common.resources.StatisticObject;
import com.base.engine.Engine;
import com.base.engine.EngineInterfaces;
import com.base.engine.Settings;
import com.base.engine.rendering.buffers.GraphicBufferUitl;

import vis.controller.VisController;
import vis.data.DataBuffer;
import vis.data.DataHandler;
import vis.data.RealTimeThread;
import vis.frame.LookAndFeel;
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
	 * 
	 */
	public static final boolean LOAD_MODELS = true;

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
	protected EngineInterfaces engine;

	private RealTimeThread realtimeThread;

	/**
	 * 
	 * @throws LWJGLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public VisApplication() throws LWJGLException, FileNotFoundException, IOException, SQLException {
		VisController.init(this);
		this.engine = new Engine(VisController.getCanvas(), LOAD_MODELS);

		/*
		 * int numItems = 100;
		 * this.handleLoadedData(DataHandler.generateDataSet(numItems, new
		 * Range<Float>(0f, 65000f)));
		 */

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
		this.handleLoadedData(DataHandler.loadDataFromRemoteAPI(10000, false));
	}

	public void handleLoadedData(final Map<Float, DataElement> data) {
		DataElement[] bounds = DataHandler.getDataBounds(data);
		VisController.getWindow().toggleTimeline(true, (int) bounds[0].getTime(), (int) bounds[1].getTime(),
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

	public void toggleCompleteParallelCoordinates(int viewportIndex) {
		this.engine.toggleCompleteParallelCoordinates();
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	@Override
	public void displaySubData(Range<Float> range) {
		Map<Float, DataElement> partialData = DataHandler.getPartialData(DataHandler.getCurrentBuffer().getData(),
				range);
		VisController.getWindow().setNumElements(partialData.size());
		List<DataElement> data = DataHandler.convertToRenderableList(partialData);
		for (int i = 0; i < Engine.NUM_VIEWS; i++)
			this.engine.setPointCloudData(i, data);
		this.engine.setPointCloudClusters(DataHandler.getCurrentClusters());
		this.engine.setChartData(Statistic.getRenderableSampledList(partialData));
		GraphicBufferUitl.performanceMeasureEnabled = false;
		this.engine.resetAllViewportDisplayLists();
	}

	@Override
	public void invertColors() {
		Settings.toggleColorInverted();
		this.engine.resetAllViewportDisplayLists();
	}

	@Override
	public void setView(int viewportIndex, String viewName) {
		this.engine.setView(viewportIndex, viewName);
	}

	public void setDataType(int viewportIndex, DataType[] type) {
		this.engine.setDataType(viewportIndex, type);
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	public void toggleDataType(int viewportIndex, DataType type, boolean toggled, int position) {
		this.engine.toggleDataType(viewportIndex, type, toggled, position);
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	public void setStatisticObject(int viewportIndex, StatisticObject type) {
		this.engine.setStatisticObject(viewportIndex, type);
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	public void toggleStats(int viewportIndex, boolean toggled) {
		this.engine.toggleStats(viewportIndex, toggled);
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	public void setPointCloudData(int viewportIndex, Map<Float, DataElement> partMap) {
		this.engine.setPointCloudData(viewportIndex, DataHandler.convertToRenderableList(partMap));
		this.engine.resetViewportDisplayList(viewportIndex);
	}

	public void rotateView(int viewportIndex, int angle) {
		this.engine.rotateView(viewportIndex, angle);
	}

	public void startLiveView() {
		// handleLoadedData(new TreeMap<>());
		// this.engine.toggleTimeColorInvert(false);
		DataHandler.setCurrentBuffer(new DataBuffer());
		this.realtimeThread = new RealTimeThread(this);
		realtimeThread.start();
	}

	public void stopLiveView() {
		// this.engine.toggleTimeColorInvert(true);
		realtimeThread.stopThread();
	}

}
