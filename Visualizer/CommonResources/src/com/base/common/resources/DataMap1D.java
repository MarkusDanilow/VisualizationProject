package com.base.common.resources;

public class DataMap1D {

	private float[] data ;
	
	public DataMap1D() {
	}

	public float[] getData() {
		return data;
	}

	public void setData(float[] data) {
		this.data = data;
	}

	public int getSize(){
		return DataInspector.notNull(data) ? this.data.length : -1 ;
	}
	
}
