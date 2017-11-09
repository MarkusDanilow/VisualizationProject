package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.util.Map;
import java.util.TreeMap;

public class DisplayListHandler {

	private static Map<String, Integer> displayLists = new TreeMap<String, Integer>();
	private static Map<String, Boolean> displayListInitState = new TreeMap<String, Boolean>();

	public static int generateDisplayList(String identifier) {
		if (identifier != null && identifier.length() > 0) {
			int list = glGenLists(1);
			displayLists.put(identifier, list);
			resetDisplayList(identifier);
			return list;
		}
		return -1;
	}

	public static void initializeList(String identifier) {
		int list = getDisplayList(identifier);
		glNewList(list, GL_COMPILE);
		displayListInitState.put(identifier, true);
	}

	public static void endList() {
		glEndList();
	}

	public static void callList(String identifier) {
		glCallList(getDisplayList(identifier));
	}

	public static int getDisplayList(String identifier) {
		return displayLists.get(identifier);
	}

	public static boolean isDisplayListInitialized(String identifier) {
		return displayListInitState.containsKey(identifier) && displayListInitState.get(identifier);
	}

	public static void resetDisplayList(String identifier) {
		displayListInitState.put(identifier, false);
	}

}
