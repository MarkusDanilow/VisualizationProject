package com.base.engine;

import java.awt.image.BufferedImage;
import java.util.List;

import com.base.common.DataElement;
import com.base.common.GenerateInterfaces;

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

	void setRawRenderData(List<DataElement> data);

	void resetViewportDisplayList(int index);

}
