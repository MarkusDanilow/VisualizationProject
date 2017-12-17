package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;

import com.base.common.IRenderer;
import com.base.engine.Engine;
import com.base.engine.RenderUtils;

public class BarChartRenderer implements IRenderer {

	@Override
	public void render(Object... objects) {

		int texture = 0;
		try {
			texture = TextureLoader
					.getTexture("png", new FileInputStream(new File("res/textures/hucVisLineBoxplotPlaceholder.png")))
					.getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture);

		RenderUtils.switch2D(-1, -1, 1, 1);
		glBegin(GL_QUADS);
		glColor4f(1, 1, 1, 1);
		glTexCoord2f(0, 0);
		glVertex2f(-1, -1);
		glTexCoord2f(1, 0);
		glVertex2f(1, -1);
		glTexCoord2f(1, 1);
		glVertex2f(1, 1);
		glTexCoord2f(0, 1);
		glVertex2f(-1, 1);
		glEnd();

		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_TEXTURE_2D);

	}

	@Override
	public boolean is3D() {
		return false;
	}

	@Override
	public boolean isAffectedByCameraPos() {
		return false;
	}

	@Override
	public boolean isAffectedByCameraAngle() {
		return false;
	}

	@Override
	public int[] createCustomViewport() {
		return null;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		if (engine == null)
			return null;
		return engine.getPointCloudData();
	}

}
