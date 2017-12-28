package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.rendering.hover.PointCloudHoverDataRenderer;

public class PointCloudRenderer extends ARenderer {

	public PointCloudRenderer() {
		this.hoverDataRenderer = new PointCloudHoverDataRenderer();
	}

	@Override
	public void render(Object... objects) {
		@SuppressWarnings("unchecked")
		List<DataElement> data = (List<DataElement>) objects[0];

		float maxTime = getMaxTimeFromData(data);

		glPointSize(5);
		glBegin(GL_POINTS);
		for (DataElement dataElement : data) {
			float x = dataElement.getX();
			float y = dataElement.getY();
			float z = dataElement.getZ();
			float[] color = calcVertexColor(x, y, z, dataElement.getTime(), maxTime);
			GL11.glColor4f(color[0], color[1], color[2], color[3]);
			glVertex3f(x, y, z);
		}
		glEnd();
	}

	@Override
	public boolean is3D() {
		return true;
	}

	public static float getMaxTimeFromData(List<DataElement> data) {
		return (data == null || data.size() < 1) ? 0 : data.get(data.size() - 1).getTime();
	}

	public static float[] calcVertexColor(float x, float y, float z, float time, float maxTime) {
		// float timeStep = 1.0f / maxTime;
		// float c = time * timeStep;
		// return new float[] { c, 1.0f - c, 0, 1f };
		// return new float[] { 0, 1f - c, c, 1 };
		float value = (0.99f / maxTime) * time;
		return new float[] { 1, value, 1- value, 0.5f + value / 2f };
	}

	@Override
	public Object selectRenderData(Engine engine) {
		if (engine == null)
			return null;
		return engine.getPointCloudData();
	}

	@Override
	public boolean isAffectedByCameraPos() {
		return true;
	}

	@Override
	public boolean isAffectedByCameraAngle() {
		return true;
	}

	@Override
	public int[] createCustomViewport() {
		return null;
	}
}
