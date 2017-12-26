package com.base.engine.interaction.data;

public class Rectangle {

	public float x1, x2, y1, y2;

	public Rectangle(float x1, float x2, float y1, float y2) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public boolean isInside(float x, float y) {
		return x > x1 && x < x2 && y > y1 && y < y2;
	}

	@Override
	public String toString() {
		return x1 + ", " + x2 + ", " + y1 + ", " + y2;
	}

}
