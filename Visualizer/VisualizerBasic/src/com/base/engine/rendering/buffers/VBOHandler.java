package com.base.engine.rendering.buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

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

import javax.xml.crypto.Data;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.TextureLoader;

import com.base.common.ColorUtil;
import com.base.common.resources.Callback;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.Point;
import com.base.engine.RenderUtil;
import com.base.engine.Settings;
import com.base.engine.font.NEW.NewFontManager;
import com.base.engine.interaction.GraphicsHoverHandler;
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
	public static void handleBufferCreation(String rendererClassName, int viewportIndex, Object data, DataType type) {
		if (internalRenderers.containsKey(rendererClassName)) {
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				int bufferHashCode = viewportIndex + renderer.hashCode();
				if (!VBOHandler.bufferExists(bufferHashCode)) {
					renderer.create(bufferHashCode, data, type);
				}
			}
		}
	}

	/**
	 * 
	 * @param viewportIndex
	 */
	public static void renderBuffer(String rendererClassName, int viewportIndex) {
		if (internalRenderers.containsKey(rendererClassName)) {
			int i = 0;
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				Callback callback = null;
				if (internalCallbacks.get(rendererClassName).size() > i)
					callback = internalCallbacks.get(rendererClassName).get(i);
				renderer.render(viewportIndex + renderer.hashCode(), callback);
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

	private static class VBOPointCloudRenderer implements IVBORenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType type) {
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
				buffers[0].put(new float[] { vertex.getLat(), vertex.getY(), vertex.getLng() });

				float[] color = PointCloudRenderer.calcVertexColor(vertex.getX(), vertex.getY(), vertex.getZ(),
						vertex.getTime(), maxTime);
				buffers[1].put(new float[] { color[0], color[1], color[2], color[3] });
			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);

			PointCloudHoverBufferData buffer = new PointCloudHoverBufferData();
			buffer.setRawData(points);
			GraphicsHoverHandler.storeBufferVertexDataAtCurrentIndex(buffer);

		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			masterRenderMethod(viewportIndex, 3, 4, GL_POINTS, callback);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * POINT CLOUD CLUSTER RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOPointCloudClusterRenderer implements IVBORenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data, DataType type) {
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
		public void render(int viewportIndex, Callback callback) {
			masterRenderMethod(viewportIndex, 3, 4, GL_POINTS, callback);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * GRID RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOGridRenderer implements IVBORenderer {

		private int campusTexture = 0;
		int maxX = Settings.getAxisScale();
		int maxY = Settings.getAxisScale();
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
		public void create(int viewportIndex, Object data, DataType type) {
			int minX = 0;
			int minY = 0;

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
			buffers[0].put(new float[] { 0, 0, 0, maxX, 0, 0, 0, 0, 0, 0, maxY, 0, 0, 0, 0, 0, 0, maxY });

			float[] red = new float[] { Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(),
					Settings.X_COLOR.getBlue(), Settings.X_COLOR.getAlpha() };
			float[] green = new float[] { Settings.Y_COLOR.getRed(), Settings.Y_COLOR.getGreen(),
					Settings.Y_COLOR.getBlue(), Settings.Y_COLOR.getAlpha() };
			float[] blue = new float[] { Settings.Z_COLOR.getRed(), Settings.Z_COLOR.getGreen(),
					Settings.Z_COLOR.getBlue(), Settings.Z_COLOR.getAlpha() };

			buffers[1].put(red);
			buffers[1].put(red);
			buffers[1].put(green);
			buffers[1].put(green);
			buffers[1].put(blue);
			buffers[1].put(blue);

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			glLineWidth(1);
			masterRenderMethod(viewportIndex, 3, 4, GL_LINES, callback);

			// glDisable(GL_BLEND);
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, campusTexture);

			glColor4f(1, 1, 1, 0.5f);

			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex3f(0, 0, 0);
			glTexCoord2f(1, 0);
			glVertex3f(maxX, 0, 0);
			glTexCoord2f(1, 1);
			glVertex3f(maxX, 0, maxY);
			glTexCoord2f(0, 1);
			glVertex3f(0, 0, maxY);
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

	private static abstract class AVBOChartRenderer implements IVBORenderer {
		protected final float yMin = -0.8f;
		protected final float xMin = -0.9f;
		protected final float yMax = Math.abs(yMin);
		protected final float xMax = Math.abs(xMin);
		protected int numItems = 50;
		protected float xStep = 0.1f;
		protected float biggestX, biggestY;

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

		@Override
		public void create(int viewportIndex, Object data, DataType type) {
			this.propertyOnYAxes = type;
		}

		protected void renderAxes() {

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
				float y = this.calcValue_yAxes((i + 1) * stepY);
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
			NewFontManager.renderText(-460, -460, 16, 2, this.propertyOnYAxes.name);
			NewFontManager.renderText(440, 450, 16, 2, "t");
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
		public void create(int viewportIndex, Object data, DataType type) {

			super.create(viewportIndex, data, type);

			this.enabled = data != null;
			if (!enabled)
				return;

			int verticesPerItem = 4;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement biggest = this.getBiggest(inputData, type);
			this.setBiggestY(DataType.getValueByType(type, biggest));

			this.numItems = inputData.size();

			this.calcXStep();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems * 8, 2, 4);

			List<Rectangle> bars = new ArrayList<>();

			for (int i = 0; i < numItems; i++) {

				DataElement e = inputData.get(i);

				float x = this.calcPosX(i);
				float value = this.calcValue_yAxes(DataType.getValueByType(type, e));

				buffers[0].put(new float[] { x, value, x + xStep, value, x + xStep, yMax, x, yMax });
				for (int j = 0; j < verticesPerItem; j++)
					buffers[1].put(new float[] { 0.8f, 0.8f, 0.8f, 1f });

				buffers[0].put(new float[] { x + this.borderWidth, value + this.borderWidth,
						x + xStep - this.borderWidth, value + this.borderWidth, x + xStep - this.borderWidth,
						yMax - this.borderWidth, x + this.borderWidth, yMax - this.borderWidth });

				bars.add(new Rectangle(x, x + xStep, -yMax, -value));

				for (int j = 0; j < verticesPerItem; j++)
					buffers[1].put(new float[] { Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(),
							Settings.X_COLOR.getBlue(), Settings.X_COLOR.getAlpha() });

			}
			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);

			// create and store buffer for hover interaction
			BarChartHoverBufferData buffer = new BarChartHoverBufferData();
			buffer.setRawData(inputData);
			buffer.setBars(bars);
			GraphicsHoverHandler.storeBufferVertexDataAtCurrentIndex(buffer);

		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			if (this.enabled) {
				glDisable(GL_BLEND);
				masterRenderMethod(viewportIndex, 2, 4, GL_QUADS, callback);
				glEnable(GL_BLEND);
				this.renderAxes();
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
		public void create(int viewportIndex, Object data, DataType type) {

			super.create(viewportIndex, data, type);

			this.enabled = data != null;
			if (!enabled)
				return;

			this.inputData = (List<DataElement>) data;
			DataElement biggest = this.getBiggest(inputData, type);
			this.setBiggestY(DataType.getValueByType(type, biggest));

			this.numItems = inputData.size();
			this.calcXStep();

			// float maxTime = PointCloudRenderer.getMaxTimeFromData(inputData);

			List<Rectangle> dots = new ArrayList<>();
			float delta = 0.025f;

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems, 2, 4);
			for (int i = 0; i < numItems; i++) {

				DataElement e = inputData.get(i);

				float valueX = this.calcValue_xAxes(e.getX());
				valueX = this.calcPosX(i);
				float valueY = this.calcValue_yAxes(DataType.getValueByType(type, e));

				buffers[0].put(new float[] { valueX, valueY });
				// buffers[1].put(PointCloudRenderer.calcVertexColor(e.getX(),

				buffers[1].put(new float[] { Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(),
						Settings.X_COLOR.getBlue(), Settings.X_COLOR.getAlpha() });

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
		public void render(int viewportIndex, Callback callback) {
			if (this.enabled) {
				glLineWidth(1.5f);
				glPointSize(8);
				masterRenderMethod(viewportIndex, 2, 4, GL_LINE_STRIP, callback);

				if (this.inputData != null) {
					glColor4f(Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(), Settings.X_COLOR.getBlue(),
							Settings.X_COLOR.getAlpha());
					glBegin(GL_POINTS);
					for (int i = 0; i < numItems; i++) {
						DataElement e = inputData.get(i);
						glVertex2f(this.calcPosX(i),
								this.calcValue_yAxes(DataType.getValueByType(propertyOnYAxes, inputData.get(i))));
					}
					glEnd();
				}

				this.renderAxes();
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
		public void create(int viewportIndex, Object data, DataType type) {

			super.create(viewportIndex, data, type);

			this.enabled = data != null;
			if (!enabled)
				return;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement biggestX = this.getBiggest(inputData, DataType.X);
			DataElement biggestY = this.getBiggest(inputData, DataType.Y);
			DataElement biggestZ = this.getBiggest(inputData, DataType.Z);

			this.numItems = inputData.size();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems * 4, 2, 4);

			float maxTime = PointCloudRenderer.getMaxTimeFromData(inputData);

			for (int i = 0; i < numItems; i++) {
				DataElement e = inputData.get(i);

				// calc color
				float time = e.isSampled() ? e.getTimeRange().getHiVal() : e.getTime();
				float[] color = PointCloudRenderer.calcVertexColor(e.getX(), e.getY(), e.getZ(), time, maxTime);
				float y = 0;

				// x coordinate
				this.setBiggestY(biggestX.getX());
				y = this.calcValue_yAxes(e.getX());
				buffers[0].put(new float[] { xMin, y });

				// y coordinate
				this.setBiggestY(biggestY.getY());
				y = this.calcValue_yAxes(e.getY());
				buffers[0].put(new float[] { 0, y, 0, y });

				// z coordinate
				this.setBiggestY(biggestZ.getZ());
				y = this.calcValue_yAxes(e.getZ());
				buffers[0].put(new float[] { xMax, y });

				buffers[1].put(color);
				buffers[1].put(color);
				buffers[1].put(color);
				buffers[1].put(color);

			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			if (this.enabled) {
				glLineWidth(1);
				masterRenderMethod(viewportIndex, 2, 4, GL_LINES, callback);
				this.setAxesStrength();
				this.setAxesColor();
				glBegin(GL_LINES);
				for (int i = -1; i < 2; i++) {
					glVertex2f(i * xMax, yMax);
					glVertex2f(i * xMax, yMin);
				}
				glEnd();

				NewFontManager.prepare();
				NewFontManager.renderText(-460, 460, 16, 2, "x");
				NewFontManager.renderText(0, 460, 16, 2, "y");
				NewFontManager.renderText(460, 460, 16, 2, "z");
				NewFontManager.close();
				RenderUtil.switch2D(-1, -1, 1, 1);
			}
		}

	}

}
