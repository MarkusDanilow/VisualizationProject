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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import com.base.common.resources.Range;
import com.base.engine.RenderUtil;
import com.base.engine.Settings;

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

	private static class CharDefinitions {
		Range<Integer> ascii;
		int indexOffset;
		int rowOffset;

		public CharDefinitions(Range<Integer> ascii, int indexOffset, int rowOffset) {
			super();
			this.ascii = ascii;
			this.indexOffset = indexOffset;
			this.rowOffset = rowOffset;
		}
	}

	public static final int SCALE = 500;

	private static int fontTexture = 0;
	private static Map<Character, FontTexturePoint> fontMapping = new HashMap<>();

	private static final int perLine = 16;
	private static final float resolution = 1f / (float) perLine;

	private static List<CharDefinitions> charDefinitions;

	public static void init() {
		try {
			fontTexture = TextureLoader.getTexture("png", new FileInputStream(new File("res/textures/font2.png")))
					.getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}

		charDefinitions = new ArrayList<>();
		charDefinitions.add(new CharDefinitions(new Range<Integer>(33, 64), 4, 4));
		charDefinitions.add(new CharDefinitions(new Range<Integer>(65, 90), 0, 1));
		charDefinitions.add(new CharDefinitions(new Range<Integer>(97, 122), 10, 2));

		for (CharDefinitions c : charDefinitions) {
			for (int i = c.ascii.getLoVal(); i <= c.ascii.getHiVal(); i++) {
				int index = i - (c.ascii.getLoVal() - c.indexOffset);
				int x = 1 - index / perLine;
				int y = (index % perLine);
				fontMapping.put((char) i,
						new FontTexturePoint(x * resolution - c.rowOffset * resolution, y * resolution + resolution));
			}
		}

	}

	public static void prepare() {
		GL11.glPushMatrix();
		RenderUtil.switch2D(-SCALE, -SCALE, SCALE, SCALE);
	}

	public static void close() {
		GL11.glPopMatrix();
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

		glColor4f(Settings.FONT_COLOR.getRed(), Settings.FONT_COLOR.getBlue(), Settings.FONT_COLOR.getGreen(),
				Settings.FONT_COLOR.getAlpha());

		for (int i = 0; i < text.length(); i++) {
			char index = text.charAt(i);
			FontTexturePoint p = fontMapping.get(index);

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
