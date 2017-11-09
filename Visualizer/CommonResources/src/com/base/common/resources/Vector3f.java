package com.base.common.resources;

public class Vector3f extends Vector2f{
	
	public static Vector3f NULL_VECTOR = new Vector3f(0, 0, 0);

	float z ; 

	public Vector3f() {
		this(0, 0, 0);
	}

	public Vector3f(float x, float y, float z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z) ;
	}
	

	@Override
	public boolean equals(Object obj) {
		if(DataInspector.notNull(obj) && Vector3f.class.isAssignableFrom(obj.getClass())){ 
			Vector3f v = (Vector3f) obj ; 
			return v.x == x && v.y == y && v.z == z; 
		}
		return false ; 
	}
	
	@Override
	public String toString() {
		return "Vector: (" + x + "|" + y + "|" + z + ")";
	}

	public static Vector3f multiply(Vector3f v, float scale) {
		return new Vector3f(v.x * scale, v.y * scale, v.z * scale);
	}

	public static Vector2f add(Vector3f...vectors){
		if(vectors != null){
			Vector3f v = new Vector3f();
			for(Vector3f v0: vectors){
				v.x += v0.x ;
				v.y += v0.y ;
				v.z += v0.z ;
			}
			return v ; 
		}
		return null ;
	}
	
	public static float dot(Vector3f vec1, Vector3f vec2) {
		return vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ();
	}

	public static Vector3f connect(float x0, float x1, float y0, float y1, float z1, float z0){
		return new Vector3f(Math.abs(x1 - x0), Math.abs(y1 - y0), Math.abs(z1 - z0));
	}

	public static Vector3f connect(Vector3f v, float x0, float y0, float z0){
		return connect(v.x, x0, v.y, y0, v.z, z0);
	}
	
	public static Vector3f difference(Vector3f vec1, Vector3f vec2){
		if(DataInspector.notNull(vec1, vec2)){
			float x = vec2.x - vec1.x ; 
			float y = vec2.y - vec1.y ; 
			float z = vec2.z - vec1.z ;
			return new Vector3f(x, y, z);
		}
		return NULL_VECTOR;
	}
	
}
