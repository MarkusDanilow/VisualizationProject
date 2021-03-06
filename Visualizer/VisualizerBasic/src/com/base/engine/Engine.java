package com.base.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;
import com.base.common.IRenderer;
import com.base.common.resources.Callback;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.DataInspector;
import com.base.common.resources.DataMap2D;
import com.base.common.resources.DataMap3D;
import com.base.common.resources.MathUtil;
import com.base.common.resources.Range;
import com.base.common.resources.StatisticObject;
import com.base.engine.font.NEW.NewFontManager;
import com.base.engine.interaction.GraphicsHoverHandler;
import com.base.engine.interaction.InteractableRectangle;
import com.base.engine.interaction.InteractionEventBarChart;
import com.base.engine.interaction.InteractionEventLineChart;
import com.base.engine.interaction.InteractionEventParallelCoordinates;
import com.base.engine.interaction.InteractionEventPointCloud;
import com.base.engine.models.World;
import com.base.engine.rendering.ARenderer;
import com.base.engine.rendering.BarChartRenderer;
import com.base.engine.rendering.GridRenderer;
import com.base.engine.rendering.LineChartRenderer;
import com.base.engine.rendering.MiniMapRenderer;
import com.base.engine.rendering.ParallelCoordinatesRenderer;
import com.base.engine.rendering.PointCloudClusterRenderer;
import com.base.engine.rendering.PointCloudRenderer;
import com.base.engine.rendering.ViewportRenderer;

import gen.algo.Algy;
import gen.algo.common.MapMirrorType;
import gen.algo.common.TerrainUtil;

public class Engine implements EngineEventListener, EngineInterfaces {

	public static boolean INVERT_TIME_COLOR = false;
	
	public static boolean FULLSCREEN_ENABLED, VSYNC_ENABLED;
	public static int DISPLAY_WIDTH, DISPLAY_HEIGHT;

	private static Engine engineInstance;

	public static int NUM_VIEWS = 4;

	public static int getDisplayWidth() {
		return DISPLAY_WIDTH;
	}

	public static void setDisplayWidth(int displayWidth) {
		DISPLAY_WIDTH = displayWidth;
	}

	public static int getDisplayHeight() {
		return DISPLAY_HEIGHT;
	}

	public static void setDisplayHeight(int displayHeight) {
		DISPLAY_HEIGHT = displayHeight;
	}

	public static boolean isFullScreenEnabled() {
		return FULLSCREEN_ENABLED;
	}

	public static void setFullScreenEnabled(boolean val) {
		FULLSCREEN_ENABLED = val;
	}

	public static boolean isVSyncEnabled() {
		return VSYNC_ENABLED;
	}

	public static void setVSyncEnabled(boolean val) {
		VSYNC_ENABLED = val;
	}

	/* --------------- MEMBER ATTRIBUTES ------------------------ */

	private boolean running = false;

	private ViewportRenderer viewportRenderer;
	private PointCloudRenderer pointCloudRenderer;
	@SuppressWarnings("unused")
	private PointCloudClusterRenderer pointCloudClusterRenderer;
	private GridRenderer gridRenderer;
	@SuppressWarnings("unused")
	private MiniMapRenderer minimapRenderer;
	private BarChartRenderer barChartRenderer;
	private LineChartRenderer lineChartRenderer;
	private ParallelCoordinatesRenderer parallelCoorinatesRenderer;

	private List<InteractableRectangle> viewportRectangles = new ArrayList<>();
	private Map<String, Callback> viewportInteractions = new HashMap<>();

	private Map<String, ARenderer[]> rendererMapping = new HashMap<>();
	private List<ARenderer[]> renderers = new ArrayList<>();
	private float[] scaleFactors = new float[NUM_VIEWS];
	private Console console;
	private Camera[] cameras = new Camera[NUM_VIEWS];

	private DataMap2D data2D;
	private DataMap3D data3D;

	@SuppressWarnings("unchecked")
	private List<DataElement>[] pointCloudData = new List[NUM_VIEWS];
	private List<Cluster> pointCloudClusters;
	private List<DataElement> chartData;

	private float[] pointCloudWorldRotations = new float[NUM_VIEWS];

	private RenderMode renderMode;

	public boolean useCompleteParallelCoordinates = false;

	public DataType[][] selectedDataTypes = new DataType[NUM_VIEWS][DataType.values().length];
	public StatisticObject[] statistics = new StatisticObject[NUM_VIEWS];
	public boolean[] statsEnabled = new boolean[NUM_VIEWS];

