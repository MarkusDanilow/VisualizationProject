package com.base.engine.interaction;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import com.base.common.resources.DataElement;
import com.base.common.resources.MathUtil;
import com.base.engine.interaction.data.PointCloudHoverBufferData;
import com.base.engine.interaction.data.Rectangle;

public class PointCloudHoverHandler extends AHoverHandler {

	@Override
	protected DataElement handleHover(float x, float y, Object data) {
		float hWidth = Display.getWidth() / 2f;
		float hHeight = Display.getHeight() / 2f;
		float delta = 0.025f;
		PointCloudHoverBufferData buffer = (PointCloudHoverBufferData) data;
		if (buffer != null) {
			for (DataElement point : buffer.getRawData()) {
				int[] coords = getScreenCoords(point.getX(), point.getY(), point.getZ());
				int sx = coords[0];
				int sy = coords[1];
				sx -= (sx > hWidth ? hWidth : 0);
				sy -= (sy > hHeight ? hHeight : 0);
				if (sx < 0 || sx > hWidth || sy < 0 || sy > hHeight)
					continue;
				float xx = MathUtil.map(sx, 0, hWidth, -1, 1, -1);
				float yy = MathUtil.map(sy, 0, hHeight, -1, 1, -1);
				Rectangle r = new Rectangle(xx - delta, xx + delta, yy - delta, yy + delta);
				if (r.isInside(x, y)) {
					return point;
				}
			}
		}
		return null;
	}

	/*
	 * Source: -------------
	 * https://stackoverflow.com/questions/26754725/gluproject-converting-3d-
	 * coordinates-to-2d-coordinates-does-not-convert-the-2d-y
	 * 
	 */
	public static int[] getScreenCoords(double x, double y, double z) {
		FloatBuffer screenCoords = BufferUtils.createFloatBuffer(4);
		IntBuffer viewportInt = GraphicsHoverHandler.getViewportMatrixAtCurrentMouseIndex();
		FloatBuffer modelView = GraphicsHoverHandler.getModelViewMatrixAtCurrentMouseIndex();
		FloatBuffer projection = GraphicsHoverHandler.getProjectionMatrixAtCurrentMouseIndex();
		boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewportInt,
				screenCoords);
		if (result) {
			return new int[] { (int) screenCoords.get(0), (int) screenCoords.get(1) };
		}
		return null;
	}

}
