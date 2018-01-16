package com.base.engine.rendering.buffers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.TextureLoader;

import com.base.common.ColorUtil;
import com.base.common.MadColor;
import com.base.common.resources.Callback;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.Point;
import com.base.common.resources.StatisticObject;
import com.base.engine.Engine;
import com.base.engine.RenderUtil;
import com.base.engine.Settings;
import com.base.engine.font.NEW.NewFontManager;
import com.base.engine.interaction.GraphicsHoverHandler;
import com.base.engine.interaction.data.AHoverBufferData;
import com.base.engine.interaction.data.BarChartHoverBufferData;
import com.base.engine.interaction.data.LineChartHoverBufferData;
import com.base.engine.interaction.data.PointCloudHoverBufferData;
import com.base.engine.interaction.data.Rectangle;
import com.base.engine.models.World;
import com.base.engine.rendering.BarChartRenderer;
import com.base.engine.rendering.GridRenderer;
import com.base.engine.rendering.LineChartRenderer;
import com.base.engine.rendering.MiniMapRenderer;
import com.base.engine.rendering.ParallelCoordinatesRenderer;
import com.base.engine.rendering.PointCloudClusterRenderer;
import com.base.engine.rendering.PointCloudRenderer;
import com.base.engine.rendering.hover.AHoverDataRenderer;

/**
 * 
 * @author Markus
 *
 */
public class VBOHandler {

	/**
	 * 
	 */
	private static Map<Integer, Integer> vertex_vbos = new HashMap<>();

	/**
	 * 
	 */
	private static Map<Integer, Integer> color_vbos = new HashMap<>();

	/**
	 * 
	 */
	private static Map<Integer, Integer> elements = new HashMap<>();

	/**
	 * 
	 */
	private static Map<Integer, Boolean> revalidate = new HashMap<>();

	/**
	 * 
	 */
	private static Map<String, List<IVBORenderer>> internalRenderers = new HashMap<>();

	/**
	 * 
	 */
	private static Map<String, List<Callback>> internalCallbacks = new HashMap<>();