	public boolean isRunning() {
		return running;
	}

	public int getNumRenderersForViewport(int viewportIndex) {
		return this.renderers.get(viewportIndex).length;
	}

	public IRenderer[] getRenderersForViewportIndex(int viewportIndex) {
		return this.renderers.get(viewportIndex);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public float[][] getDataToRender2D() {
		return data2D.getData();
	}

	public float[][][] getDataToRender3D() {
		return data3D.getData();
	}

	public List<DataElement>[] getPointCloudData() {
		return this.pointCloudData;
	}

	public List<DataElement> getPointCloudData(int viewportIndex) {
		return this.pointCloudData[viewportIndex];
	}

	public List<Cluster> getPointCloudClusters() {
		return pointCloudClusters;
	}

	public List<DataElement> getChartData() {
		return this.chartData;
	}

	public void setPointCloudClusters(List<Cluster> clusters) {
		this.pointCloudClusters = clusters;
	}

	public void setDataToRender2D(float[][] data) {
		data2D.setData(data);
	}

	public void setDataToRender3D(float[][][] data) {
		data3D.setData(data);
	}

	public void setPointCloudData(List<DataElement>[] data) {
		this.pointCloudData = data;
	}

	public void setPointCloudData(int viewportIndex, List<DataElement> data) {
		this.pointCloudData[viewportIndex] = data;
	}

	public void setChartData(List<DataElement> data) {
		this.chartData = data;
	}

	public void enable2DRenderMode() {
		this.renderMode = RenderMode.RENDER_2D;
	}

	public void enable3DRenderMode() {
		this.renderMode = RenderMode.RENDER_3D;
	}

	public void enableRawRenderMode() {
		this.renderMode = RenderMode.RENDER_RAW;
	}

	public boolean is2DRendeModeEnabled() {
		return this.renderMode == RenderMode.RENDER_2D;
	}

	public boolean is3DRenderModeEnabled() {
		return this.renderMode == RenderMode.RENDER_3D;
	}

	public boolean isRawRenderModeEnabled() {
		return this.renderMode == RenderMode.RENDER_RAW;
	}

	public Engine(final Canvas canvas, final boolean loadModels) {
		this.data2D = new DataMap2D();
		this.data3D = new DataMap3D();
		new Thread() {
			@Override
			public void run() {
				try {
					try {
						createDisplay(canvas);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				prepareComponents(canvas == null);
				if (loadModels)
					prepareModels();
				gameLoop();
				exit();
			}
		}.start();

		engineInstance = this;
	}

	private static DisplayMode getDisplayMode(int targetWidth, int targetHeight) {
		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for (DisplayMode m : modes) {
				if (m.isFullscreenCapable() && m.getWidth() == targetWidth && m.getHeight() == targetHeight)
					return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createDisplay(Canvas canvas) throws LWJGLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		boolean isParentDefined = canvas != null;

		if (isParentDefined) {
			Display.setParent(canvas);
			JFrame window = (JFrame) SwingUtilities.getRoot(canvas);
			Point location = SwingUtilities.convertPoint(canvas, canvas.getX(), canvas.getY(), window);
			Display.setLocation(location.x, location.y);

			// change canvas position for MAC fix
			// canvas.setLocation(location);
		}

		setDisplayWidth(Settings.getDisplayWidth());
		setDisplayHeight(Settings.getDisplayHeight());
		setFullScreenEnabled(Settings.getFullscreen());
		setVSyncEnabled(Settings.getVSync());
		try {
			DisplayMode displayMode = getDisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT);
			if (!(displayMode == null))
				Display.setDisplayMode(displayMode);
			else
				Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
			Display.setFullscreen(FULLSCREEN_ENABLED);
			Display.setVSyncEnabled(VSYNC_ENABLED);
			Point center = Util.getCenterOfScreen(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
			Display.setLocation((int) center.getX(), (int) center.getY());
			Display.setTitle(Settings.getApplicationTitle());
			Display.create();
			FrameRateUtil.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (isParentDefined) {
		}

	}

	private void toggleFullScreen() throws Exception {
		setFullScreenEnabled(!isFullScreenEnabled());
		if (isFullScreenEnabled()) {
			setDisplayWidth((int) Util.getScreenSize().getWidth());
			setDisplayHeight((int) Util.getScreenSize().getHeight());
		} else {
			setDisplayWidth(Settings.getDisplayWidth());
			setDisplayHeight(Settings.getDisplayHeight());
		}
		DisplayMode displayMode = getDisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		displayMode = displayMode == null ? new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT) : displayMode;
		Display.setDisplayMode(displayMode);
		Display.setFullscreen(FULLSCREEN_ENABLED);
	}

	private void prepareComponents(boolean useSystemEvents) {
		// CommandInterpreter.create(this);
		RenderUtil.create();
		RenderUtil.initGL();
		// FontManager.init();
		NewFontManager.init();
		InputHandler.create(this, useSystemEvents);

		int hWidth = Display.getWidth() / 2;
		int hHeight = Display.getHeight() / 2;

		/* define renderers and add them to the list of all renderers */
		this.viewportRenderer = new ViewportRenderer();
		this.pointCloudRenderer = new PointCloudRenderer();
		this.pointCloudClusterRenderer = new PointCloudClusterRenderer();
		this.gridRenderer = new GridRenderer();
		this.minimapRenderer = new MiniMapRenderer();
		this.barChartRenderer = new BarChartRenderer();
		this.lineChartRenderer = new LineChartRenderer();
		this.parallelCoorinatesRenderer = new ParallelCoordinatesRenderer();

		Camera.setSpeed(25f);

		for (int i = 0; i < NUM_VIEWS; i++) {
			switch (i) {
			case 0:
				ARenderer[] renderers = new ARenderer[4];
				renderers[0] = this.gridRenderer;
				renderers[1] = this.pointCloudRenderer;
				// renderers[2] = .pointCloudClusterRenderer;
				renderers[3] = this.minimapRenderer;
				this.renderers.add(renderers);
				this.rendererMapping.put(Settings.get3DView(), renderers);
				Callback event = new InteractionEventPointCloud();
				this.viewportRectangles.add(new InteractableRectangle(0, hWidth, hHeight, hHeight * 2, event));
				this.viewportInteractions.put(Settings.get3DView(), event);
				break;
			case 1:
				renderers = new ARenderer[1];
				renderers[0] = this.lineChartRenderer;
				this.renderers.add(renderers);
				this.rendererMapping.put(Settings.getLineChartView(), renderers);
				event = new InteractionEventLineChart();
				this.viewportRectangles.add(new InteractableRectangle(0, hWidth, 0, hHeight, event));
				this.viewportInteractions.put(Settings.getLineChartView(), event);
				break;
			case 2:
				renderers = new ARenderer[1];
				renderers[0] = this.barChartRenderer;
				this.renderers.add(renderers);
				this.rendererMapping.put(Settings.getBarChartView(), renderers);
				event = new InteractionEventBarChart();
				this.viewportRectangles.add(new InteractableRectangle(hWidth, hWidth * 2, hHeight, hHeight * 2, event));
				this.viewportInteractions.put(Settings.getBarChartView(), event);
				break;
			case 3:
				renderers = new ARenderer[1];
				renderers[0] = this.parallelCoorinatesRenderer;
				this.renderers.add(renderers);
				this.rendererMapping.put(Settings.getParallelCoordinatesView(), renderers);
				event = new InteractionEventParallelCoordinates();
				this.viewportRectangles.add(new InteractableRectangle(hWidth, hWidth * 2, 0, hHeight, event));
				this.viewportInteractions.put(Settings.getParallelCoordinatesView(), event);
				break;
			}

			this.scaleFactors[i] = 0.25f;
			this.cameras[i] = new Camera(
					new Vector3f(591 + ViewportRenderer.scale / 7, -4085, 532 + ViewportRenderer.scale / 7));
			this.cameras[i].setPitch(30);
			this.cameras[i].setYaw(130);

			Arrays.fill(this.selectedDataTypes[i], null);
			this.selectedDataTypes[i][0] = DataType.X;

			if (i == 3) {
				this.selectedDataTypes[i][1] = DataType.Y;
				this.selectedDataTypes[i][2] = DataType.Z;
				this.selectedDataTypes[i][3] = DataType.DIST;
			}

		}
		/* ------------- */

		this.console = new Console();
	}

	private void prepareModels() {
		World.create();
	}

	private void gameLoop() {
		try {
			this.setRunning(true);
			while (!Display.isCloseRequested() && this.isRunning()) {
				getInput();
				update();
				render();
				Display.sync(60);
				Display.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		try {
			if (FrameRateUtil.update()) {
				PerformanceUtil.update();
				StringBuilder sb = new StringBuilder();
				sb.append("Current FPS: " + FrameRateUtil.getFps() + "\n");
				sb.append("Current Memory Usage: \n" + "> max memory: " + PerformanceUtil.getMaxMem() + " MB\n"
						+ "> total free memory: " + PerformanceUtil.getTotalFreeMem() + " MB\n" + "> allocated memory: "
						+ PerformanceUtil.getAllocatedMem() + " MB\n" + "> free memory: " + PerformanceUtil.getFreeMem()
						+ " MB\n");
				sb.append("Processor Information: \n" + "> processor cores: " + PerformanceUtil.getProcessorCores()
						+ "\n" + "> system cpu load: " + PerformanceUtil.getSystemCpuLoad() + "%\n"
						+ "> process cpu load: " + PerformanceUtil.getProcessCpuLoad() + "%");
				console.update(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void render() {
		try {
			RenderUtil.setClearColor();
			viewportRenderer.prepare();
			viewportRenderer.render(this, this.cameras, this.renderers, this.scaleFactors);
			console.render(null);
			viewportRenderer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getInput() {
		try {
			InputHandler.getMouseInput(this);
			InputHandler.getKeyboardInput(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			RenderUtil.exitGL();
			InputHandler.destroy();
			Display.destroy();
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void destroy() {
		System.exit(0);
	}

	@Override
	public void notify(EngineEvent event, Object... data) {
		if (event != null) {
			switch (event) {

			case EXIT:
				this.setRunning(false);
				break;

			case FULLSCREEN_TOGGLE:
				try {
					toggleFullScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case CONSOLE_TOOGLE:
				Console.setVisible(!Console.isVisible());
				break;

			case COMMAND_LINE_TOGGLE:
				console.clearCommandLine();
				Console.setCommandLineFocused(!Console.isCommandLineFocused());
				break;
			case COMMAND_LINE_TYPE:
				console.updateCommandLineInput(String.valueOf(data[0]));
				break;
			case COMMAND_LINE_REMOVE_LAST:
				console.removeLastCommandLineCharacter();
				break;
			case COMMAND_LINE_EXECUTE:
				CommandInterpreter.execute(console.getCommandLine());
				console.clearCommandLine();
				break;
			case COMMAND_LINE_COMPLETE:
				console.setCommandLineInput(CommandInterpreter.getNextCompleteCommand(console.getCommandLine()));
				break;
			case MOVE_LEFT:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.WEST);
				break;
			case MOVE_RIGHT:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.EAST);
				break;
			case MOVE_FORWARD:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.NORTH);
				break;
			case MOVE_BACKWARD:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.SOUTH);
				break;
			case MOVE_UP:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.UP);
				break;
			case MOVE_DOWN:
				cameras[Integer.valueOf(String.valueOf(data[0]))].move(EDirection.DOWN);
				break;

			case LOOK_X:
				cameras[Integer.valueOf(String.valueOf(data[1]))].yaw(Float.valueOf(String.valueOf(data[0])));
				break;
			case LOOK_Y:
				cameras[Integer.valueOf(String.valueOf(data[1]))].pitch(Float.valueOf(String.valueOf(data[0])));
				break;

			case SCALE:
				this.changeScaleFactor(Float.valueOf(String.valueOf(data[0])),
						Integer.valueOf(String.valueOf(data[1])));
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void resetDisplayLists() {
		this.viewportRenderer.resetDisplayList(this, 0);
	}

	@Override
	public void updateHints(String hints) {
		this.console.updateHint(hints);
		toggleHelp();
	}

	@Override
	public void toggleHelp() {
		Console.setHintsVisible(!Console.isHintsVisible());
	}

	@Override
	public void changeRenderMode(int renderMode) {
	}

	@Override
	public void toggleAnimation() {
	}

	@Override
	public void changeCameraSpeed(float speed) {
		Camera.setSpeed(speed);
	}

	@Override
	public void toggleGrid() {
	}

	@Override
	public void toggleWater() {
	}

	@Override
	public void toggleVegetation() {
	}

	private void map(Range<Float> range) {
		data2D.setData(MathUtil.map(data2D.getData(), range.getLoVal(), range.getHiVal(), -1));
	}

	public void smooth(int smoothness) {
		Algy.smooth(data2D.getData(), smoothness);
		TerrainUtil.smooth(data2D.getData(), smoothness);
		resetDisplayLists();
	}

	@Override
	public void generatePerlinNoise2D(int w, int h, int o) {
		data2D.setData(Algy.generatePerlin(w, h, o, MapMirrorType.NO_MIRROR));
		this.enable2DRenderMode();
	}

	@Override
	public void generateRandomNoise(int w, int h) {
		data2D.setData(Algy.generateRandom(w, h, MapMirrorType.NO_MIRROR));
		this.enable2DRenderMode();
	}

	@Override
	public void generatePerlinNoise2D(int w, int h, int o, Range<Float> range) {
		generatePerlinNoise2D(w, h, o);
		map(range);
		this.enable2DRenderMode();
	}

	@Override
	public void generateRandomNoise(int w, int h, Range<Float> range) {
		generateRandomNoise(w, h);
		map(range);
		this.enable2DRenderMode();
	}

	@Override
	public void generateMidpointDisplacement(int w) {
		data2D.setData(Algy.generateMidpointDisplacement(w, MapMirrorType.NO_MIRROR));
		this.enable2DRenderMode();
	}

	@Override
	public void generateMidpointDisplacement(int w, Range<Float> range) {
		generateMidpointDisplacement(w);
		map(range);
		this.enable2DRenderMode();
	}

	@Override
	public void generateDiamondSquare(int w) {
		data2D.setData(Algy.generateDiamondSquare(w, MapMirrorType.NO_MIRROR));
		this.enable2DRenderMode();
	}

	@Override
	public void generateDiamondSquare(int w, Range<Float> range) {
		generateDiamondSquare(w);
		map(range);
		this.enable2DRenderMode();
	}

	@Override
	public void generateSimplexNoise2D(int w, int h, int o) {
		data2D.setData(Algy.generateSimplex(w, h, o, MapMirrorType.NO_MIRROR));
		this.enable2DRenderMode();
	}

	@Override
	public void generateSimplexNoise2D(int w, int h, int o, Range<Float> range) {
		generateSimplexNoise2D(w, h, o);
		map(range);
		this.enable2DRenderMode();
	}

	@Override
	public void generatePerlinNoise2D(int w, int h, int o, Range<Float> range, int smoothness) {
		this.generatePerlinNoise2D(w, h, o, range);
		smooth(smoothness);
		this.enable2DRenderMode();
	}

	@Override
	public void generateRandomNoise(int w, int h, Range<Float> range, int smoothness) {
		this.generateRandomNoise(w, h, range);
		smooth(smoothness);
		this.enable2DRenderMode();
	}

	@Override
	public void generateMidpointDisplacement(int w, Range<Float> range, int smoothness) {
		this.generateMidpointDisplacement(w, range);
		smooth(smoothness);
		this.enable2DRenderMode();
	}

	@Override
	public void generateDiamondSquare(int w, Range<Float> range, int smoothness) {
		this.generateDiamondSquare(w, range);
		smooth(smoothness);
		this.enable2DRenderMode();
	}

	@Override
	public void generateSimplexNoise2D(int w, int h, int o, Range<Float> range, int smoothness) {
		this.generateSimplexNoise2D(w, h, o, range);
		smooth(smoothness);
		this.enable2DRenderMode();
	}

	@Override
	public void loadHeightmap(BufferedImage heightmap) {
		if (DataInspector.notNull(heightmap)) {
			data2D.setData(new float[heightmap.getWidth()][heightmap.getHeight()]);
			for (int i = 0; i < data2D.getSizeX(); i++) {
				for (int j = 0; j < data2D.getSizeY(); j++) {
					int val = heightmap.getRGB(i, j) & 0x000000ff;
					data2D.getData()[i][j] = val;
				}
			}
			this.resetDisplayLists();
		}
	}

	@Override
	public void generatePerlinNoise3D(int sx, int sy, int sz, int o, int smoothness) {
		float[][][] data = Algy.generatePerlin(sx, sy, sz, smoothness, 0.01f, 1.0f, MapMirrorType.NO_MIRROR);
		this.data3D.setData(data);
		this.enable3DRenderMode();
	}

	@Override
	public void generateSimplexNoise3D(int sx, int sy, int sz, int o, int smoothness) {
		this.enable3DRenderMode();

	}

	@Override
	public void resetViewportDisplayList(int index) {
		this.viewportRenderer.resetDisplayList(this, index);
	}

	public void resetAllViewportDisplayLists() {
		for (int i = 0; i < NUM_VIEWS; i++) {
			this.resetViewportDisplayList(i);
		}
	}

	@Override
	public void changeScaleFactor(float factor, int viewIndex) {
		if (viewIndex < this.scaleFactors.length) {
			if (this.scaleFactors[viewIndex] + factor > 0) {
				this.scaleFactors[viewIndex] += factor;
			}
		}
	}

	@Override
	public void toggleCompleteParallelCoordinates() {
		this.useCompleteParallelCoordinates = !this.useCompleteParallelCoordinates;
	}

	@Override
	public void setHoverData(int viewportIndex, DataElement data, float x, float y) {
		boolean found = false;
		for (int i = 0; i < this.renderers.size() && !found; i++) {
			for (ARenderer aRenderer : this.renderers.get(i)) {
				if (aRenderer != null) {
					if (i == viewportIndex && aRenderer.hasHoverDataRenderer() && data != null) {
						aRenderer.toggleHover(true);
						aRenderer.getHoverDataRenderer().setHoverData(data);
						aRenderer.getHoverDataRenderer().setX(x);
						aRenderer.getHoverDataRenderer().setY(y);
						found = true;
					} else {
						aRenderer.toggleHover(false);
					}
				}
			}
		}
	}

	@Override
	public void setView(int viewportIndex, String viewName) {
		if (viewportIndex > -1 && viewportIndex < this.renderers.size() && this.rendererMapping.containsKey(viewName)
				&& this.viewportInteractions.containsKey(viewName)) {
			GraphicsHoverHandler.setHoverHandlerForView(viewportIndex, viewName);
			ARenderer[] renderers = this.rendererMapping.get(viewName);
			ARenderer[] newRenderers = new ARenderer[renderers.length];
			for (int i = 0; i < renderers.length; i++) {
				if (renderers[i] != null) {
					Class<?> rClass = renderers[i].getClass();
					try {
						newRenderers[i] = (ARenderer) rClass.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			this.renderers.set(viewportIndex, newRenderers);
			this.viewportRectangles.get(viewportIndex).setCallback(this.viewportInteractions.get(viewName));
			this.resetAllViewportDisplayLists();
		}
	}

	@Override
	public List<InteractableRectangle> getViewports() {
		return this.viewportRectangles;
	}

	@Override
	public void setDataType(int viewportIndex, DataType[] type) {
		if (type != null && viewportIndex > -1 && viewportIndex < NUM_VIEWS)
			this.selectedDataTypes[viewportIndex] = type;
	}

	public DataType[] getViewportDataType(int viewportIndex) {
		return cleanDataTypes(this.selectedDataTypes[viewportIndex]);
	}

	private DataType[] cleanDataTypes(DataType[] v) {
		int r, w;
		final int n = r = w = v.length;
		DataType[] copy = Arrays.copyOfRange(v, 0, v.length);
		while (r > 0) {
			final DataType s = copy[--r];
			if (s != null) {
				copy[--w] = s;
			}
		}
		return Arrays.copyOfRange(copy, w, n);
	}

	@Override
	public void toggleDataType(int viewportIndex, DataType type, boolean toggled, int position) {
		if (viewportIndex > -1 && viewportIndex < this.selectedDataTypes.length && position > -1
				&& position < this.selectedDataTypes[viewportIndex].length) {
			if (!Arrays.asList(this.selectedDataTypes[viewportIndex]).contains(type) || !toggled)
				this.selectedDataTypes[viewportIndex][position] = (toggled ? type : null);
		}
	}

	@Override
	public void setStatisticObject(int viewportIndex, StatisticObject stats) {
		this.statistics[viewportIndex] = stats;
	}

	public StatisticObject[] getStatistics() {
		return statistics;
	}

	public void toggleStats(int viewportIndex, boolean toggled) {
		this.statsEnabled[viewportIndex] = toggled;
	}

	public boolean areStatsEnabled(int viewportIndex) {
		return this.statsEnabled[viewportIndex];
	}

	@Override
	public void rotateView(int viewportIndex, int angle) {
		this.pointCloudWorldRotations[viewportIndex] += angle;
	}

	public float[] getPointCloudWorldRotations() {
		return pointCloudWorldRotations;
	}

	public static void resetAllViewports() {
		engineInstance.resetAllViewportDisplayLists();
	}

	@Override
	public void toggleTimeColorInvert(boolean toggle) {
		INVERT_TIME_COLOR = toggle ;
	}

}
