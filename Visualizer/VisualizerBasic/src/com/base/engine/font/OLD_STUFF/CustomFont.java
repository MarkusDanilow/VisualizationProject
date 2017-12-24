package com.base.engine.font.OLD_STUFF;

import java.awt.Font;

import org.newdawn.slick.Color;

public class CustomFont extends Font {

	private static final long serialVersionUID = 7060535065563066766L;

	private String name;
	private Color color;

	public CustomFont(String fontName, int fontAttr, int fontSize, Color color,
			String name) {
		super(fontName, fontAttr, fontSize);
		setName(name);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	private void setColor(Color color) {
		this.color = color;
	}

}
