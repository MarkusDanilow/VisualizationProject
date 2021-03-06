package com.base.engine.interaction;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.base.common.resources.DataElement;
import com.base.engine.Engine;
import com.base.engine.EngineInterfaces;
import com.base.engine.Settings;
import com.base.engine.interaction.data.AHoverBufferData;

public class GraphicsHoverHandler {

	private static AHoverBufferData[] bufferedVertexData = new AHoverBufferData[Engine.NUM_VIEWS];
	private static int currentBufferIndex = 0;
	private static int currentMouseBufferIndex = 0;

	private static Map<String, AHoverHandler> handlerMapping = new HashMap<>();
	private static List<AHoverHandler> handlers = new ArrayList<>();

	private static Map<Integer, FloatBuffer> modelViewMatrices = new HashMap<>();
	private static Map<Integer, FloatBuffer> projectionMatrices = new HashMap<>();
	private static Map<Integer, IntBuffer> viewportMatrices = new HashMap<>();

	static {

		PointCloudHoverHandler pointCloud = new PointCloudHoverHandler();
		LineChartHoverHandler lineChart = new LineChartHoverHandler();
		BarChartHoverHandler barChart = new BarChartHoverHandler();
		ParallelCoordinatesHoverHandler parallelCorrdinates = new ParallelCoordinatesHoverHandler();

		handlerMapping.put(Settings.get3DView(), pointCloud);
		handlerMapping.put(Settings.getLineChartView(), lineChart);
		handlerMapping.put(Settings.getBarChartView(), barChart);
		handlerMapping.put(Settings.getParallelCoordinatesView(), parallelCorrdinates);

		handlers.add(pointCloud);
		handlers.add(lineChart);
		handlers.add(barChart);
		handlers.add(parallelCorrdinates);
	}

	public static void setHoverHandlerForView(int viewportIndex, String viewName) {
		handlers.set(viewportIndex, handlerMapping.get(viewName));
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

	public static void handleHover(EngineInterfaces engine, float x, float y) {
		DataElement hoverData = handlers.get(currentMouseBufferIndex).handleHover(x, y,
				getBufferVertexDataAtMouseBufferIndex());
		engine.setHoverData(currentMouseBufferIndex, hoverData, x, y);
	}

	public static FloatBuffer getModelViewMatrixAtCurrentMouseIndex() {
		return modelViewMatrices.get(currentMouseBufferIndex);
	}

	public static FloatBuffer getProjectionMatrixAtCurrentMouseIndex() {
		return projectionMatrices.get(currentMouseBufferIndex);
	}

	public static IntBuffer getViewportMatrixAtCurrentMouseIndex() {
		return viewportMatrices.get(currentMouseBufferIndex);
	}

	public static void storeCurrentMatrices() {
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		modelViewMatrices.put(currentBufferIndex, modelView);
		viewportMatrices.put(currentBufferIndex, viewport);
		projectionMatrices.put(currentBufferIndex, projection);
	}

}
