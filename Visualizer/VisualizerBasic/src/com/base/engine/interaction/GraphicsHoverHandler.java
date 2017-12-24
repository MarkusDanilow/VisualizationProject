package com.base.engine.interaction;

import java.util.ArrayList;
import java.util.List;

import com.base.engine.Engine;
import com.base.engine.interaction.data.AHoverBufferData;

public class GraphicsHoverHandler {

	private static AHoverBufferData[] bufferedVertexData = new AHoverBufferData[Engine.NUM_VIEWS];
	private static int currentBufferIndex = 0;
	private static int currentMouseBufferIndex = 0;

	private static List<AHoverHandler> handlers = new ArrayList<>();

	static {
		handlers.add(new PointCloudHoverHandler());
		handlers.add(new LineChartHoverHandler());
		handlers.add(new BarChartHoverHandler());
		handlers.add(new ParallelCoordinatesHoverHandler());
	}

	public static void setCurrentBufferIndex(int index) {
		if (index > 0 && index < bufferedVertexData.length)
			currentBufferIndex = index;
		else
			currentBufferIndex = 0;
	}

	public static void storeBufferVertexDataAtCurrentIndex(AHoverBufferData data) {
		bufferedVertexData[currentBufferIndex] = data;
	}

	public static AHoverBufferData getBufferVertexDataAtCurrentIndex() {
		return bufferedVertexData[currentBufferIndex];
	}

	public static AHoverBufferData getBufferVertexData(int index) {
		return bufferedVertexData[index];
	}

	public static AHoverBufferData getBufferVertexDataAtMouseBufferIndex() {
		return bufferedVertexData[currentMouseBufferIndex];
	}

	public static void setCurrentMouseBufferIndex(int currentMouseBufferIndex) {
		GraphicsHoverHandler.currentMouseBufferIndex = currentMouseBufferIndex;
	}

	public static void handleHover(float x, float y) {
		handlers.get(currentMouseBufferIndex).handleHover(x, y, getBufferVertexDataAtMouseBufferIndex());
	}

}
