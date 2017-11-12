package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.base.engine.rendering.buffers.DisplayListHandler;

public class WavefrontOBJ {

	final String DISPLAY_LIST_IDENTIFIER ;
	
	private List<Vector3f> vertices;
	
	public WavefrontOBJ(String DLID) {
		this.vertices  = new ArrayList<Vector3f>();
		this.DISPLAY_LIST_IDENTIFIER = DLID ; 
	}
	
	public void addVertex(Vector3f vertex){
		if(vertex != null){
			this.vertices.add(vertex);
		}
	}

	public void prerender(float scale){
		DisplayListHandler.generateDisplayList(DISPLAY_LIST_IDENTIFIER);
		DisplayListHandler.initializeList(DISPLAY_LIST_IDENTIFIER);
		glPushMatrix();
		glScalef(scale, scale, scale);
//		glColor4f(0.8f, 0.5f, 0.1f, 0.2f);
		glColor4f(0.35f, 0.35f, 0.35f, 0.5f);
		glBegin(GL_POINTS);
		for(Vector3f vertex: this.vertices){
			glVertex3f(vertex.x, vertex.y, vertex.z);
		}
		glEnd();
		glPopMatrix();
		DisplayListHandler.endList();
	}
	
	@Override
	public String toString() {
		return this.vertices.toString();
	}
	
	
	public static WavefrontOBJ load(String path, float scale){
		try{
			boolean found = false, isVertex = false ; 
			if(path != null && path.length() > 0){
				File objFile = new File(path);
				BufferedReader br = new BufferedReader(new FileReader(objFile));
				WavefrontOBJ obj = new WavefrontOBJ(objFile.getName());
				String line ;
				while((line = br.readLine()) != null && (!found || isVertex)){
					String[] s = line.split("\\s+");
					isVertex = s[0] != null && s[0].equals("v");
					if(!found){
						found = isVertex ; 
					}
					if(isVertex){
						float vx = Float.parseFloat(s[1]);
						float vy = Float.parseFloat(s[2]);
						float vz = Float.parseFloat(s[3]);
						Vector3f vertex = new Vector3f(vx, vy, vz);
						obj.addVertex(vertex);
					}
				}
				br.close();
				obj.prerender(scale);
				return obj ; 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null ; 
	}
	
	
	
}