	/**
	 * 
	 */
	static {

		/*
		 * Define different renderers
		 */
		VBOPointCloudRenderer pointCloudRenderer = new VBOPointCloudRenderer();
		VBOPointCloudClusterRenderer PointCloudClusterRenderer = new VBOPointCloudClusterRenderer();
		VBOGridRenderer gridRenderer = new VBOGridRenderer();
		VBOBarChartRenderer barChartRenderer = new VBOBarChartRenderer();
		VBOLineChartRenderer lineChartRenderer = new VBOLineChartRenderer();
		VBOParallelCoordinatesRenderer parallelCoordinatesRenderer = new VBOParallelCoordinatesRenderer();

		/*
		 * Add renderers to rendering techniques
		 */
		List<IVBORenderer> pointCloud = new ArrayList<>();
		pointCloud.add(pointCloudRenderer);

		List<IVBORenderer> pointCloudCluster = new ArrayList<>();
		pointCloudCluster.add(PointCloudClusterRenderer);

		List<IVBORenderer> grid = new ArrayList<>();
		grid.add(gridRenderer);

		List<IVBORenderer> minimap = new ArrayList<>();
		minimap.add(gridRenderer);
		minimap.add(pointCloudRenderer);

		List<IVBORenderer> barChart = new ArrayList<>();
		barChart.add(barChartRenderer);

		List<IVBORenderer> lineChart = new ArrayList<>();
		lineChart.add(lineChartRenderer);

		List<IVBORenderer> parallelCoordinates = new ArrayList<>();
		parallelCoordinates.add(parallelCoordinatesRenderer);

		/*
		 * associate renderer classes with rendering methods
		 */
		internalRenderers.put(PointCloudRenderer.class.getSimpleName(), pointCloud);
		internalRenderers.put(PointCloudClusterRenderer.class.getSimpleName(), pointCloudCluster);
		internalRenderers.put(GridRenderer.class.getSimpleName(), grid);
		internalRenderers.put(MiniMapRenderer.class.getSimpleName(), minimap);
		internalRenderers.put(BarChartRenderer.class.getSimpleName(), barChart);
		internalRenderers.put(LineChartRenderer.class.getSimpleName(), lineChart);
		internalRenderers.put(ParallelCoordinatesRenderer.class.getSimpleName(), parallelCoordinates);

		/*
		 * Define different callback methods for rendering
		 */
		Callback largePointSizeCallback = new Callback() {
			public Object execute(Object... data) {
				glPointSize(5f);
				return 0;
			}
		};
		Callback smallPointSizeCallback = new Callback() {
			public Object execute(Object... data) {
				glPointSize(2f);
				return 0;
			}
		};

		Callback switch2D = new Callback() {
			@Override
			public Object execute(Object... data) {
				RenderUtil.switch2D(-1, -1, 1, 1);
				return 0;
			}
		};

		/*
		 * Add callback methods to rendering techniques
		 */
		List<Callback> cPointCloud = new ArrayList<>();
		cPointCloud.add(largePointSizeCallback);

		List<Callback> cPointCloudCluster = new ArrayList<>();
		cPointCloudCluster.add(largePointSizeCallback);

		List<Callback> cGrid = new ArrayList<>();

		List<Callback> cMinimap = new ArrayList<>();
		cMinimap.add(null);
		cMinimap.add(smallPointSizeCallback);

		List<Callback> cBarChart = new ArrayList<>();
		cBarChart.add(switch2D);

		List<Callback> cLineChart = new ArrayList<>();
		cLineChart.add(switch2D);

		List<Callback> cParallelCoordinates = new ArrayList<>();
		cParallelCoordinates.add(switch2D);

		/*
		 * associate renderer classes with callback methods
		 */
		internalCallbacks.put(PointCloudRenderer.class.getSimpleName(), cPointCloud);
		internalCallbacks.put(PointCloudClusterRenderer.class.getSimpleName(), cPointCloudCluster);
		internalCallbacks.put(GridRenderer.class.getSimpleName(), cGrid);
		internalCallbacks.put(MiniMapRenderer.class.getSimpleName(), cMinimap);
		internalCallbacks.put(BarChartRenderer.class.getSimpleName(), cBarChart);
		internalCallbacks.put(LineChartRenderer.class.getSimpleName(), cLineChart);
		internalCallbacks.put(ParallelCoordinatesRenderer.class.getSimpleName(), cParallelCoordinates);

	}

	/**
	 * 
	 * @param rendererClassName
	 * @return
	 */
	public static int[] getHashCodesForRenderMethod(String rendererClassName) {
		List<IVBORenderer> renderers = internalRenderers.get(rendererClassName);
		int[] output = new int[renderers.size()];
		for (int i = 0; i < renderers.size(); i++) {
			output[i] = renderers.get(i).hashCode();
		}
		return output;
	}

	/**
	 * 
	 * @param viewportIndex
	 * @return
	 */
	public static boolean bufferExists(int viewportIndex) {
		return vertex_vbos.containsKey(viewportIndex) && !revalidate.get(viewportIndex);
	}

	/**
	 * 
	 * @param viewportIndex
	 */
	public static void revalidate(int viewportIndex) {
		revalidate.put(viewportIndex, true);
	}

