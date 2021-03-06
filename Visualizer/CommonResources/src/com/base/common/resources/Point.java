package com.base.common.resources;

public class Point {

	private int index = -1; // denotes which Cluster it belongs to
	public float x, y, z;

	public Point(double d, double e, double f) {
		super();
		this.x = (float) d;
		this.y = (float) e;
		this.z = (float) f;
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

	public double getSquareOfDistance(Point anotherPoint) {
		return (x - anotherPoint.x) * (x - anotherPoint.x) + (y - anotherPoint.y) * (y - anotherPoint.y)
				+ (z - anotherPoint.z) * (z - anotherPoint.z);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

}
