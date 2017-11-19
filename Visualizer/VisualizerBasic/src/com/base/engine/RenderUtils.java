package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.awt.Color;

import org.lwjgl.opengl.Display;

public final class RenderUtils {

	private static float NEAR_CLIPPING;
	private static float FAR_CLIPPING;
	private static Color CLEAR_COLOR;

	public static float getNearClipping() {
		return NEAR_CLIPPING;
	}

	public static void setNearClipping(float nearClipping) {
		NEAR_CLIPPING = nearClipping;
	}

	public static float getFarClipping() {
		return FAR_CLIPPING;
	}

	public static void setFarClipping(float farClipping) {
		FAR_CLIPPING = farClipping;
	}

	public static Color getClearColor() {
		return CLEAR_COLOR;
	}

	public static void setClearColor(Color clearColor) {
		CLEAR_COLOR = clearColor;
	}

	public static void create() {
		setClearColor(Settings.getClearColor());
		setNearClipping(Settings.getNearClipping());
		setFarClipping(Settings.getFarClipping());
	}

	public static void initGL() {

		glClearColor(CLEAR_COLOR.getRed() / 255f, CLEAR_COLOR.getGreen() / 255f, CLEAR_COLOR.getBlue() / 255f,
				CLEAR_COLOR.getAlpha());

		glClearDepth(1.0f);

		// switch3D();
		glEnable(GL_TEXTURE_2D);

		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_LINE_SMOOTH);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_POLYGON_SMOOTH);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.001f);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);

	}

	public static void exitGL() {
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHT1);
	}

	public static void switch2D(float left, float top, float width, float height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(left, width, height, top);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
	}

	public static void switch3D(int left, int top, int width, int height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glViewport(left, top, width, height);
		gluPerspective(45.0f, (float) Display.getWidth() / (float) Display.getHeight(), NEAR_CLIPPING, FAR_CLIPPING);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glCullFace(GL_FRONT);
		glEnable(GL_CULL_FACE);

	}

}