	/**
	 * 
	 * @param data
	 * @param viewportIndex
	 */
	public static void handleBufferCreation(String rendererClassName, int viewportIndex, Object data, DataType[] type,
			StatisticObject stats) {
		if (internalRenderers.containsKey(rendererClassName)) {
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				int bufferHashCode = viewportIndex + renderer.hashCode();
				if (!VBOHandler.bufferExists(bufferHashCode)) {
					renderer.create(bufferHashCode, data, type, stats);
				}
			}
		}
	}

	/**
	 * 
	 * @param viewportIndex
	 */
	public static void renderBuffer(String rendererClassName, int viewportIndex, DataType[] type) {
		if (internalRenderers.containsKey(rendererClassName)) {
			int i = 0;
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				Callback callback = null;
				if (internalCallbacks.get(rendererClassName).size() > i)
					callback = internalCallbacks.get(rendererClassName).get(i);
				renderer.render(viewportIndex + renderer.hashCode(), callback, type);
				i++;
			}
		}
	}

	private static FloatBuffer[] initBuffers(int viewportIndex, int numElements, int vertices, int colors) {
		vertex_vbos.put(viewportIndex, glGenBuffers());
		color_vbos.put(viewportIndex, glGenBuffers());
		elements.put(viewportIndex, numElements);
		revalidate.put(viewportIndex, false);
		FloatBuffer v = BufferUtils.createFloatBuffer(elements.get(viewportIndex) * vertices);
		FloatBuffer c = BufferUtils.createFloatBuffer(elements.get(viewportIndex) * colors);
		return new FloatBuffer[] { v, c };
	}

	private static void finalizeBuffers(int viewportIndex, FloatBuffer vBuffer, FloatBuffer cBuffer) {
		vBuffer.flip();
		cBuffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vertex_vbos.get(viewportIndex));
		glBufferData(GL_ARRAY_BUFFER, vBuffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, color_vbos.get(viewportIndex));
		glBufferData(GL_ARRAY_BUFFER, cBuffer, GL_DYNAMIC_DRAW);
	}

	private static void masterRenderMethod(int viewportIndex, int vertices, int colors, int renderMode,
			Callback renderSettingsFunction) {

		if (!bufferExists(viewportIndex))
			return;

		if (renderSettingsFunction != null) {
			renderSettingsFunction.execute();
		}

		// bind vertices
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertex_vbos.get(viewportIndex));
		glVertexPointer(vertices, GL_FLOAT, 0, 0);

		// bind colors
		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, color_vbos.get(viewportIndex));
		glColorPointer(colors, GL_FLOAT, 0, 0);

		// draw buffers
		glDrawArrays(renderMode, 0, elements.get(viewportIndex));

		// close buffers
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);

	}

	/*
	 * ------------------------------------------------------------------------
	 * POINT CLOUD RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static abstract class AIVBORenderer implements IVBORenderer {

		protected StatisticObject stats;
		protected boolean statsEnabled = false;

		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {
			this.stats = stats;
			this.statsEnabled = this.stats != null;
		}

		protected boolean canRenderStats() {
			return this.statsEnabled;
		}

		protected abstract void renderStats();

	}

	private static class VBOPointCloudRenderer extends AIVBORenderer {

		private float scale = Settings.getAxisScale() / 2;

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			super.create(viewportIndex, data, type, stats);

			if (data == null)
				return;
			List<DataElement> points = (List<DataElement>) data;

			FloatBuffer[] buffers = initBuffers(viewportIndex, points.size(), 3, 4);

			float maxTime = PointCloudRenderer.getMaxTimeFromData(points);
			for (DataElement vertex : points) {

				// use this for x,y,z rotation
				// buffers[0].put(new float[] { vertex.getX(), vertex.getZ(),
				// vertex.getY() });

				// use this for GPS position
				buffers[0].put(new float[] { vertex.getLat() - scale, vertex.getZ(), vertex.getLng() - scale });

				float[] color = PointCloudRenderer.calcVertexColor(vertex.getLat(), vertex.getZ(), vertex.getLng(),
						vertex.getTime(), maxTime);

				buffers[1].put(new float[] { color[0], color[1], color[2], color[3] });
			}

			int playerPosIndex = Engine.INVERT_TIME_COLOR ? 0 : (points.size() > 1 ? points.size() - 1 : 0);
			DataElement lastVertex = points.get(playerPosIndex);
			World.movePlayer(lastVertex.getLat() - scale, lastVertex.getZ(), lastVertex.getLng() - scale);

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);

			PointCloudHoverBufferData buffer = new PointCloudHoverBufferData();
			buffer.setRawData(points);
			GraphicsHoverHandler.storeBufferVertexDataAtCurrentIndex(buffer);

		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			masterRenderMethod(viewportIndex, 3, 4, GL_POINTS, callback);
		}

		@Override
		protected void renderStats() {
			if (this.canRenderStats()) {

			}
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * POINT CLOUD CLUSTER RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOPointCloudClusterRenderer extends AIVBORenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			super.create(viewportIndex, data, type, stats);

			if (data == null)
				return;
			List<Cluster> clusters = (List<Cluster>) data;
			int size = 0;
			for (Cluster cluster : clusters) {
				size += cluster.getNumPoints();
			}
			FloatBuffer[] buffers = initBuffers(viewportIndex, size, 3, 4);
			for (int i = 0; i < clusters.size(); i++) {
				Cluster cluster = clusters.get(i);
				Point centroid = cluster.getCentroid();
				int centroidHash = (int) ((centroid.x * 137 + centroid.y * 149 + centroid.z * 163));
				float[] clusterColor = ColorUtil.convertToRenderableColor4f(ColorUtil.intToRGB(centroidHash));
				for (Point point : cluster.getPoints()) {
					buffers[0].put(new float[] { point.x, point.y, point.z });
					buffers[1].put(clusterColor);
				}
			}
			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			masterRenderMethod(viewportIndex, 3, 4, GL_POINTS, callback);
		}

		@Override
		protected void renderStats() {
			if (this.canRenderStats()) {

			}
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * GRID RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOGridRenderer implements IVBORenderer {

		private int campusTexture = 0;

		int maxX = Settings.getAxisScale() / 2;
		int maxY = Settings.getAxisScale() / 2;
		int minX = -maxX;
		int minY = -maxY;

		int gridSizeX = 1000;
		int gridSizeY = 1000;

		public VBOGridRenderer() {
			try {
				campusTexture = TextureLoader.getTexture("png", new FileInputStream(new File("res/textures/map.png")))
						.getTextureID();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			float alphaChannel = 0.1f;

			int size = 6 + ((Math.abs(minX) + maxX) / gridSizeX) * ((Math.abs(minY) + maxY) / gridSizeY);

			FloatBuffer[] buffers = initBuffers(viewportIndex, size, 3, 4);

			float[] grid = new float[] { Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getGreen(),
					Settings.FONT_COLOR.getBlue(), alphaChannel };

			for (int x = minX; x < maxX; x += gridSizeX) {
				buffers[0].put(new float[] { x, 0, minY, x, 0, maxY });
				buffers[1].put(grid);
				buffers[1].put(grid);
			}
			for (int y = minY; y < maxY; y += gridSizeY) {
				buffers[0].put(new float[] { minX, 0, y, maxX, 0, y });
				buffers[1].put(grid);
				buffers[1].put(grid);
			}

			// push axes to the buffers
			// buffers[0].put(new float[] { 0, 0, 0, maxX, 0, 0, 0, 0, 0, 0,
			// maxY, 0, 0, 0, 0, 0, 0, maxY });

			buffers[0].put(new float[] { minX, 0, minY, maxX, 0, minY, minX, 0, minY, minY, maxY * 2, minY, minX, 0,
					minY, minX, 0, maxY });

			float[] red = new float[] { Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(),
					Settings.X_COLOR.getBlue(), Settings.X_COLOR.getAlpha() };
			float[] green = new float[] { Settings.Y_COLOR.getRed(), Settings.Y_COLOR.getGreen(),
					Settings.Y_COLOR.getBlue(), Settings.Y_COLOR.getAlpha() };
			float[] blue = new float[] { Settings.Z_COLOR.getRed(), Settings.Z_COLOR.getGreen(),
					Settings.Z_COLOR.getBlue(), Settings.Z_COLOR.getAlpha() };

			buffers[1].put(red);
			buffers[1].put(red);
			buffers[1].put(blue);
			buffers[1].put(blue);
			buffers[1].put(green);
			buffers[1].put(green);

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			glLineWidth(1);
			masterRenderMethod(viewportIndex, 3, 4, GL_LINES, callback);

			// glDisable(GL_BLEND);
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, campusTexture);

			glColor4f(1, 1, 1, 0.5f);

			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex3f(minX, 0, minY);
			glTexCoord2f(1, 0);
			glVertex3f(maxX, 0, minY);
			glTexCoord2f(1, 1);
			glVertex3f(maxX, 0, maxY);
			glTexCoord2f(0, 1);
			glVertex3f(minX, 0, maxY);
			glEnd();
			// glEnable(GL_BLEND);

			glBindTexture(GL_TEXTURE_2D, 0);
			glDisable(GL_TEXTURE_2D);

			World.render();

		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * ABSTRACT CHART RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static abstract class AVBOChartRenderer extends AIVBORenderer {
		protected final float yMin = -0.8f;
		protected final float xMin = -0.7f;
		protected final float yMax = Math.abs(yMin);
		protected final float xMax = 0.9f;
		protected int numItems = 50;
		protected float xStep = 0.1f;
		protected float biggestX, biggestY;

		protected float maxTime = 0f;

		private int arrowTexture = 0;

		protected boolean enabled = false;

		protected DataType propertyOnYAxes = DataType.X;

		public AVBOChartRenderer() {
			try {
				arrowTexture = TextureLoader.getTexture("png", new FileInputStream(new File("res/textures/arrow.png")))
						.getTextureID();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		protected static MadColor getColorByType(DataType type) {
			switch (type) {
			case X:
				return Settings.X_COLOR;
			case Y:
				return Settings.Y_COLOR;
			case Z:
				return Settings.Z_COLOR;
			default:
				return Settings.X_COLOR;
			}
		}

		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {
			super.create(viewportIndex, data, type, stats);
			this.propertyOnYAxes = type[0];
			if (data != null) {
				List<DataElement> list = (List<DataElement>) data;
				int index = list.size() > 1 ? list.size() - 1 : 0;
				if (list.size() > 0)
					this.maxTime = list.get(index).getTime();
			}
		}

		protected void renderAxes(DataType[] type) {

			glLineWidth(1f);

			float upperBound = (float) (Math.ceil(this.biggestY) + Math.ceil(this.biggestY) * 0.01f);
			int steps = 20;
			float stepY = upperBound / steps;

			this.setAxesColor();
			this.setAxesStrength();
			glBegin(GL_LINES);
			glVertex2f(xMin - 0.05f, yMax);
			glVertex2f(xMax + 0.05f, yMax);
			glVertex2f(xMin - 0.025f, yMax + 0.05f);
			glVertex2f(xMin - 0.025f, yMin - 0.05f);

			for (int i = 0; i <= numItems; i++) {
				float x = this.calcPosX(i) + this.xStep / 2f;
				glVertex2f(x, yMax + 0.025f);
				glVertex2f(x, yMax);
			}

			for (int i = 0; i < steps; i++) {
				float currentValue = (i + 1) * stepY;
				float y = this.calcValue_yAxes(currentValue);
				glVertex2f(xMin - 0.0375f, y);
				glVertex2f(xMin - 0.025f, y);
			}

			glEnd();

			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, arrowTexture);

			glColor4f(Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getGreen(),
					Settings.FONT_COLOR.getAlpha());

			glPushMatrix();
			RenderUtil.rotateTexture(180);
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(xMin - 0.0375f, yMin - 0.05f);
			glTexCoord2f(1, 0);
			glVertex2f(xMin - 0.0125f, yMin - 0.05f);
			glTexCoord2f(1, 1);
			glVertex2f(xMin - 0.0125f, yMin - 0.075f);
			glTexCoord2f(0, 1);
			glVertex2f(xMin - 0.0375f, yMin - 0.075f);
			glEnd();
			glPopMatrix();

			glPushMatrix();
			RenderUtil.rotateTexture(-90);
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(xMax + 0.05f, yMax - 0.0125f);
			glTexCoord2f(1, 0);
			glVertex2f(xMax + 0.0675f, yMax - 0.0125f);
			glTexCoord2f(1, 1);
			glVertex2f(xMax + 0.0675f, yMax + 0.0125f);
			glTexCoord2f(0, 1);
			glVertex2f(xMax + 0.05f, yMax + 0.0125f);
			glEnd();
			glPopMatrix();

			glBindTexture(GL_TEXTURE_2D, 0);
			glDisable(GL_TEXTURE_2D);

			NewFontManager.prepare();

			for (int i = 0; i <= numItems; i += 5) {
				float x = this.calcPosX(i) + this.xStep / 2f;
				int time = Math.round((this.maxTime / numItems) * i);
				NewFontManager.renderText(x * 460, yMax * 460 + 65, 16, 2, String.valueOf(time));
			}
			for (int i = 0; i < steps; i += 3) {
				float currentValue = (i + 1) * stepY;
				float y = this.calcValue_yAxes(currentValue);
				NewFontManager.renderText(xMin * 460 - 150, y * 460, 16, 2, String.valueOf(currentValue));
			}

			NewFontManager.renderText(-400, -460, 16, 2, type[0].name);
			NewFontManager.renderText(455, 450, 16, 2, "t");
			NewFontManager.close();
			RenderUtil.switch2D(-1, -1, 1, 1);

		}

		protected float calcPosX(int i) {
			return (i * xStep) + xMin;
		}

		protected float calcXStep() {
			xStep = (((xMax - xMin)) / ((float) numItems));
			return xStep;
		}

		@SuppressWarnings("unused")
		public void setBiggestX(float biggestX) {
			this.biggestX = biggestX;
		}

		public void setBiggestY(float biggestY) {
			this.biggestY = biggestY;
		}

		protected DataElement getBiggest(List<DataElement> inputData, DataType type) {
			try {
				return Collections.max(inputData, new Comparator<DataElement>() {
					@Override
					public int compare(DataElement arg0, DataElement arg1) {
						if (arg0 == null && arg1 == null)
							return 0;
						if (arg0 == null) {
							return -1;
						}
						if (arg1 == null)
							return 1;
						float v1 = DataType.getValueByType(type, arg0);
						float v2 = DataType.getValueByType(type, arg1);
						return Float.compare(v1, v2);
					}
				});
			} catch (Exception e) {
				return null;
			}
		}

		protected float calcValue_xAxes(float value) {
			float valueRatio = value / this.biggestX;
			return valueRatio * (xMax - xMin) + xMin;
		}

		protected float calcValue_yAxes(float value) {
			float valueRatio = value / this.biggestY;
			return valueRatio * (yMin - yMax) + yMax;
		}

		protected void setAxesColor() {
			glColor4f(Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getGreen(),
					Settings.FONT_COLOR.getAlpha());
		}

		protected void setAxesStrength() {
			glLineWidth(1.5f);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * BAR CHART RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOBarChartRenderer extends AVBOChartRenderer {

		private final float borderWidth = 0.005f;

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			super.create(viewportIndex, data, type, stats);

			this.enabled = data != null;
			if (!enabled)
				return;

			int verticesPerItem = 4;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement biggest = this.getBiggest(inputData, type[0]);
			this.setBiggestY(DataType.getValueByType(type[0], biggest));

			this.numItems = inputData.size();

			this.calcXStep();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems * 8, 2, 4);

			List<Rectangle> bars = new ArrayList<>();

			MadColor color = getColorByType(type[0]);

			for (int i = 0; i < numItems; i++) {

				DataElement e = inputData.get(i);

				float x = this.calcPosX(i);
				float value = this.calcValue_yAxes(DataType.getValueByType(type[0], e));

				buffers[0].put(new float[] { x, value, x + xStep, value, x + xStep, yMax, x, yMax });
				for (int j = 0; j < verticesPerItem; j++)
					buffers[1].put(new float[] { 0.2f, 0.2f, 0.2f, 0.5f });

				buffers[0].put(new float[] { x + this.borderWidth, value + this.borderWidth,
						x + xStep - this.borderWidth, value + this.borderWidth, x + xStep - this.borderWidth,
						yMax - this.borderWidth, x + this.borderWidth, yMax - this.borderWidth });

				bars.add(new Rectangle(x, x + xStep, -yMax, -value));

				for (int j = 0; j < verticesPerItem; j++) {
					if (AHoverDataRenderer.activeElement != null
							&& AHoverDataRenderer.activeElement.getId() == e.getId()) {
						buffers[1].put(new float[] { Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getGreen(),
								Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getAlpha() });
					} else {
						buffers[1].put(
								new float[] { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() });
					}
				}

			}
			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);

			// create and store buffer for hover interaction
			BarChartHoverBufferData buffer = new BarChartHoverBufferData();
			buffer.setRawData(inputData);
			buffer.setBars(bars);
			GraphicsHoverHandler.storeBufferVertexDataAtCurrentIndex(buffer);

		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			if (this.enabled) {
				glDisable(GL_BLEND);
				masterRenderMethod(viewportIndex, 2, 4, GL_QUADS, callback);
				glEnable(GL_BLEND);
				this.renderAxes(type);
				this.renderStats();
			}
		}

		@Override
		protected void renderStats() {
			if (this.canRenderStats()) {
				float y1 = this.calcValue_yAxes(stats.getA());
				glColor4f(1, 1, 1, 1);
				glBegin(GL_POINTS);
				glVertex2f(xMin, y1);
				glVertex2f(xMax, y1);
				glEnd();
				glBegin(GL_LINES);
				glVertex2f(xMin, y1);
				glVertex2f(xMax, y1);
				glEnd();

				NewFontManager.prepare();
				NewFontManager.renderText(-100, y1 * 460 - 30, 16, 3, "d=" + stats.getA());
				NewFontManager.close();
				RenderUtil.switch2D(-1, -1, 1, 1);

			}
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * LINE CHART RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOLineChartRenderer extends AVBOChartRenderer {

		private List<DataElement> inputData;

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			super.create(viewportIndex, data, type, stats);

			this.enabled = data != null;
			if (!enabled)
				return;

			this.inputData = (List<DataElement>) data;
			DataElement biggest = this.getBiggest(inputData, type[0]);
			this.setBiggestY(DataType.getValueByType(type[0], biggest));

			this.numItems = inputData.size();
			this.calcXStep();

			// float maxTime = PointCloudRenderer.getMaxTimeFromData(inputData);

			List<Rectangle> dots = new ArrayList<>();
			float delta = 0.025f;

			MadColor color = getColorByType(type[0]);

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems, 2, 4);
			for (int i = 0; i < numItems; i++) {

				DataElement e = inputData.get(i);

				float valueX = this.calcValue_xAxes(e.getX());
				valueX = this.calcPosX(i);
				float valueY = this.calcValue_yAxes(DataType.getValueByType(type[0], e));

				buffers[0].put(new float[] { valueX, valueY });
				// buffers[1].put(PointCloudRenderer.calcVertexColor(e.getX(),

				if (AHoverDataRenderer.activeElement != null && AHoverDataRenderer.activeElement.getId() == e.getId()) {
					buffers[1].put(new float[] { Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getGreen(),
							Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getAlpha() });
				} else {
					buffers[1].put(new float[] { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() });
				}

				dots.add(new Rectangle(valueX - delta, valueX + delta, -valueY - delta, -valueY + delta));

			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);

			// create and store buffer for hover interaction
			LineChartHoverBufferData buffer = new LineChartHoverBufferData();
			buffer.setRawData(inputData);
			buffer.setExtendedDots(dots);
			GraphicsHoverHandler.storeBufferVertexDataAtCurrentIndex(buffer);
		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			if (this.enabled) {
				glLineWidth(1.5f);
				glPointSize(8);
				masterRenderMethod(viewportIndex, 2, 4, GL_LINE_STRIP, callback);

				MadColor color = getColorByType(type[0]);

				if (this.inputData != null) {

					glBegin(GL_POINTS);
					for (int i = 0; i < numItems; i++) {
						DataElement e = inputData.get(i);
						if (AHoverDataRenderer.activeElement != null
								&& AHoverDataRenderer.activeElement.getId() == e.getId()) {
							glColor4f(Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getGreen(),
									Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getAlpha());
						} else {
							glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
						}
						glVertex2f(this.calcPosX(i), this.calcValue_yAxes(DataType.getValueByType(propertyOnYAxes, e)));
					}
					glEnd();
				}

				this.renderAxes(type);
				this.renderStats();
			}
		}

		@Override
		protected void renderStats() {
			if (this.canRenderStats()) {
				float y1 = this.calcValue_yAxes(stats.getB());
				float y2 = this.calcValue_yAxes(stats.getA() * numItems) - y1;
				glColor4f(1, 1, 1, 1);
				glBegin(GL_POINTS);
				glVertex2f(xMin, y1);
				glVertex2f(xMax, y2);
				glEnd();
				glBegin(GL_LINES);
				glVertex2f(xMin, y1);
				glVertex2f(xMax, y2);
				glEnd();

				NewFontManager.prepare();
				NewFontManager.renderText(-100, 0 - 30, 16, 3, "f(x)=" + stats.getA() + "x+" + stats.getB());
				NewFontManager.close();
				RenderUtil.switch2D(-1, -1, 1, 1);
			}
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * PARALLEL COORDINATES RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOParallelCoordinatesRenderer extends AVBOChartRenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats) {

			super.create(viewportIndex, data, type, stats);

			this.enabled = data != null;
			if (!enabled)
				return;

			int numProperties = type.length - 1;
			float xStep = (xMax - xMin) / (float) numProperties;
			int numVertices = (numProperties + 1) * 2 - 1;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement[] biggest = new DataElement[numProperties + 1];
			for (int i = 0; i <= numProperties; i++) {
				biggest[i] = this.getBiggest(inputData, type[i]);
			}

			this.numItems = inputData.size();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems * numVertices, 2, 4);

			float maxTime = PointCloudRenderer.getMaxTimeFromData(inputData);

			for (int i = 0; i < numItems; i++) {
				DataElement e = inputData.get(i);

				// calc color
				float time = e.isSampled() ? e.getTimeRange().getHiVal() : e.getTime();
				float[] color = PointCloudRenderer.calcVertexColor(e.getX(), e.getY(), e.getZ(), time, maxTime);

				if (AHoverDataRenderer.activeElement != null && AHoverDataRenderer.activeElement.getId() == e.getId()) {
					color = new float[] { Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getGreen(),
							Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getAlpha() };
				}

				float y = 0;

				for (int j = 0; j <= numProperties; j++) {
					this.setBiggestY(DataType.getValueByType(type[j], biggest[j]));
					float value = DataType.getValueByType(type[j], e);
					y = this.calcValue_yAxes(value);
					buffers[0].put(new float[] { xMin + j * xStep, y });
					if (j < numProperties) {
						this.setBiggestY(DataType.getValueByType(type[j + 1], biggest[j + 1]));
						float yNext = this.calcValue_yAxes(DataType.getValueByType(type[j + 1], e));
						buffers[0].put(new float[] { xMin + (j + 1) * xStep, yNext });
						buffers[1].put(color);
					}
					buffers[1].put(color);
				}

			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback, DataType[] type) {
			if (this.enabled) {

				float xStep = (xMax - xMin) / (float) (type.length - 1);
				float xStep2 = (920f) / (float) (type.length - 1);

				glLineWidth(1);
				masterRenderMethod(viewportIndex, 2, 4, GL_LINES, callback);
				this.setAxesStrength();
				this.setAxesColor();
				glBegin(GL_LINES);
				for (int i = 0; i < type.length; i++) {
					glVertex2f(xMin + i * xStep, yMax);
					glVertex2f(xMin + i * xStep, yMin);
				}
				glEnd();

				NewFontManager.prepare();
				for (int i = 0; i < type.length; i++) {
					NewFontManager.renderText(-460 + i * xStep2, 460, 16, 2, type[i].name);
				}
				NewFontManager.close();
				RenderUtil.switch2D(-1, -1, 1, 1);
			}
		}

		@Override
		protected void renderStats() {
			if (this.canRenderStats()) {

			}
		}

	}

}
