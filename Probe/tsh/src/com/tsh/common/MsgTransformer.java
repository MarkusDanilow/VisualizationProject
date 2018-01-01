package com.tsh.common;

import com.tsh.gps.GPSHandler;

public class MsgTransformer {
	private String text;
	private double value;
	private int[] vector = new int[3];
	private String[] gps;

	public MsgTransformer(String text, double value) {
		this.text = text;
		this.value = value;
	}

	public MsgTransformer() {
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setVector(int[] xyz) throws ArrayIndexOutOfBoundsException {
		if (vector.length == xyz.length) {
			this.vector = xyz;
		}
	}

	public void setGPS(String rawGPSData) {
		GPSHandler gpsh = new GPSHandler();
		gps = rawGPSData.split(",");
		if (gps[2].equals("A")) {
			System.out.println(gpsh.convertLat(gps[3], gps[4]));
			System.out.println(gpsh.convertLon(gps[5], gps[6]));
			gps[3] = String.valueOf(gpsh.convertLat(gps[3], gps[4]));
			gps[5] = String.valueOf(gpsh.convertLon(gps[5], gps[6]));
		}
	}

	public String getPGS() {
		String p = "&gSt=" + gps[2] + "&gLt=" + gps[3] + "&gNS=" + gps[4] + "&gLg=" + gps[5] + "&gEW=" + gps[6] + "&gSOG=" + gps[7];
		return p;
	}

	public String getVector() {
		return String.valueOf(this.vector[0]) + " " + String.valueOf(this.vector[1]) + " " + String.valueOf(this.vector[2]);
	}

	@Override
	public String toString() {
		return text + " " + String.valueOf(value);
	}
}