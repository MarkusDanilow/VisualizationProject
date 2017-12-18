package com.base.engine.rendering.buffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import com.base.common.ColorUtil;
import com.base.common.resources.Callback;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.Point;
import com.base.engine.RenderUtils;
import com.base.engine.rendering.BarChartRenderer;
import com.base.engine.rendering.GridRenderer;
import com.base.engine.rendering.LineChartRenderer;
import com.base.engine.rendering.MiniMapRenderer;
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

		/*
		 * associate renderer classes with rendering methods
		 */
		internalRenderers.put(PointCloudRenderer.class.getSimpleName(), pointCloud);
		internalRenderers.put(PointCloudClusterRenderer.class.getSimpleName(), pointCloudCluster);
		internalRenderers.put(GridRenderer.class.getSimpleName(), grid);
		internalRenderers.put(MiniMapRenderer.class.getSimpleName(), minimap);
		internalRenderers.put(BarChartRenderer.class.getSimpleName(), barChart);
		internalRenderers.put(LineChartRenderer.class.getSimpleName(), lineChart);

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
				RenderUtils.switch2D(-1, -1, 1, 1);
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

		/*
		 * associate renderer classes with callback methods
		 */
		internalCallbacks.put(PointCloudRenderer.class.getSimpleName(), cPointCloud);
		internalCallbacks.put(PointCloudClusterRenderer.class.getSimpleName(), cPointCloudCluster);
		internalCallbacks.put(GridRenderer.class.getSimpleName(), cGrid);
		internalCallbacks.put(MiniMapRenderer.class.getSimpleName(), cMinimap);
		internalCallbacks.put(BarChartRenderer.class.getSimpleName(), cBarChart);
		internalCallbacks.put(LineChartRenderer.class.getSimpleName(), cLineChart);

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
	public static void handleBufferCreation(String rendererClassName, int viewportIndex, Object data) {
		if (internalRenderers.containsKey(rendererClassName)) {
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				int bufferHashCode = viewportIndex + renderer.hashCode();
				if (!VBOHandler.bufferExists(bufferHashCode)) {
					renderer.create(bufferHashCode, data);
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
		public void create(int viewportIndex, Object data) {
			if (data == null)
				return;
			List<DataElement> points = (List<DataElement>) data;

			FloatBuffer[] buffers = initBuffers(viewportIndex, points.size(), 3, 4);

			float maxTime = PointCloudRenderer.getMaxTimeFromData(points);
			for (DataElement vertex : points) {
				buffers[0].put(new float[] { vertex.getX(), vertex.getY(), vertex.getZ() });
				float[] color = PointCloudRenderer.calcVertexColor(vertex.getX(), vertex.getY(), vertex.getZ(),
						vertex.getTime(), maxTime);
				buffers[1].put(new float[] { color[0], color[1], color[2], color[3] });
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
	 * POINT CLOUD CLUSTER RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOPointCloudClusterRenderer implements IVBORenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data) {
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

		@Override
		public void create(int viewportIndex, Object data) {
			int gridSizeX = 1000;
			int gridSizeY = 1000;
			int minX = 0;
			int minY = 0;
			int maxX = 65532;
			int maxY = 65532;

			float alphaChannel = 0.1f;

			int size = 6 + ((Math.abs(minX) + maxX) / gridSizeX) * ((Math.abs(minY) + maxY) / gridSizeY);

			FloatBuffer[] buffers = initBuffers(viewportIndex, size, 3, 4);

			// push axes to the buffers
			buffers[0].put(new float[] { 0, 0, 0, maxX, 0, 0, 0, 0, 0, 0, maxY, 0, 0, 0, 0, 0, 0, maxY });
			buffers[1].put(new float[] { 1, 0, 0, 0.5f, 1, 0, 0, 0.5f, 0, 1, 0, 0.5f, 0, 1, 0, 0.5f, 0, 0, 1, 0.5f, 0,
					0, 1, 0.5f });

			for (int x = minX; x < maxX; x += gridSizeX) {
				buffers[0].put(new float[] { x, 0, minY, x, 0, maxY });
				buffers[1].put(new float[] { 1, 1, 1, alphaChannel });
				buffers[1].put(new float[] { 1, 1, 1, alphaChannel });
			}
			for (int y = minY; y < maxY; y += gridSizeY) {
				buffers[0].put(new float[] { minX, 0, y, maxX, 0, y });
				buffers[1].put(new float[] { 1, 1, 1, alphaChannel });
				buffers[1].put(new float[] { 1, 1, 1, alphaChannel });
			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			masterRenderMethod(viewportIndex, 3, 4, GL_LINES, callback);
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

		protected void renderAxes() {
			glColor4f(0.5f, 0.5f, 0.5f, 1f);
			glBegin(GL_LINES);
			glVertex2f(xMin - 0.025f, yMax);
			glVertex2f(xMax + 0.05f, yMax);
			glVertex2f(xMin - 0.025f, yMax + 0.05f);
			glVertex2f(xMin - 0.025f, yMin - 0.05f);

			for (int i = 0; i < numItems; i++) {
				float x = this.calcPosX(i) + this.xStep / 2f;
				glVertex2f(x, yMax + 0.025f);
				glVertex2f(x, yMax);
			}

			glEnd();
		}

		protected float calcPosX(int i) {
			return (i * xStep) + xMin;
		}

		protected float calcXStep() {
			xStep = (((xMax - xMin)) / ((float) numItems));
			return xStep;
		}

		protected DataElement getBiggestX(List<DataElement> inputData) {
			return Collections.max(inputData, new Comparator<DataElement>() {
				@Override
				public int compare(DataElement arg0, DataElement arg1) {
					return Float.compare(arg0.getX(), arg1.getX());
				}
			});
		}

		protected DataElement getBiggestY(List<DataElement> inputData) {
			return Collections.max(inputData, new Comparator<DataElement>() {
				@Override
				public int compare(DataElement arg0, DataElement arg1) {
					return Float.compare(arg0.getY(), arg1.getY());
				}
			});
		}

		protected DataElement getBiggestZ(List<DataElement> inputData) {
			return Collections.max(inputData, new Comparator<DataElement>() {
				@Override
				public int compare(DataElement arg0, DataElement arg1) {
					return Float.compare(arg0.getZ(), arg1.getZ());
				}
			});
		}

		protected float calcValue_yAxes(float value, float biggest) {
			float valueRatio = value / biggest;
			return valueRatio * (yMin - yMax) + yMax;
		}

		protected float calcValue_xAxes(float value, float biggest) {
			float valueRatio = value / biggest;
			return valueRatio * (xMax - xMin) + xMin;
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
		public void create(int viewportIndex, Object data) {

			if (data == null)
				return;

			int verticesPerItem = 4;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement biggest = this.getBiggestX(inputData);
			this.numItems = inputData.size();

			this.calcXStep();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems * 8, 2, 4);
			for (int i = 0; i < numItems; i++) {

				float x = this.calcPosX(i);
				float value = this.calcValue_yAxes(inputData.get(i).getX(), biggest.getX());

				buffers[0].put(new float[] { x, value, x + xStep, value, x + xStep, yMax, x, yMax });
				for (int j = 0; j < verticesPerItem; j++)
					buffers[1].put(new float[] { 0.8f, 0.8f, 0.8f, 1f });

				buffers[0].put(new float[] { x + this.borderWidth, value + this.borderWidth,
						x + xStep - this.borderWidth, value + this.borderWidth, x + xStep - this.borderWidth,
						yMax - this.borderWidth, x + this.borderWidth, yMax - this.borderWidth });
				for (int j = 0; j < verticesPerItem; j++)
					buffers[1].put(new float[] { 1f, 0f, 0f, 1f });

			}
			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			glDisable(GL_BLEND);
			masterRenderMethod(viewportIndex, 2, 4, GL_QUADS, callback);
			this.renderAxes();
			glEnable(GL_BLEND);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * LINE CHART RENDERING
	 * ------------------------------------------------------------------------
	 */

	/**
	 * TODO: Whats the sense of the line chart again ??!
	 * @author Markus
	 *
	 */
	private static class VBOLineChartRenderer extends AVBOChartRenderer {

		@SuppressWarnings("unchecked")
		@Override
		public void create(int viewportIndex, Object data) {

			if (data == null)
				return;

			List<DataElement> inputData = (List<DataElement>) data;
			DataElement biggestX = this.getBiggestX(inputData);
			DataElement biggestY = this.getBiggestY(inputData);

			this.numItems = inputData.size();
			this.calcXStep();

			FloatBuffer[] buffers = initBuffers(viewportIndex, numItems, 2, 4);
			for (int i = 0; i < numItems; i++) {

				float valueX = this.calcPosX(i);
				float valueY = this.calcValue_yAxes(inputData.get(i).getX(), biggestX.getX());

				buffers[0].put(new float[] { valueX, valueY });
				buffers[1].put(new float[] { 1f, 0f, 0f, 1f });

			}

			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex, Callback callback) {
			glDisable(GL_BLEND);
			masterRenderMethod(viewportIndex, 2, 4, GL_LINE_STRIP, callback);
			this.renderAxes();
			glEnable(GL_BLEND);
		}

	}

}
