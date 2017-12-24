package com.base.common;

import com.base.engine.Engine;

public interface IRenderer {

	void render(Object... objects);

	boolean is3D();

	boolean isAffectedByCameraPos();

	boolean isAffectedByCameraAngle();

	int[] createCustomViewport();

	Object selectRenderData(Engine engine);
	
	void toggleHover(boolean state);
	boolean isHoverActivated();
	
}
