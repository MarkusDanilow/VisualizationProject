package com.base.engine;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;

public final class RenderUtils {

	private static float NEAR_CLIPPING;
	private static float FAR_CLIPPING;

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

	public static void create() {
		setNearClipping(Settings.getNearClipping());
		setFarClipping(Settings.getFarClipping());
	}

	public static void setClearColor() {
		glClearColor(Settings.CLEAR_COLOR.getRed(), Settings.CLEAR_COLOR.getGreen(), Settings.CLEAR_COLOR.getBlue(),
				Settings.CLEAR_COLOR.getAlpha());
	}

	public static void initGL() {

		setClearColor();
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

	public static void set2DMode(float x, float width, float y, float height) {
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glPushMatrix(); // Store The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		glOrtho(x, width, y, height, -1, 1); // Set Up An Ortho Screen
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		glPushMatrix(); // Store The Modelview Matrix
		glLoadIdentity(); // Reset The Modelview Matrix
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

	public static void rotateTexture(float angle) {
		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();
		glTranslatef(0.5f, 0.5f, 0.0f);
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glTranslatef(-0.5f, -0.5f, 0.0f);
		glMatrixMode(GL_MODELVIEW);
	}

}
