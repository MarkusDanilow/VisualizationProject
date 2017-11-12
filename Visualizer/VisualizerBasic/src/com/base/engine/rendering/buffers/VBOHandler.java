package com.base.engine.rendering.buffers;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.base.common.DataElement;
import com.base.engine.Engine;
import com.base.engine.rendering.PointCloudRenderer;

public class VBOHandler {

	private static int[] vertex_vbos = new int[Engine.NUM_VIEWS];
	private static int[] color_vbos = new int[Engine.NUM_VIEWS];

	private static int[] elements = new int[Engine.NUM_VIEWS];
	private static boolean[] revalidate = new boolean[Engine.NUM_VIEWS];

	static {
		for (int i = 0; i < Engine.NUM_VIEWS; i++) {
			vertex_vbos[i] = -1;
			color_vbos[i] = -1;
			elements[i] = 0;
			revalidate[i] = true;
		}
	}

	public static boolean bufferExists(int viewportIndex) {
		return vertex_vbos[viewportIndex] != -1 && !revalidate[viewportIndex];
	}

	public static void createBuffer(List<DataElement> data, int viewportIndex) {
		vertex_vbos[viewportIndex] = GL15.glGenBuffers();
		color_vbos[viewportIndex] = GL15.glGenBuffers();
		elements[viewportIndex] = data.size();
		revalidate[viewportIndex] = false;

		float maxTime = PointCloudRenderer.getMaxTimeFromData(data);

		FloatBuffer vertices = BufferUtils.createFloatBuffer(elements[viewportIndex] * 3);
		FloatBuffer colors = BufferUtils.createFloatBuffer(elements[viewportIndex] * 4);
		for (DataElement vertex : data) {
			vertices.put(new float[] { vertex.getX(), vertex.getY(), vertex.getZ() });
			float[] color = PointCloudRenderer.calcVertexColor(vertex.getX(), vertex.getY(), vertex.getZ(),
					vertex.getTime(), maxTime);
			colors.put(new float[] { color[0], color[1], color[2], color[3] });
		}
		vertices.flip();
		colors.flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_vbos[viewportIndex]);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_vbos[viewportIndex]);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colors, GL15.GL_DYNAMIC_DRAW);

	}

	public static void renderBuffer(int viewportIndex) {
		if (!bufferExists(viewportIndex))
			return;
		GL11.glPointSize(2.5f);

		// bind vertices
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_vbos[viewportIndex]);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

		// bind colors
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_vbos[viewportIndex]);
		GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);

		// draw buffers
		GL11.glDrawArrays(GL11.GL_POINTS, 0, elements[viewportIndex]);

		// close buffers
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	}

	public static void revalidate(int viewportIndex) {
		revalidate[viewportIndex] = true;
	}

}