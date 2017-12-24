package com.base.engine.font.NEW;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.opengl.TextureLoader;

public class NewFontManager {

	private static int fontTexture = 0 ; 
	private static Map<Integer, Point> fontMapping = new HashMap<>();
	
	private static final float resolution = 1f / 8f ; 
	
	static{
		try {
			fontTexture = TextureLoader
					.getTexture("png", new FileInputStream(new File("res/textures/hucVisScatterplotPlaceholder.png")))
					.getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// large letters 
		for(int i = 65; i <= 90; i++){
			
		}
		
		// small letters
		
		// numbers 
		
	}
	
	
	
}
