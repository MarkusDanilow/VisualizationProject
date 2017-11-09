package com.base.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WavefrontModelHandler {

	private static List<WavefrontOBJ> models = new ArrayList<WavefrontOBJ>();
	
	public static void loadModels(){
		models.add(WavefrontOBJ.load("models/TreeOak.obj", 0.5f));
		models.add(WavefrontOBJ.load("models/TreePine.obj", 1.5f));
	}
	
	public static WavefrontOBJ getRandomModel(){
		Random r = new Random();
		int index = r.nextInt(models.size());
		return models.get(index);
	}
	
}
