package com.base.engine.rendering.buffers;

import com.base.common.resources.Callback;

public interface IVBORenderer {

	void create(int viewportIndex, Object data);

	void render(int viewportIndex, Callback callback);

}
