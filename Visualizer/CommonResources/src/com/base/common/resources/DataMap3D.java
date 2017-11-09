package com.base.common.resources;

public class DataMap3D {
	
	float[][][] data ;

	public float[][][] getData() {
		return data;
	}

	public void setData(float[][][] data) {
		this.data = data;
	} 
	
	public int getSizeX(){
		return this.data.length ;
	}
	
	public int getSizeY(){
		return this.data[0].length ;
	}
	
	public int getSizeZ(){
		return this.data[0][0].length ; 
	}

}
