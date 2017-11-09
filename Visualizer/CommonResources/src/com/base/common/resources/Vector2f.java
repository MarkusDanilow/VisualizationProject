package com.base.common.resources;

public class Vector2f {

	public static Vector2f NULL_VECTOR = new Vector2f(0, 0);
	
	float x, y;

	public Vector2f() {
		this(0, 0);
	}

	public Vector2f(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public static Vector2f multiply(Vector2f v, float scale) {
		return new Vector2f(v.x * scale, v.y * scale);
	}

	public static Vector2f add(Vector2f...vectors){
		if(vectors != null){
			Vector2f v = new Vector2f();
			for(Vector2f v0: vectors){
				v.x += v0.x ;
				v.y += v0.y ;
			}
			return v ; 
		}
		return null ;
	}
	
	public static float dot(Vector2f vec1, Vector2f vec2) {
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY();
	}

	public static Vector2f connect(float x0, float x1, float y0, float y1){
		return new Vector2f(Math.abs(x1 - x0), Math.abs(y1 - y0));
	}

	public static Vector2f connect(Vector2f v, float x0, float y0){
		return connect(v.x, x0, v.y, y0);
	}
	
	public static Vector2f difference(Vector2f vec1, Vector2f vec2){
		if(DataInspector.notNull(vec1, vec2)){
			float x = vec2.x - vec1.x ; 
			float y = vec2.y - vec1.y ; 
			return new Vector2f(x, y);
		}
		return NULL_VECTOR;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(DataInspector.notNull(obj) && Vector2f.class.isAssignableFrom(obj.getClass())){ 
			Vector2f v = (Vector2f) obj ; 
			return v.x == x && v.y == y ; 
		}
		return false ; 
	}
	
	@Override
	public String toString() {
		return "Vector: (" + x + "|" + y + ")";
	}

}
