package com.base.common.resources;

public class Regression {

	private Float m;
	private Float n;
	private Float stdErr;

	public Regression() {

	}

	public Regression(Float m, Float n) {
		this.m = m;
		this.n = n;
	}

	public Regression(Float m, Float n, Float stdErr) {
		this.m = m;
		this.n = n;
		this.stdErr = stdErr;
	}

	public Float getStdErr() {
		return stdErr;
	}

	public void setStdErr(Float stdErr) {
		this.stdErr = stdErr;
	}

	public Float getM() {
		return m;
	}

	public void setM(Float m) {
		this.m = m;
	}

	public Float getN() {
		return n;
	}

	public void setN(Float n) {
		this.n = n;
	}

	public String toString() {
		return "y = "+getM() +"x + "+ getN()+"\nStandardfehler: "+getStdErr()+"\n";
		
	}
}
