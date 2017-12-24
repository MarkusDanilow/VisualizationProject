package com.base.engine.font.OLD_STUFF;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;

public final class FontManager {

	static class MADFontPackage {
		private static int cnt = 0;
		private int ID;
		private String name;
		private CustomFont font;
		private CustomUnicodeFont uFont;

		public MADFontPackage(CustomFont font) {
			setID();
			setFont(font);
			setuFont(CustomUnicodeFont.getUnicodeFontByMADFont(font));
		}

		public int getID() {
			return ID;
		}

		private void setID() {
			ID = cnt++;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			if (!(name == null) && !(name.equals("")))
				this.name = name;
		}

		public CustomFont getFont() {
			return font;
		}

		public void setFont(CustomFont font) {
			if (!(font == null)) {
				this.font = font;
				setName(font.getName());
			}
		}

		public CustomUnicodeFont getuFont() {
			return uFont;
		}

		public void setuFont(CustomUnicodeFont uFont) {
			if (!(uFont == null))
				this.uFont = uFont;
		}
	}

	public static final String DEFAULT = "Default",
							DEBUGGING_TEXT = "DebuggingText",
							DEBUGGING_ERROR = "DebuggingError",
							DEBUGGING_TEXT_SPECIAL = "DebuggingTextSpecial",
							MENU_TEXT = "MenuText";

	private static final CustomFont DEFAULT_FONT = new CustomFont("Lithos Pro",
			Font.BOLD, 18, Color.white, DEFAULT);
	private static final CustomFont DEBUGGING_TEXT_FONT = new CustomFont(
			"Lithos Pro", Font.BOLD, 13, Color.white, DEBUGGING_TEXT);
	private static final CustomFont DEBUGGING_ERROR_FONT = new CustomFont(
			"Lithos Pro", Font.BOLD, 13, Color.red, DEBUGGING_ERROR);
	private static final CustomFont DEBUGGING_SCREEN_SPECIAL_FONT = new CustomFont("Lithos Pro", 
			Font.BOLD, 13, Color.yellow, DEBUGGING_TEXT_SPECIAL);
	private static final CustomFont MENU_TEXT_FONT = new CustomFont("Lithos Pro",
			Font.BOLD, 16, Color.white, MENU_TEXT);

	private static ArrayList<MADFontPackage> fonts = new ArrayList<MADFontPackage>();

	public static void init() {
		addFont(DEFAULT_FONT);
		addFont(DEBUGGING_TEXT_FONT);
		addFont(DEBUGGING_ERROR_FONT);
		addFont(MENU_TEXT_FONT);
		addFont(DEBUGGING_SCREEN_SPECIAL_FONT);
	}

	public static void addFont(CustomFont font) {
		if (!(font == null)) {
			fonts.add(new MADFontPackage(font));
		}
	}

	public static CustomFont getFontByName(String name) {
		if (!(name == null) && !name.equals("")) {
			for (MADFontPackage f : fonts) {
				if (f.getName().equals(name))
					return f.getFont();
			}
		}
		return DEFAULT_FONT;
	}

	public static CustomFont getFontByID(int ID) {
		if (ID >= 0) {
			for (MADFontPackage f : fonts) {
				if (f.getID() == ID)
					return f.getFont();
			}
		}
		return DEFAULT_FONT;
	}

	public static CustomUnicodeFont getUFontByName(String name) {
		if (!(name == null) && !name.equals("")) {
			for (MADFontPackage f : fonts) {
				if (f.getName().equals(name))
					return f.getuFont();
			}
		}
		return toUnicodeFont(DEFAULT_FONT);
	}

	public static CustomUnicodeFont getUFontByID(int ID) {
		if (ID >= 0) {
			for (MADFontPackage f : fonts) {
				if (f.getID() == ID)
					return f.getuFont();
			}
		}
		return toUnicodeFont(DEFAULT_FONT);
	}

	public static CustomUnicodeFont toUnicodeFont(CustomFont font) {
		if (!(font == null))
			return CustomUnicodeFont.getUnicodeFontByMADFont(font);
		return CustomUnicodeFont.getUnicodeFontByMADFont(DEFAULT_FONT);
	}

}
