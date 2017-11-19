package com.base.common;

import com.base.engine.Engine;

public interface IRenderer {

	void render(Object... objects);
	boolean is3D();
	Object selectRenderData(Engine engine);

}
