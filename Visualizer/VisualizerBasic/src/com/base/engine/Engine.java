package com.base.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import com.base.common.DataElement;
import com.base.common.EngineEvent;
import com.base.common.EngineEventListener;
import com.base.common.IRenderer;
import com.base.common.resources.DataInspector;
import com.base.common.resources.DataMap2D;
import com.base.common.resources.DataMap3D;
import com.base.common.resources.MathUtil;
import com.base.common.resources.Range;
import com.base.engine.font.FontManager;

import gen.algo.Algy;
import gen.algo.common.MapMirrorType;
import gen.algo.common.TerrainUtil;

public class Engine implements EngineEventListener, EngineInterfaces {

	public static boolean FULLSCREEN_ENABLED, VSYNC_ENABLED;
	public static int DISPLAY_WIDTH, DISPLAY_HEIGHT;

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
	private IRenderer[] renderers = new IRenderer[4];
	private Console console;
	private Camera camera;

	private DataMap2D data2D;
	private DataMap3D data3D;

	private List<DataElement> rawData;

	private RenderMode renderMode;

	public boolean isRunning() {
		return running;
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

	public List<DataElement> getRawRenderData() {
		return this.rawData;
	}

	public void setDataToRender2D(float[][] data) {
		data2D.setData(data);
	}

	public void setDataToRender3D(float[][][] data) {
		data3D.setData(data);
	}

	public void setRawRenderData(List<DataElement> data) {
		this.rawData = data;
		this.enableRawRenderMode();
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
		CommandInterpreter.create(this);
		RenderUtils.create();
		RenderUtils.initGL();
		FontManager.init();
		InputHandler.create(this, useSystemEvents);
		this.camera = new Camera(new Vector3f(100, -100, -100));

		/* define renderers and add them to the list of all renderers */
		this.viewportRenderer = new ViewportRenderer();
		PointCloudRenderer pointCloudRenderer = new PointCloudRenderer();
		for (int i = 0; i < this.renderers.length; i++) {
			this.renderers[i] = pointCloudRenderer;
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
			viewportRenderer.render(this, this.camera, this.renderers);
			console.render(null);
			viewportRenderer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getInput() {
		try {
			InputHandler.getKeyboardInput();
			InputHandler.getMouseInput();
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
				camera.move(EDirection.WEST);
				break;
			case MOVE_RIGHT:
				camera.move(EDirection.EAST);
				break;
			case MOVE_FORWARD:
				camera.move(EDirection.NORTH);
				break;
			case MOVE_BACKWARD:
				camera.move(EDirection.SOUTH);
				break;
			case MOVE_UP:
				camera.move(EDirection.UP);
				break;
			case MOVE_DOWN:
				camera.move(EDirection.DOWN);
				break;

			case LOOK_X:
				camera.yaw(Float.valueOf(String.valueOf(data[0])));
				break;
			case LOOK_Y:
				camera.pitch(Float.valueOf(String.valueOf(data[0])));
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void resetDisplayLists() {
		this.viewportRenderer.resetDisplayList(0);
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
		this.viewportRenderer.resetDisplayList(index);
	}

}
