package com.base.engine;

import org.lwjgl.Sys;

public final class FrameRateUtil {

	private static long lastFrame;
	private static int fps;
	private static int savedFPS ;
	private static long lastFPS;

	public static void start() {
		getDelta();
		lastFPS = getTime();
	}

	public static boolean update() {
		getDelta();
		return updateFPS();
	}

	private static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	private static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private static boolean updateFPS() {
		boolean res = false ; 
		if (getTime() - lastFPS > 1000) {
			savedFPS = fps ; 
			setFps(0);
			lastFPS += 1000;
			res = true ;
			System.out.println("FPS: " + savedFPS);
		}
		fps++ ; 
		return res ; 
	}

	public static long getLastFrame() {
		return lastFrame;
	}

	public static int getFps() {
		return savedFPS;
	}

	private static void setFps(int fps) {
		FrameRateUtil.fps = fps;
	}

}
