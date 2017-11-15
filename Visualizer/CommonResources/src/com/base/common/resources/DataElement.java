package com.base.common.resources;

public class DataElement {

	private float time;

	private Point point;

	public DataElement(float x, float y, float z, float time) {
		super();
		this.point = new Point(x, y, z);
		this.time = time;
	}

	public float getX() {
		return this.point.getX();
	}

	public void setX(float x) {
		this.point.setX(x);
	}

	public float getY() {
		return this.point.getY();
	}

	public void setY(float y) {
		this.point.setY(y);
	}

	public float getZ() {
		return this.point.getZ();
	}

	public void setZ(float z) {
		this.point.setZ(z);
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return getX() + ", " + getY() + ", " + getZ();
	}

}
