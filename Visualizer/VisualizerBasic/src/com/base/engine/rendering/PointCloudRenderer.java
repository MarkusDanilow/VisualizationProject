package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;

public class PointCloudRenderer implements IRenderer {

	private GridRenderer gridRenderer;

	public PointCloudRenderer() {
		this.gridRenderer = new GridRenderer();
	}

	@Override
	public void render(Object... objects) {
		@SuppressWarnings("unchecked")
		List<DataElement> data = (List<DataElement>) objects[0];
		int x1 = (int) data.get(0).getX(), x2 = (int) data.get(0).getX(), z1 = (int) data.get(0).getZ(),
				z2 = (int) data.get(0).getZ();
		
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
			if (x < x1)
				x1 = (int) x;
			if (x > x2)
				x2 = (int) x;
			if (z < z1)
				z1 = (int) z;
			if (z > z2)
				z2 = (int) z;
		}
		glEnd();
		this.gridRenderer.render(x1, z1, x2, z2);
	}

	@Override
	public boolean is3D() {
		return true;
	}

	public static float getMaxTimeFromData(List<DataElement> data) {
		return (data == null || data.size() < 1) ? 0 : data.get(data.size() - 1).getTime();
	}

	public static float[] calcVertexColor(float x, float y, float z, float time, float maxTime) {
		float timeStep = 1.0f / maxTime;
		float c = time * timeStep;
		return new float[] { c, 1.0f - c, 0, 1f };
	}
}
