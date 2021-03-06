package com.base.engine;

import java.awt.image.BufferedImage;
import java.util.List;

import com.base.common.GenerateInterfaces;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.DataElement.DataType;
import com.base.common.resources.StatisticObject;
import com.base.engine.interaction.InteractableRectangle;

public interface EngineInterfaces extends GenerateInterfaces {

	boolean isRunning();

	void updateHints(String hints);

	void toggleHelp();

	void setRunning(boolean running);

	void resetDisplayLists();

	void changeRenderMode(int renderMode);

	void toggleAnimation();

	void toggleGrid();

	void toggleWater();

	void toggleVegetation();

	void changeCameraSpeed(float speed);

	void smooth(int smoothness);

	void loadHeightmap(BufferedImage heightmap);

	void setPointCloudClusters(List<Cluster> clusters);

	void setPointCloudData(List<DataElement>[] data);

	void setPointCloudData(int viewportIndex, List<DataElement> data);

	void setChartData(List<DataElement> data);

	void resetViewportDisplayList(int index);

	void resetAllViewportDisplayLists();

	void changeScaleFactor(float factor, int viewIndex);

	void toggleCompleteParallelCoordinates();

	void setHoverData(int viewportIndex, DataElement data, float x, float y);

	void setView(int viewportIndex, String viewName);

	void setDataType(int viewportIndex, DataType[] type);

	void toggleDataType(int viewportIndex, DataType type, boolean toggled, int position);

	List<InteractableRectangle> getViewports();

	void setStatisticObject(int viewportIndex, StatisticObject type);
	void toggleStats(int viewportIndex, boolean toggled);

	void rotateView(int viewportIndex, int angle);
	
	void toggleTimeColorInvert(boolean toggle);

}
