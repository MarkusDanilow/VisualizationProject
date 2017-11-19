package com.base.engine.rendering.buffers;

import com.base.common.ColorUtil;
import com.base.common.IRenderer;
import com.base.engine.Engine;

public class GraphicBufferUitl {

	public static final String DISPLAY_LIST_REPFIX = "viewport_display_list_";
	public static final boolean[] useDisplayLists = new boolean[Engine.NUM_VIEWS];

	static {
		for (int i = 0; i < Engine.NUM_VIEWS; i++) {
			useDisplayLists[i] = false;
		}
	}

	public static int createRendererhasCode(int viewportIndex, int rendererIndex) {
		return ColorUtil.hashCode("" + viewportIndex + "_" + rendererIndex);
	}

	public static void handleGraphicsData(Object data, IRenderer renderer, int viewportIndex, int rendererIndex) {

		if (data == null || renderer == null || viewportIndex < 0)
			return;

		int rendererHash = createRendererhasCode(viewportIndex, rendererIndex);

		// render and buffer the data using display lists
		if (useDisplayLists[viewportIndex]) {
			String displayListName = DISPLAY_LIST_REPFIX + rendererHash;
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
			if (!VBOHandler.bufferExists(rendererHash)) {
				VBOHandler.createBuffer(renderer.getClass().getSimpleName(), rendererHash, data);
			}
			VBOHandler.renderBuffer(renderer.getClass().getSimpleName(), rendererHash);
		}

	}

	public static void resetDisplayList(int viewportIndex, int rendererHash) {
		if (useDisplayLists[viewportIndex]) {
			DisplayListHandler.resetDisplayList(DISPLAY_LIST_REPFIX + rendererHash);
		} else {
			VBOHandler.revalidate(rendererHash);
		}
	}

}
