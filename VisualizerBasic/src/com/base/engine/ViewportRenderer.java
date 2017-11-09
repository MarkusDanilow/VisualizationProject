package com.base.engine;

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

import java.util.List;

import org.lwjgl.opengl.Display;

import com.base.common.DataElement;
import com.base.common.IRenderer;
import com.base.common.Renderable;
import com.base.engine.font.FontManager;
import com.base.engine.font.FontRenderer;

public class ViewportRenderer implements Renderable {

	public static final String DISPLAY_LIST_REPFIX = "viewport_display_list_";

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

	public void render(Engine engine, Camera camera, IRenderer[] renderers) {

		if (renderers == null)
			return;

		for (int i = 0; i < renderers.length; i++) {

			int x = i < 2 ? 0 : Display.getWidth() / 2;
			int y = i % 2 != 0 ? 0 : Display.getHeight() / 2;
			int width = Display.getWidth() / 2;
			int height = Display.getHeight() / 2;

			RenderUtils.switch3D(x, y, width, height);

			glPushMatrix();
			glRotatef(camera.getPitch(), 1, 0, 0);
			glRotatef(camera.getYaw(), 0, 1, 0);
			glTranslatef(camera.getPos().getX(), camera.getPos().getY(), camera.getPos().getZ());
			glScalef(0.25f, 0.25f, 0.25f);

			// call the logic for the different rendering styles
			// Also call / initialize the corresponding display list

			String displayListName = DISPLAY_LIST_REPFIX + String.valueOf(i);

			List<DataElement> data = engine.getRawRenderData();
			if (data != null && data.size() > 0) {
				if (!DisplayListHandler.isDisplayListInitialized(displayListName)) {
					DisplayListHandler.generateDisplayList(displayListName);
					DisplayListHandler.initializeList(displayListName);
					renderers[i].render(data);
					DisplayListHandler.endList();
				} else {
					DisplayListHandler.callList(displayListName);
				}
			}

			// FontRenderer.renderText(0, 0, "Display: " + i, FontManager.getUFontByName(FontManager.DEFAULT));

			glPopMatrix();

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

	public void resetDisplayList(int displayListIndex) {
		DisplayListHandler.resetDisplayList(DISPLAY_LIST_REPFIX + displayListIndex);
	}
}
