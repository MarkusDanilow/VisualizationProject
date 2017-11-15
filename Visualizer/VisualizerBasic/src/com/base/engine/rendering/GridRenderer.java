package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class GridRenderer {

	static final int GRID_SIZE = 10;

	public void render(int minX, int minY, int maxX, int maxY) {

		glLineWidth(5);
		glBegin(GL_LINES);
		// x = red
		glColor4f(1, 0, 0, 0.5f);
		glVertex3f(0, 0, 0);
		glVertex3f(100, 0, 0);
		// y = green
		glColor4f(0, 1, 0, 0.5f);
		glVertex3f(0, 0, 0);
		glVertex3f(0, 100, 0);
		// z = blue
		glColor4f(0, 0, 1, 0.5f);
		glVertex3f(0, 0, 0);
		glVertex3f(0, 0, -100);
		glEnd();
		glLineWidth(1);

		glBegin(GL_LINES);
		glColor4f(1, 1, 1, 0.5f);
		for (int x = minX; x < maxX; x += GRID_SIZE) {
			glVertex3f(x, 0, minY);
			glVertex3f(x, 0, maxY);
		}
		for (int y = minY; y < maxY; y += GRID_SIZE) {
			glVertex3f(minX, 0, y);
			glVertex3f(maxX, 0, y);
		}
		glEnd();
	}
}
