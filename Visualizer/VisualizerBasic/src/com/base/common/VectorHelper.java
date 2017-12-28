package com.base.common;

import org.lwjgl.util.vector.Vector3f;

public class VectorHelper {

	public static Vector3f mult(Vector3f vector, float val){
		return new Vector3f(vector.x * val, vector.y * val, vector.z * val) ; 
	}
	
	public static Vector3f mult(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.x * vec2.x, vec1.y * vec2.y, vec1.z * vec2.z) ; 
	}
	
	public static Vector3f div(Vector3f vector, float val){
		return new Vector3f(vector.x / val, vector.y / val, vector.z / val) ; 
	}

	public static Vector3f add(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z) ; 
	}

	public static Vector3f sub(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z) ; 
	}
	
	public static float length(Vector3f vector){
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}
	
}
