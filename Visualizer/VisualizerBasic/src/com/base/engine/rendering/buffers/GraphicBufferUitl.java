package com.base.engine.rendering.buffers;

import java.util.List;

import com.base.common.IRenderer;
import com.base.common.resources.DataElement;
import com.base.engine.Engine;

public class GraphicBufferUitl {

	public static final String DISPLAY_LIST_REPFIX = "viewport_display_list_";
	public static final boolean[] useDisplayLists = new boolean[Engine.NUM_VIEWS];

	static {
		for (int i = 0; i < Engine.NUM_VIEWS; i++) {
			useDisplayLists[i] = false;
		}
	}

	public static void handleGraphicsData(List<DataElement> data, IRenderer renderer, int viewportIndex) {

		if (data == null || renderer == null || viewportIndex < 0)
			return;

		// render and buffer the data using display lists
		if (useDisplayLists[viewportIndex]) {
			String displayListName = DISPLAY_LIST_REPFIX + String.valueOf(viewportIndex);
			if (!DisplayListHandler.isDisplayListInitialized(displayListName)) {
				DisplayListHandler.generateDisplayList(displayListName);
				DisplayListHandler.initializeList(displayListName);
				renderer.render(data);
				DisplayListHandler.endList();
			} else {
				DisplayListHandler.callList(displayListName);
			}
		}

		// instead of display lists: use VBOs
		else {
			if (!VBOHandler.bufferExists(viewportIndex)) {
				VBOHandler.createBuffer(data, viewportIndex);
			}
			VBOHandler.renderBuffer(viewportIndex);
		}

	}

	public static void resetDisplayList(int viewportIndex) {
		if (useDisplayLists[viewportIndex]) {
			DisplayListHandler.resetDisplayList(DISPLAY_LIST_REPFIX + viewportIndex);
		} else {
			VBOHandler.revalidate(viewportIndex);
		}
	}

}