package com.base.common.resources;

public class DataMap2D {

	private float[][] data;

	public DataMap2D() {
	}

	public float[][] getData() {
		return data;
	}

	public void setData(float[][] data) {
		this.data = data;
	}

	public int getSizeX(){
		return data != null ? data.length : -1 ; 
	}
	
	public int getSizeY(){
		return getSizeX() > 0 && data[0] != null ? data[0].length : -1 ; 
	}
	
}
