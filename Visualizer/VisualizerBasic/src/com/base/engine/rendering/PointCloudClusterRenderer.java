package com.base.engine.rendering;

import com.base.engine.Engine;

public class PointCloudClusterRenderer extends ARenderer {

	@Override
	public void render(Object... objects) {

	}

	@Override
	public boolean is3D() {
		return true;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		if (engine == null)
			return null;
		return engine.getPointCloudClusters();
	}

	@Override
	public boolean isAffectedByCameraPos() {
		return true;
	}

	@Override
	public boolean isAffectedByCameraAngle() {
		return true;
	}

	@Override
	public int[] createCustomViewport() {
		return null;
	}

}
