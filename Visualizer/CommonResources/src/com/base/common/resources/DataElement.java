package com.base.common.resources;

public class DataElement {

	private float time;
	private Point point;

	private int sampleRate = 0;
	private Range<Float> timeRange = null;

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

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public boolean isSampled() {
		return this.sampleRate > 1 && this.timeRange != null;
	}

	public Range<Float> getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(Range<Float> timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public String toString() {
		return getX() + ", " + getY() + ", " + getZ();
	}

}
