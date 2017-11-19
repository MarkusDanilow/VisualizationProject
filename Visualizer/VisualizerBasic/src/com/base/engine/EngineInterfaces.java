package com.base.engine;

import java.awt.image.BufferedImage;
import java.util.List;

import com.base.common.GenerateInterfaces;
import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;

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

	void setPointCloudData(List<DataElement> data);
	void setPointCloudClusters(List<Cluster> clusters);

	void resetViewportDisplayList(int index);
	
	void changeScaleFactor(float factor, int viewIndex);

}
