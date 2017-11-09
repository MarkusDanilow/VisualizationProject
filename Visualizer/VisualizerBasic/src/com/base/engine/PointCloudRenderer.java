package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.List;

import com.base.common.DataElement;
import com.base.common.IRenderer;

class PointCloudRenderer implements IRenderer {

	private GridRenderer gridRenderer;

	public PointCloudRenderer() {
		this.gridRenderer = new GridRenderer();
	}

	@Override
	public void render(Object... objects) {
		@SuppressWarnings("unchecked")
		List<DataElement> data = (List<DataElement>) objects[0];
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		float maxTime = data.get(data.size() - 1).getTime();
		float timeStep = 1.0f / maxTime;

		glPointSize(5);
		glBegin(GL_POINTS);
		for (DataElement dataElement : data) {
			float x = dataElement.getX();
			float y = dataElement.getY();
			float z = dataElement.getZ();
			float c = dataElement.getTime() * timeStep;
			glColor3f(c, 1.0f - c, 0);
			glVertex3f(x, y, z);
			if (x < x1)
				x1 = (int) x;
			if (x > x2)
				x2 = (int) x;
			if (y < y1)
				y1 = (int) y;
			if (y > y2)
				y2 = (int) y;
		}
		glEnd();
		this.gridRenderer.render(x1, y1, x2, y2);
	}
}
