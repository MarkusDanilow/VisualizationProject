package com.base.common;

public class DataElement {

	private float x, y, z, time;

	public DataElement(float x, float y, float z, float time) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.time = time;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + z;
	}

}
