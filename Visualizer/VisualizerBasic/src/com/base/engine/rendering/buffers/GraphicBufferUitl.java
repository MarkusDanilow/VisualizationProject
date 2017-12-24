package com.base.engine.rendering.buffers;

import com.base.common.ColorUtil;
import com.base.common.IRenderer;
import com.base.engine.Engine;

public class GraphicBufferUitl {

	public static final String DISPLAY_LIST_REPFIX = "viewport_display_list_";
	public static final boolean[] useDisplayLists = new boolean[Engine.NUM_VIEWS];

	private static long start, end;
	public static boolean performanceMeasureEnabled = false;

	static {
		for (int i = 0; i < Engine.NUM_VIEWS; i++) {
			useDisplayLists[i] = false;
		}
	}

	public static int createRendererHashCode(int viewportIndex, int rendererIndex) {
		return ColorUtil.hashCode("" + viewportIndex + "_" + rendererIndex);
	}

	public static void handleGraphicsData(Object data, IRenderer renderer, int viewportIndex, int rendererIndex) {

		if (renderer == null || viewportIndex < 0)
			return;

		int rendererHash = createRendererHashCode(viewportIndex, rendererIndex);

		// render and buffer the data using display lists
		if (useDisplayLists[viewportIndex]) {
			if (data == null)
				return;
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
			if (viewportIndex == 0)
				start = System.currentTimeMillis();
			VBOHandler.handleBufferCreation(renderer.getClass().getSimpleName(), rendererHash, data);
			VBOHandler.renderBuffer(renderer.getClass().getSimpleName(), rendererHash);
			if (viewportIndex == Engine.NUM_VIEWS - 1) {
				end = System.currentTimeMillis();
				if (performanceMeasureEnabled) {
					System.out.println("Time for rendering all data elements: " + (end - start) + "ms");
					performanceMeasureEnabled = false;
				}
			}
		}

	}

	public static void resetDisplayList(int viewportIndex, int rendererHash, IRenderer[] renderers) {

		if (useDisplayLists[viewportIndex]) {
			DisplayListHandler.resetDisplayList(DISPLAY_LIST_REPFIX + rendererHash);
		} else {
			for (int i = 0; i < renderers.length; i++) {
				if (renderers[i] == null)
					continue;
				int[] hashCodes = VBOHandler.getHashCodesForRenderMethod(renderers[i].getClass().getSimpleName());
				for (int j = 0; j < hashCodes.length; j++)
					VBOHandler.revalidate(rendererHash + hashCodes[j]);
			}
		}
	}

}
