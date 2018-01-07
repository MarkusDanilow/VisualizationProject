package com.base.engine.rendering;

import com.base.common.resources.StatisticObject;
import com.base.engine.Engine;
import com.base.engine.rendering.hover.LineChartHoverDataRenderer;

public class LineChartRenderer extends ARenderer {

	public LineChartRenderer() {
		this.hoverDataRenderer = new LineChartHoverDataRenderer();
	}

	@Override
	public void render(Object... objects) {
		StatisticObject stats = (StatisticObject) objects[0];
		System.out.println("rendering stats in line chart, " + stats);
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
		return engine.getChartData();
	}

}
