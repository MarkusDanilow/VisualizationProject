package com.base.engine.interaction;

import com.base.common.resources.DataElement;
import com.base.engine.interaction.data.LineChartHoverBufferData;
import com.base.engine.interaction.data.Rectangle;

public class LineChartHoverHandler extends AHoverHandler {

	@Override
	protected DataElement handleHover(float x, float y, Object data) {
		LineChartHoverBufferData buffer = (LineChartHoverBufferData) data;
		if (buffer != null) {
			for (int i = 0; i < buffer.getExtendedDots().size(); i++) {
				Rectangle bar = buffer.getExtendedDots().get(i);
				if (bar.isInside(x, y)) {
					return buffer.getRawData().get(i);
				}
			}
		}
		return null;
	}

}
