package com.base.common.resources;

public class StatisticObject {

	private Float a;
	private Float b;
	private Float c;

	public StatisticObject() {

	}

	public StatisticObject(Float a, Float b) {
		this.a = a;
		this.b = b;
	}

	public StatisticObject(Float a, Float b, Float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Float getC() {
		return c;
	}

	public void setC(Float c) {
		this.c = c;
	}

	public Float getA() {
		return a;
	}

	public void setA(Float a) {
		this.a = a;
	}

	public Float getB() {
		return b;
	}

	public void setB(Float b) {
		this.b = b;
	}

	public String toString() {
		//Regression: a = m, b = n, c = stdErr
		return "y = "+getA() +"x + "+ getB()+"\nStandardfehler: "+getC()+"\n";
		
	}
}
