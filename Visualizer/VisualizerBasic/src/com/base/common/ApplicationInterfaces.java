package com.base.common;

import com.base.engine.EngineInterfaces;

public interface ApplicationInterfaces extends GenerateInterfaces {

	void setEngineReference(EngineInterfaces engineReference);
	
	float[][] getDataToRender();
	
	void smooth(int smoothness);
	
}
