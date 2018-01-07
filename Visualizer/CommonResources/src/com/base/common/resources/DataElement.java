package com.base.common.resources;

public class DataElement {

	public static enum DataType {

		ID("id"), TIME("t"), X("x"), Y("y"), Z("z"), LAT("lat"), LNG("lng"), DIST("dist");

		public final String name;

		private DataType(String name) {
			this.name = name;
		}

		public static float getValueByType(DataType t, DataElement e) {
			if (t != null && e != null) {
				switch (t) {
				case ID:
					return e.getId();
				case TIME:
					return e.getTime();
				case X:
					return e.getPoint().getX();
				case Y:
					return e.getPoint().getY();
				case Z:
					return e.getPoint().getZ();
				case LAT:
					return e.getRealLat();
				case LNG:
					return e.getRealLng();
				case DIST:
					return e.getDistance();
				}
			}
			return -1f;
		}
		
	}

	private static Range<Float> latRange = new Range<Float>(48.47885489462709f, 48.48547494510185f);
	private static Range<Float> lngRange = new Range<Float>(9.183356612920761f, 9.19342964887619f);

	public static int scale;

	private float id;
	private float time;
	private Point point;
	private float lat, lng;
	private float realLat, realLng;
	private float distance;

	private int sampleRate = 0;
	private Range<Float> timeRange = null;

	public DataElement(float x, float y, float z, float id) {
		super();
		this.point = new Point(x, y, z);
		this.id = id;
	}

	public DataElement(float x, float y, float z, float id, float lat, float lng, float distance) {
		this(x, y, z, id);
		this.setLat(lat);
		this.setLng(lng);
		this.setDistance(distance);
	}

	public float getId() {
		return id;
	}

	public void setId(float id) {
		this.id = id;
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
		return id;
	}

	public void setTime(float time) {
		this.id = time;
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

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		if (lat < latRange.getLoVal())
			lat = latRange.getLoVal();
		if (lat > latRange.getHiVal())
			lat = latRange.getHiVal();
		this.realLat = lat;
		this.lat = MathUtil.mapWithoutPrecision(lat, latRange.getLoVal(), latRange.getHiVal(), 0, scale);
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		if (lng < lngRange.getLoVal())
			lng = lngRange.getLoVal();
		if (lng > lngRange.getHiVal())
			lng = lngRange.getHiVal();
		this.realLng = lng;
		this.lng = MathUtil.mapWithoutPrecision(lng, lngRange.getLoVal(), lngRange.getHiVal(), 0, scale);
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getRealLat() {
		return realLat;
	}

	public void setRealLat(float realLat) {
		this.realLat = realLat;
	}

	public float getRealLng() {
		return realLng;
	}

	public void setRealLng(float realLng) {
		this.realLng = realLng;
	}

	@Override
	public String toString() {
		return "x: " + getX() + ", " + 
				"y: " + getY() + ", " + 
				"z: " + getZ() + ", "+
				"lat: " + getRealLat() + ", " + 
				"lng: " + getRealLng() +"; ";
	}

}
