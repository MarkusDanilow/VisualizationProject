package com.base.engine.font;

import static org.lwjgl.opengl.GL11.*;

public final class FontRenderer {

	public static void renderText(float left, float top, String text,
			CustomUnicodeFont font) {
		glPushMatrix();
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_LIGHT1);
		font.drawString(left, top, text, font.getColor());
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

}
