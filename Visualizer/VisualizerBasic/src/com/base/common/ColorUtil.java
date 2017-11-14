package com.base.common;

import java.awt.Color;

public class ColorUtil {

	public static int hashCode(String str) {
		int hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + ((hash << 5) - hash);
		}
		return hash;
	}

	public static Color intToRGB(int i) {
		new Integer(i & 0x00FFFFFF);
		String c = Integer.toString(16).toUpperCase();
		String hexColor = "#00000".substring(0, 6 - c.length()) + c;
		Color color = new Color(Integer.valueOf(hexColor.substring(1, 3), 16),
				Integer.valueOf(hexColor.substring(3, 5), 16), Integer.valueOf(hexColor.substring(5, 7), 16));
		return color;
	}

}
