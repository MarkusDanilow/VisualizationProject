package com.base.common;

import java.awt.Color;

public class ColorUtil {

	public static int hashCode(String str) {
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + ((hash << 5) - hash);
		}
		return Math.abs(hash);
	}

	public static Color intToRGB(int intColor) {
		String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
		Color color = new Color(Integer.valueOf(hexColor.substring(1, 3), 16),
				Integer.valueOf(hexColor.substring(3, 5), 16), Integer.valueOf(hexColor.substring(5, 7), 16));
		return color;
	}

	public static Color colorFromString(String str) {
		return intToRGB(hashCode(str));
	}

	public static float[] convertToRenderableColor4f(Color c) {
		return new float[] { c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 1.0f };
	}

}
