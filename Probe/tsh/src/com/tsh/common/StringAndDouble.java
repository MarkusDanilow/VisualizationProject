package com.tsh.common;

public class StringAndDouble {
	private static String text;
	private static double value;

	public StringAndDouble(String text, double value) {
		this.text = text;
		this.value = value;
	}

	public StringAndDouble() {
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

	@Override
	public String toString() {
		return text + " " + String.valueOf(value);
	}
}