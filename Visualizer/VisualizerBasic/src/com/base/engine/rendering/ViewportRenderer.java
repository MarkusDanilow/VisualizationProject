package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.base.common.IRenderer;
import com.base.common.Renderable;
import com.base.engine.Camera;
import com.base.engine.Engine;
import com.base.engine.RenderUtils;
import com.base.engine.rendering.buffers.GraphicBufferUitl;

public class ViewportRenderer implements Renderable {

	@Override
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
	}

	@Override
	public void render(float[][] data) {
	}

	@Override
	public void close() {
	}

	public void render(Engine engine, Camera[] camera, IRenderer[][] renderers, float[] scaleFactors) {

		if (renderers == null || renderers.length < 1)
			return;

		int width = Display.getWidth() / 2;
		int height = Display.getHeight() / 2;

		for (int i = 0; i < renderers.length; i++) {

			int x = i < 2 ? 0 : width;
			int y = i % 2 != 0 ? 0 : height;

			if (renderers[i] != null) {

				for (int j = 0; j < renderers[i].length; j++) {

					if (renderers[i][j] != null) {

						int[] customViewport = renderers[i][j].createCustomViewport();
						boolean isNormalViewport = customViewport == null;

						if (isNormalViewport) {
							RenderUtils.switch3D(x, y, width, height);
						} else {
							RenderUtils.switch3D(customViewport[0], customViewport[1], customViewport[2],
									customViewport[3]);
							glPushMatrix();
							RenderUtils.switch2D(-1, -1, 1, 1);
							glBegin(GL11.GL_QUADS);
							GL11.glColor4f(0, 0, 0, 1);
							glVertex2f(-1, -1);
							glVertex2f(1, -1);
							glVertex2f(1, 1);
							glVertex2f(-1, 1);
							glEnd();
							glBegin(GL11.GL_LINE_STRIP);
							GL11.glColor4f(1, 1, 1, 0.5f);
							glVertex2f(-1, -1);
							glVertex2f(1, -1);
							glVertex2f(1, 1);
							glVertex2f(-1, 1);
							glEnd();
							glPopMatrix();
							RenderUtils.switch3D(customViewport[0], customViewport[1], customViewport[2],
									customViewport[3]);
						}

						glPushMatrix();

						try {
							// only translate and rotate in 3D view
							if (renderers[i][j].is3D() && renderers[i][j].isAffectedByCameraAngle()) {
								glRotatef(camera[i].getPitch(), 1, 0, 0);
								glRotatef(camera[i].getYaw(), 0, 1, 0);
							}
							if (renderers[i][j].isAffectedByCameraPos()) {
								glTranslatef(camera[i].getPos().getX(), camera[i].getPos().getY(),
										camera[i].getPos().getZ());
								glScalef(scaleFactors[i], scaleFactors[i], scaleFactors[i]);
							} else {
								glRotatef(35, 1, 0, 0);
								glRotatef(130, 0, 1, 0);
								glTranslatef(3000, -6000, 3000);
								glScalef(0.05f, 0.05f, 0.05f);
							}

							Object renderData = renderers[i][j].selectRenderData(engine);

							GraphicBufferUitl.handleGraphicsData(renderData, renderers[i][j], i, j);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							glPopMatrix();
						}

					}

				}

			}

		}

		RenderUtils.switch3D(0, 0, Display.getWidth(), Display.getHeight());
		this.renderSplitScreen();

	}

	private void renderSplitScreen() {
		int w = Display.getWidth();
		int h = Display.getHeight();
		RenderUtils.switch2D(0, 0, w, h);
		glPushMatrix();
		glLineWidth(2);
		glColor3f(1, 1, 1);
		glBegin(GL_LINES);
		glVertex2f(0, h / 2);
		glVertex2f(w, h / 2);
		glVertex2f(w / 2, 0);
		glVertex2f(w / 2, h);
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render() {
	}

	public void resetDisplayList(Engine engine, int displayListIndex) {
		for (int i = 0; i < engine.getNumRenderersForViewport(displayListIndex); i++) {
			int rendererHash = GraphicBufferUitl.createRendererhasCode(displayListIndex, i);
			GraphicBufferUitl.resetDisplayList(displayListIndex, rendererHash);
		}
	}
}
