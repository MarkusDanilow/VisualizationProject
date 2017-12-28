package com.base.engine.rendering;

import com.base.common.IRenderer;
import com.base.engine.rendering.hover.AHoverDataRenderer;

public abstract class ARenderer implements IRenderer {

	protected boolean hoverActivated = false;
	protected AHoverDataRenderer hoverDataRenderer = null;

	@Override
	public void toggleHover(boolean state) {
		this.hoverActivated = state;
	}

	@Override
	public boolean isHoverActivated() {
		return this.hoverActivated;
	}

	public boolean hasHoverDataRenderer() {
		return this.hoverDataRenderer != null;
	}

	public AHoverDataRenderer getHoverDataRenderer() {
		return hoverDataRenderer;
	}

	public void setHoverDataRenderer(AHoverDataRenderer hoverDataRenderer) {
		this.hoverDataRenderer = hoverDataRenderer;
	}

}
