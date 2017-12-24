package com.base.engine.font.OLD_STUFF;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class CustomUnicodeFont extends UnicodeFont {

	private Color color;

	public CustomUnicodeFont(Font font, Color color) {
		super(font);
		setColor(color);
	}

	public Color getColor() {
		return color;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	@SuppressWarnings("unchecked")
	public static CustomUnicodeFont getUnicodeFontByMADFont(CustomFont font) {
		if (!(font == null)) {
			java.awt.Color color = new java.awt.Color(font.getColor().getRed(),
					font.getColor().getGreen(), font.getColor().getBlue());
			CustomUnicodeFont tmp = new CustomUnicodeFont(font, font.getColor());
			tmp.getEffects().add(new ColorEffect(color));
			tmp.addAsciiGlyphs();
			try {
				tmp.loadGlyphs();
			} catch (SlickException e) {
				e.printStackTrace();
			}
			return tmp;
		}
		return null;
	}

}
