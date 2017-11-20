package com.base.engine.rendering.buffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.base.common.ColorUtil;
import com.base.common.resources.Callback;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.Point;
import com.base.engine.rendering.GridRenderer;
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

		VBOPointCloudRenderer pcr = new VBOPointCloudRenderer();
		VBOPointCloudClusterRenderer pccr = new VBOPointCloudClusterRenderer();
		VBOGridRenderer gr = new VBOGridRenderer();

		List<IVBORenderer> pointCloud = new ArrayList<>();
		pointCloud.add(pcr);

		List<IVBORenderer> pointCloudCluster = new ArrayList<>();
		pointCloudCluster.add(pccr);

		List<IVBORenderer> grid = new ArrayList<>();
		grid.add(gr);

		List<IVBORenderer> minimap = new ArrayList<>();
		minimap.add(gr);
		minimap.add(pcr);

		internalRenderers.put(PointCloudRenderer.class.getSimpleName(), pointCloud);
		internalRenderers.put(PointCloudClusterRenderer.class.getSimpleName(), pointCloudCluster);
		internalRenderers.put(GridRenderer.class.getSimpleName(), grid);
		internalRenderers.put(MiniMapRenderer.class.getSimpleName(), minimap);

		Callback largePointSizeCallback = new Callback() {
			public Object execute(Object... data) {
				GL11.glPointSize(5f);
				return 0;
			}
		};
		Callback smallPointSizeCallback = new Callback() {
			public Object execute(Object... data) {
				GL11.glPointSize(2f);
				return 0;
			}
		};

		List<Callback> cPointCloud = new ArrayList<>();
		cPointCloud.add(largePointSizeCallback);

		List<Callback> cPointCloudCluster = new ArrayList<>();
		cPointCloudCluster.add(largePointSizeCallback);

		List<Callback> cGrid = new ArrayList<>();

		List<Callback> cMinimap = new ArrayList<>();
		cMinimap.add(null);
		cMinimap.add(smallPointSizeCallback);

		internalCallbacks.put(PointCloudRenderer.class.getSimpleName(), cPointCloud);
		internalCallbacks.put(PointCloudClusterRenderer.class.getSimpleName(), cPointCloudCluster);
		internalCallbacks.put(GridRenderer.class.getSimpleName(), cGrid);
		internalCallbacks.put(MiniMapRenderer.class.getSimpleName(), cMinimap);
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
	public static void createBuffer(String rendererClassName, int viewportIndex, Object data) {
		if (internalRenderers.containsKey(rendererClassName)) {
			for (IVBORenderer renderer : internalRenderers.get(rendererClassName)) {
				renderer.create(viewportIndex + renderer.hashCode(), data);
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
		vertex_vbos.put(viewportIndex, GL15.glGenBuffers());
		color_vbos.put(viewportIndex, GL15.glGenBuffers());
		elements.put(viewportIndex, numElements);
		revalidate.put(viewportIndex, false);
		FloatBuffer v = BufferUtils.createFloatBuffer(elements.get(viewportIndex) * 3);
		FloatBuffer c = BufferUtils.createFloatBuffer(elements.get(viewportIndex) * 4);
		return new FloatBuffer[] { v, c };
	}

	private static void finalizeBuffers(int viewportIndex, FloatBuffer vBuffer, FloatBuffer cBuffer) {
		vBuffer.flip();
		cBuffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_vbos.get(viewportIndex));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vBuffer, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_vbos.get(viewportIndex));
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, cBuffer, GL15.GL_DYNAMIC_DRAW);
	}

	private static void masterRenderMethod(int viewportIndex, int vertices, int colors, int renderMode,
			Callback renderSettingsFunction) {

		if (!bufferExists(viewportIndex))
			return;

		if (renderSettingsFunction != null) {
			renderSettingsFunction.execute();
		}

		// bind vertices
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_vbos.get(viewportIndex));
		GL11.glVertexPointer(vertices, GL11.GL_FLOAT, 0, 0);

		// bind colors
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_vbos.get(viewportIndex));
		GL11.glColorPointer(colors, GL11.GL_FLOAT, 0, 0);

		// draw buffers
		GL11.glDrawArrays(renderMode, 0, elements.get(viewportIndex));

		// close buffers
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);

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
			masterRenderMethod(viewportIndex, 3, 4, GL11.GL_POINTS, callback);
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
			Callback settings = new Callback() {
				@Override
				public Object execute(Object... data) {
					GL11.glPointSize(5f);
					return 0;
				}
			};
			masterRenderMethod(viewportIndex, 3, 4, GL11.GL_POINTS, settings);
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
			masterRenderMethod(viewportIndex, 3, 4, GL11.GL_LINES, callback);
		}

	}

}
