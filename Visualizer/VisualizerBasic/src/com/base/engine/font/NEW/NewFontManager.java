package com.base.engine.font.NEW;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

public class NewFontManager {

	private static class FontTexturePoint {
		float x, y;

		public FontTexturePoint(float x, float y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "" + x + ", " + y;
		}
	}

	private static int fontTexture = 0;
	private static Map<Character, FontTexturePoint> fontMapping = new HashMap<>();

	private static final int perLine = 8;
	private static final float resolution = 1f / (float) perLine;

	public static void init() {
		try {
			fontTexture = TextureLoader.getTexture("png", new FileInputStream(new File("res/textures/font.png")))
					.getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// large letters
		for (int i = 65; i <= 90; i++) {
			int index = (i - 65);
			int x = 1 - index / perLine;
			int y = (index % perLine);
			fontMapping.put((char) i, new FontTexturePoint(x * resolution - resolution, y * resolution + resolution));
		}

		// small letters
		for (int i = 97; i <= 122; i++) {
			int index = (i - 95);
			int x = 1 - index / perLine;
			int y = (index % perLine);
			fontMapping.put((char) i,
					new FontTexturePoint(x * resolution - 4 * resolution, y * resolution + resolution));
		}

		// numbers
		for (int i = 48; i <= 57; i++) {
			int index = (i - 44);
			int x = 1 - index / perLine;
			int y = (index % perLine);
			fontMapping.put((char) i,
					new FontTexturePoint(x * resolution - 7 * resolution, y * resolution + resolution));
		}

		// :
		for (int i = 58; i <= 58; i++) {
			int index = (i - 52);
			int x = 1 - index / perLine;
			int y = (index % perLine);
			fontMapping.put((char) i,
					new FontTexturePoint(x * resolution - 8 * resolution, y * resolution + resolution));
		}

		// .
		for (int i = 46; i <= 46; i++) {
			int index = (i - 39);
			int x = 1 - index / perLine;
			int y = (index % perLine);
			fontMapping.put((char) i,
					new FontTexturePoint(x * resolution - 8 * resolution, y * resolution + resolution));
		}

	}

	public static void renderText(float x, float y, float fontSize, float scale, String text) {

		fontSize *= scale;
		float hf = (fontSize / 2f);
		float hff = (hf / 2f) * 1.5f;

		float xTemp = x;
		x = y;
		y = -xTemp;

		GL11.glPushMatrix();

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, fontTexture);

		GL11.glRotatef(90, 0, 0, 1);

		glColor4f(1, 1, 1, 1);

		for (int i = 0; i < text.length(); i++) {
			char index = text.charAt(i);
			FontTexturePoint p = fontMapping.get(index);
			System.out.println(index + " >> " + p);

			float posX = -i * hff;

			if (p != null) {

				glBegin(GL_QUADS);

				glTexCoord2f(p.x, p.y);
				glVertex2f(x - hf, posX + y - hff);

				glTexCoord2f(p.x - resolution, p.y);
				glVertex2f(x + hf, posX + y - hff);

				glTexCoord2f(p.x - resolution, p.y - resolution);
				glVertex2f(x + hf, posX + y + hff);

				glTexCoord2f(p.x, p.y - resolution);
				glVertex2f(x - hf, posX + y + hff);

				glEnd();
			}

		}

		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_TEXTURE_2D);

		GL11.glPopMatrix();

	}

}
