package com.base.engine;

import java.io.File;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.base.common.MadColor;

public final class Settings {

	static final String SECTION_DISPLAY = "Display", SECTION_APP = "Application", SECTION_RENDERER = "Renderer",
			SECTION_VIEWS = "Views";

	private static Ini settingsFile;

	private static boolean colorInverted = false;

	public static MadColor FONT_COLOR, HOVER_BG_COLOR, HOVER_BORDER_COLOR, X_COLOR, Y_COLOR, Z_COLOR, MINIMAP_BG,
			MINIMAP_POS, CLEAR_COLOR, WND_COLOR;

	static {
		try {
			settingsFile = new Ini(new File("res/settings.ini"));
			initColors();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initColors() {
		FONT_COLOR = getFontColor();
		HOVER_BG_COLOR = getHoverBackground();
		HOVER_BORDER_COLOR = getHoverBorder();
		X_COLOR = getXColor();
		Y_COLOR = getYColor();
		Z_COLOR = getZColor();
		MINIMAP_BG = getMinimapBackground();
		MINIMAP_POS = getMinimapPosition();
		CLEAR_COLOR = getClearColor();
		WND_COLOR = getWindowColor();
	}

	public static boolean isColorInverted() {
		return colorInverted;
	}

	public static void toggleColorInverted() {
		Settings.colorInverted = !Settings.colorInverted;
		initColors();
	}

	private static String getInvertedColorName() {
		return colorInverted ? "_invert" : "";
	}

	public static MadColor getColorByName(String name) {
		String c = getRendererSection().get(name);
		c = c.substring(1, c.length() - 1);
		String[] channels = c.split(",");
		return new MadColor(Integer.parseInt(channels[0]), Integer.parseInt(channels[1]), Integer.parseInt(channels[2]),
				Integer.parseInt(channels[3]));
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

	private static Section getViewsSection() {
		return getSectionByName(SECTION_VIEWS);
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

	public static MadColor getWindowColor() {
		return getColorByName("wndColor");
	}

	public static MadColor getClearColor() {
		return getColorByName("clearColor" + getInvertedColorName());
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

	public static int getMaxItemsInChart() {
		return Integer.parseInt(getRendererSection().get("maxItemsInChart"));
	}

	public static MadColor getFontColor() {
		return getColorByName("fontColor" + getInvertedColorName());
	}

	public static MadColor getHoverBackground() {
		return getColorByName("hoverBackgroundColor" + getInvertedColorName());
	}

	public static MadColor getHoverBorder() {
		return getColorByName("hoverBorderColor" + getInvertedColorName());
	}

	public static MadColor getXColor() {
		return getColorByName("xColor");
	}

	public static MadColor getYColor() {
		return getColorByName("yColor");
	}

	public static MadColor getZColor() {
		return getColorByName("zColor");
	}

	public static MadColor getMinimapBackground() {
		return getColorByName("minimapBackground" + getInvertedColorName());
	}

	public static MadColor getMinimapPosition() {
		return getColorByName("minimapPosition");
	}

	public static String getViewByName(String name) {
		return getViewsSection().get(name);
	}

	public static String get3DView() {
		return getViewByName("3D");
	}

	public static String getBarChartView() {
		return getViewByName("BarChart");
	}

	public static String getLineChartView() {
		return getViewByName("LineChart");
	}

	public static String getParallelCoordinatesView() {
		return getViewByName("ParallelCoordinates");
	}
}