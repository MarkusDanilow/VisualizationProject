package com.base.engine.interaction;

import com.base.engine.interaction.data.BarChartHoverBufferData;
import com.base.engine.interaction.data.Rectangle;

public class BarChartHoverHandler extends AHoverHandler {

	@Override
	protected void handleHover(float x, float y, Object data) {
		BarChartHoverBufferData buffer = (BarChartHoverBufferData) data;
		if (buffer == null)
			return;
		for (int i = 0; i < buffer.getBars().size(); i++) {
			Rectangle bar = buffer.getBars().get(i);
			if (bar.isInside(x, y)) {
				System.out.println(x + ", " + y + ", " + buffer.getRawData().get(i).getX());
				break;
			}
		}
	}

}
