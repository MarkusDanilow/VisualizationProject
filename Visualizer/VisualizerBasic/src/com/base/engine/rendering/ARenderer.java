package com.base.engine.rendering;

import com.base.common.IRenderer;

public abstract class ARenderer implements IRenderer {

	protected boolean hoverActivated = false ; 
	
	@Override
	public void toggleHover(boolean state) {
		this.hoverActivated = state ; 
	}
	
	@Override
	public boolean isHoverActivated() {
		return this.hoverActivated ; 
	}
	
}
