package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;

import com.base.engine.Engine;
import com.base.engine.Settings;

public class GridRenderer extends ARenderer {

	static final int GRID_SIZE = 10;

	public void render() {

	}

	@Override
	public void render(Object... objects) {

		int minX = 0;
		int minY = 0;
		int maxX = 65532;
		int maxY = 65532;

		glLineWidth(5);
		glBegin(GL_LINES);
		// x = red
		glColor4f(Settings.X_COLOR.getRed(), Settings.X_COLOR.getGreen(), Settings.X_COLOR.getBlue(),
				Settings.X_COLOR.getAlpha());
		glVertex3f(0, 0, 0);
		glVertex3f(100, 0, 0);
		// y = green
		glColor4f(Settings.Y_COLOR.getRed(), Settings.Y_COLOR.getGreen(), Settings.Y_COLOR.getBlue(),
				Settings.Y_COLOR.getAlpha());
		glVertex3f(0, 0, 0);
		glVertex3f(0, 100, 0);
		// z = blue
		glColor4f(Settings.Z_COLOR.getRed(), Settings.Z_COLOR.getGreen(), Settings.Z_COLOR.getBlue(),
				Settings.Z_COLOR.getAlpha());
		glVertex3f(0, 0, 0);
		glVertex3f(0, 0, -100);
		glEnd();
		glLineWidth(1);

		glBegin(GL_LINES);

		glColor4f(Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getGreen(),
				Settings.FONT_COLOR.getAlpha());

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

	@Override
	public boolean is3D() {
		return true;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		return null;
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
