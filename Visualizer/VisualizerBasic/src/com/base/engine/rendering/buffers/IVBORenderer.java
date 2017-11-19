package com.base.engine.rendering.buffers;

public interface IVBORenderer {

	void create(int viewportIndex, Object data);

	void render(int viewportIndex);

}
