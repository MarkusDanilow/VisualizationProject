package com.base.engine.rendering.buffers;

import java.nio.FloatBuffer;
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

	private static Map<String, IVBORenderer> internalRenderers = new HashMap<>();

	/**
	 * 
	 */
	static {
		internalRenderers.put(PointCloudRenderer.class.getSimpleName(), new VBOPointCloudRenderer());
		internalRenderers.put(PointCloudClusterRenderer.class.getSimpleName(), new VBOPointCloudClusterRenderer());
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
			internalRenderers.get(rendererClassName).create(viewportIndex, data);
		}
	}

	/**
	 * 
	 * @param viewportIndex
	 */
	public static void renderBuffer(String rendererClassName, int viewportIndex) {
		if (internalRenderers.containsKey(rendererClassName)) {
			internalRenderers.get(rendererClassName).render(viewportIndex);
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
		public void render(int viewportIndex) {
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
	 * POINT CLOUD CLUSTER RENDERING
	 * ------------------------------------------------------------------------
	 */

	private static class VBOPointCloudClusterRenderer implements IVBORenderer {

		@Override
		public void create(int viewportIndex, Object data) {
			List<Cluster> clusters = (List<Cluster>) data;
			FloatBuffer[] buffers = initBuffers(viewportIndex, clusters.size(), 3, 4);
			for (int i = 0; i < clusters.size(); i++) {
				Point centroid = clusters.get(i).getCentroid();
				buffers[0].put(new float[] { centroid.x, centroid.y, centroid.z });
				buffers[1].put(ColorUtil.convertToRenderableColor4f(ColorUtil.intToRGB(i * 1000)));
			}
			finalizeBuffers(viewportIndex, buffers[0], buffers[1]);
		}

		@Override
		public void render(int viewportIndex) {
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

}
