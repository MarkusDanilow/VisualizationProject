package com.base.engine.rendering;

import org.lwjgl.opengl.Display;

import com.base.engine.Engine;

public class MiniMapRenderer extends ARenderer {

	@Override
	public void render(Object... objects) {

	}

	@Override
	public boolean is3D() {
		return true;
	}

	@Override
	public boolean isAffectedByCameraPos() {
		return false;
	}

	@Override
	public boolean isAffectedByCameraAngle() {
		return false;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		if (engine == null)
			return null;
		return engine.getPointCloudData();
	}

	@Override
	public int[] createCustomViewport() {
		return new int[] { 0, Display.getHeight() / 2, Display.getWidth() / 5, Display.getHeight() / 5 };
	}

}
