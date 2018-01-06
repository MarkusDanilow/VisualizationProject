package com.base.engine.rendering.buffers;

import com.base.common.resources.Callback;
import com.base.common.resources.DataElement.DataType;

public interface IVBORenderer {

	void create(int viewportIndex, Object data, DataType type);

	void render(int viewportIndex, Callback callback);

}
