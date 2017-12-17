package com.base.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;
import com.base.common.IRenderer;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.DataInspector;
import com.base.common.resources.DataMap2D;
import com.base.common.resources.DataMap3D;
import com.base.common.resources.MathUtil;
import com.base.common.resources.Range;
import com.base.engine.font.FontManager;
import com.base.engine.rendering.BarChartRenderer;
import com.base.engine.rendering.GridRenderer;
import com.base.engine.rendering.LineChartRenderer;
import com.base.engine.rendering.MiniMapRenderer;
import com.base.engine.rendering.PointCloudClusterRenderer;
import com.base.engine.rendering.PointCloudRenderer;
import com.base.engine.rendering.ViewportRenderer;
import com.base.engine.rendering.WhateverRenderer;

import gen.algo.Algy;
import gen.algo.common.MapMirrorType;
import gen.algo.common.TerrainUtil;

public class Engine implements EngineEventListener, EngineInterfaces {

	public static boolean FULLSCREEN_ENABLED, VSYNC_ENABLED;
	public static int DISPLAY_WIDTH, DISPLAY_HEIGHT;

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

	private boolean running = false;

	private ViewportRenderer viewportRenderer;
	private PointCloudRenderer pointCloudRenderer;
	private PointCloudClusterRenderer pointCloudClusterRenderer;
	private GridRenderer gridRenderer;
	private MiniMapRenderer minimapRenderer;
	private BarChartRenderer barChartRenderer;
	private LineChartRenderer lineChartRenderer;
	private WhateverRenderer whateverRenderer;

	private IRenderer[][] renderers = new IRenderer[NUM_VIEWS][];
	private float[] scaleFactors = new float[NUM_VIEWS];
	private Console console;
	private Camera[] cameras = new Camera[NUM_VIEWS];

	private DataMap2D data2D;
	private DataMap3D data3D;

	private List<DataElement> pointCloudData;
	private List<Cluster> pointCloudClusters;

	private RenderMode renderMode;

	public boolean isRunning() {
		return running;
	}

	public int getNumRenderersForViewport(int viewportIndex) {
		return this.renderers[viewportIndex].length;
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

	public List<DataElement> getPointCloudData() {
		return this.pointCloudData;
	}

	public List<Cluster> getPointCloudClusters() {
		return pointCloudClusters;
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

	public void setPointCloudData(List<DataElement> data) {
		this.pointCloudData = data;
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
		RenderUtils.create();
		RenderUtils.initGL();
		FontManager.init();
		InputHandler.create(this, useSystemEvents);

		/* define renderers and add them to the list of all renderers */
		this.viewportRenderer = new ViewportRenderer();
		this.pointCloudRenderer = new PointCloudRenderer();
		this.pointCloudClusterRenderer = new PointCloudClusterRenderer();
		this.gridRenderer = new GridRenderer();
		this.minimapRenderer = new MiniMapRenderer();
		this.barChartRenderer = new BarChartRenderer();
		this.lineChartRenderer = new LineChartRenderer();
		this.whateverRenderer = new WhateverRenderer();

		Camera.setSpeed(25f);

		for (int i = 0; i < this.renderers.length; i++) {
			switch (i) {
			case 0:
				this.renderers[i] = new IRenderer[4];
				this.renderers[i][0] = this.gridRenderer;
				this.renderers[i][1] = this.pointCloudRenderer;
				// this.renderers[i][2] = this.pointCloudClusterRenderer;
				this.renderers[i][3] = this.minimapRenderer;
				break;
			case 1:
				this.renderers[i] = new IRenderer[1];
				this.renderers[i][0] = this.lineChartRenderer;
				break;
			case 2:
				this.renderers[i] = new IRenderer[1];
				this.renderers[i][0] = this.barChartRenderer;
				break;
			case 3:
				this.renderers[i] = new IRenderer[1];
				this.renderers[i][0] = this.whateverRenderer;
				break;
			}
			this.scaleFactors[i] = 0.25f;
			this.cameras[i] = new Camera(new Vector3f(591, -985, 532));
			this.cameras[i].setPitch(23);
			this.cameras[i].setYaw(130);
		}
		/* ------------- */

		this.console = new Console();
	}

	private void prepareModels() {
		WavefrontModelHandler.loadModels();
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
			viewportRenderer.prepare();
			viewportRenderer.render(this, this.cameras, this.renderers, this.scaleFactors);
			viewportRenderer.close();
			console.render(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getInput() {
		try {
			InputHandler.getMouseInput();
			InputHandler.getKeyboardInput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			RenderUtils.exitGL();
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

	@Override
	public void changeScaleFactor(float factor, int viewIndex) {
		if (viewIndex < this.scaleFactors.length) {
			if (this.scaleFactors[viewIndex] + factor > 0) {
				this.scaleFactors[viewIndex] += factor;
			}
		}
	}

}
