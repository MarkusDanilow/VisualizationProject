package com.base.common;

import com.base.common.resources.Range;

public interface GenerateInterfaces {

	void generatePerlinNoise2D(int w, int h, int o);
	void generatePerlinNoise2D(int w, int h, int o, Range<Float> range);
	void generatePerlinNoise2D(int w, int h, int o, Range<Float> range, int smoothness);

	void generatePerlinNoise3D(int sx, int sy, int sz, int o, int smoothness);
	
	void generateRandomNoise(int w, int h);
	void generateRandomNoise(int w, int h, Range<Float> range);
	void generateRandomNoise(int w, int h, Range<Float> range, int smoothness);
	
	void generateMidpointDisplacement(int w);
	void generateMidpointDisplacement(int w, Range<Float> range);
	void generateMidpointDisplacement(int w, Range<Float> range, int smoothness);
	
	void generateDiamondSquare(int w);
	void generateDiamondSquare(int w, Range<Float> range);
	void generateDiamondSquare(int w, Range<Float> range, int smoothness);
	
	void generateSimplexNoise2D(int w, int h, int o);
	void generateSimplexNoise2D(int w, int h, int o, Range<Float> range);
	void generateSimplexNoise2D(int w, int h, int o, Range<Float> range, int smoothness);
	
	void generateSimplexNoise3D(int sx, int sy, int sz, int o, int smoothness);
	
}
