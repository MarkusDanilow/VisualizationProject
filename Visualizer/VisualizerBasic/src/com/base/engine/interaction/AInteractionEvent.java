package com.base.engine.interaction;

import com.base.common.resources.Callback;
import com.base.common.resources.MathUtil;

public abstract class AInteractionEvent implements Callback {

	protected InteractionEventResult calcViewportCoords(Object... data) {
		float hWidth = (float) data[4];
		float hHeight = (float) data[5];
		float currentX = (float) data[6];
		float currentY = (float) data[7];
		if (currentX > hWidth)
			currentX -= hWidth;
		if (currentY > hHeight)
			currentY -= hHeight;
		return new InteractionEventResult((int) data[8], MathUtil.map(currentX / (float) hWidth, 0, 1, -1, 1, -1),
				MathUtil.map(currentY / (float) hHeight, 0, 1, -1, 1, -1));
	}

}
