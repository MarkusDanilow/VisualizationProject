package com.base.engine.interaction;

public class InteractionEventResult {

	private int bufferIndex;
	float lx;
	float ly;

	public InteractionEventResult(int bufferIndex, float lx, float ly) {
		super();
		this.bufferIndex = bufferIndex;
		this.lx = lx;
		this.ly = ly;
	}

	public int getBufferIndex() {
		return bufferIndex;
	}

	public void setBufferIndex(int bufferIndex) {
		this.bufferIndex = bufferIndex;
	}

	public float getLx() {
		return lx;
	}

	public void setLx(float lx) {
		this.lx = lx;
	}

	public float getLy() {
		return ly;
	}

	public void setLy(float ly) {
		this.ly = ly;
	}

}
