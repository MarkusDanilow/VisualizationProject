package com.base.common;

public interface Renderable {

	void prepare();
	void render();
	void render(float[][] data);
	void close();
	
}
