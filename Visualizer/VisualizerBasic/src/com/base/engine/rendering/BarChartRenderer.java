package com.base.engine.rendering;

import com.base.common.resources.StatisticObject;
import com.base.engine.Engine;
import com.base.engine.rendering.hover.BarChartHoverDataRenderer;

public class BarChartRenderer extends ARenderer {

	public BarChartRenderer() {
		this.hoverDataRenderer = new BarChartHoverDataRenderer();
	}

	@Override
	public void render(Object... objects) {
		StatisticObject stats = (StatisticObject) objects[0];
		System.out.println("rendering stats in bar chart, " + stats);
	}

	@Override
	public boolean is3D() {
		return false;
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
	public int[] createCustomViewport() {
		return null;
	}

	@Override
	public Object selectRenderData(Engine engine) {
		if (engine == null)
			return null;
		return engine.getChartData();
	}

}
