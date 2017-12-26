package com.base.common;

import java.awt.Color;

public class MadColor {

	float red, green, blue, alpha;

	public MadColor(int red, int green, int blue, int alpha) {
		this.red = red / 255f;
		this.green = green / 255f;
		this.blue = blue / 255f;
		this.alpha = alpha / 255f;
	}
	
	public MadColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float aplha) {
		this.alpha = aplha;
	}

	public Color toAwtColor(){
		return new Color(red, green, blue, alpha);
	}
	
}
