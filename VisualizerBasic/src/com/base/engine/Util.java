package com.base.engine;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Util {

	public static Point getCenterOfScreen() {
		return getCenterOfScreen(new Dimension(0, 0));
	}

	public static Point getCenterOfScreen(Dimension displaySize) {
		Dimension size = getScreenSize();
		int x = (int) ((size.getWidth() - displaySize.getWidth()) / 2);
		int y = (int) ((size.getHeight() - displaySize.getHeight()) / 2);
		return new Point(x, y);
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
