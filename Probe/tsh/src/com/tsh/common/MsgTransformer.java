package com.tsh.common;

public class MsgTransformer {
	private static String text;
	private static double value;
	private static int[] vector = new int[3];

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

	public void setVector (int[] xyz) throws ArrayIndexOutOfBoundsException{
		if(vector.length == xyz.length){
			this.vector = xyz;
		}
	}

	public String getVector() {
		return String.valueOf(this.vector[0]) + " " + String.valueOf(this.vector[1]) + " " + String.valueOf(this.vector[2]);
	}

	@Override
	public String toString() {
		return text + " " + String.valueOf(value);
	}
}