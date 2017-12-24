package com.base.engine.interaction.data;

import java.util.List;

import com.base.common.resources.DataElement;

public abstract class AHoverBufferData {

	protected List<DataElement> rawData;

	public List<DataElement> getRawData() {
		return rawData;
	}

	public void setRawData(List<DataElement> rawData) {
		this.rawData = rawData;
	}

}
