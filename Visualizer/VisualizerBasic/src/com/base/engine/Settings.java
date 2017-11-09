package com.base.engine;

import java.awt.Color;
import java.io.File;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

public final class Settings {

	static final String SECTION_DISPLAY = "Display", SECTION_APP = "Application", SECTION_RENDERER = "Renderer";

	private static Ini settingsFile;

	static {
		try {
			settingsFile = new Ini(new File("res/settings.ini"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Section getSectionByName(String name) {
		return settingsFile.get(name);
	}

	private static Section getApplicationSection() {
		return getSectionByName(SECTION_APP);
	}

	private static Section getRendererSection() {
		return getSectionByName(SECTION_RENDERER);
	}

	private static Section getDisplaySection() {
		return getSectionByName(SECTION_DISPLAY);
	}

	public static String getApplicationTitle() {
		return getApplicationSection().get("title");
	}

	public static int getDisplayWidth() {
		return Integer.parseInt(getDisplaySection().get("width"));
	}

	public static int getDisplayHeight() {
		return Integer.parseInt(getDisplaySection().get("height"));
	}

	public static boolean getFullscreen() {
		return Boolean.valueOf(getDisplaySection().get("fullscreen"));
	}

	public static boolean getVSync() {
		return Boolean.valueOf(getDisplaySection().get("vsync"));
	}

	public static Color getClearColor() {
		String c = getRendererSection().get("clearColor");
		c = c.substring(1, c.length() - 1);
		String[] channels = c.split(",");
		return new Color(Integer.parseInt(channels[0]), Integer.parseInt(channels[1]), Integer.parseInt(channels[2]),
				Integer.parseInt(channels[3]));
	}

	public static float getNearClipping() {
		return Float.parseFloat(getRendererSection().get("nearClipping"));
	}

	public static float getFarClipping() {
		return Float.parseFloat(getRendererSection().get("farClipping"));
	}

	public static int getAnimationStep() {
		return Integer.parseInt(getRendererSection().get("animationStep"));
	}

}