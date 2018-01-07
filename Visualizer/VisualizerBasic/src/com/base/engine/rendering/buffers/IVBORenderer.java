package com.base.engine.rendering.buffers;

import com.base.common.resources.Callback;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.StatisticObject;

public interface IVBORenderer {

	void create(int viewportIndex, Object data, DataType[] type, StatisticObject stats);

	void render(int viewportIndex, Callback callback, DataType[] type);

}
