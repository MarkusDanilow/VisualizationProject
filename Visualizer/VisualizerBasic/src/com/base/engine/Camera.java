package com.base.engine;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

	public static final float MOUSE_SENSITIVITY = 0.05f;
	private static float SPEED = 0.5f;

	public static float getSpeed() {
		return SPEED;
	}

	public static void setSpeed(float speed) {
		SPEED = speed;
	}

	private float yaw = 0, pitch = 0;
	private Vector3f pos = new Vector3f(0, 0, 0);

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public Vector3f getPos() {
		return pos;
	}

	public Camera() {
	}

	public Camera(Vector3f pos) {
		this.pos = pos;
	}

	public void yaw(float val) {
		val *= MOUSE_SENSITIVITY;
		this.yaw += val;
	}

	public void pitch(float val) {
		val *= MOUSE_SENSITIVITY;
		this.pitch += val;
	}

	public void update() {
		// this.physics.fall(this);
	}

	private void up(float distance) {
		try {
			float y = pos.getY();
			y -= distance;
			pos.setY(y);
		} catch (RuntimeException e) {
		}
	}

	private void down(float distance) {
		try {
			float y = pos.getY();
			y += distance;
			pos.setY(y);
		} catch (RuntimeException e) {
		}
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	private void forwards(float distance) {
		try {
			float x = pos.getX();
			float z = pos.getZ();
			float deltaX = distance * (float) Math.sin(Math.toRadians(yaw));
			float deltaZ = distance * (float) Math.cos(Math.toRadians(yaw));
			x -= deltaX;
			z += deltaZ;
			pos.setX(x);
			pos.setZ(z);
		} catch (RuntimeException e) {
		}
	}

	private void backwards(float distance) {
		try {
			float x = pos.getX();
			float z = pos.getZ();
			float deltaX = distance * (float) Math.sin(Math.toRadians(yaw));
			float deltaZ = distance * (float) Math.cos(Math.toRadians(yaw));
			x += deltaX;
			z -= deltaZ;
			pos.setX(x);
			pos.setZ(z);
		} catch (RuntimeException e) {
		}
	}

	private void strafeLeft(float distance) {
		try {
			float x = pos.getX();
			float z = pos.getZ();
			float deltaX = distance * (float) Math.sin(Math.toRadians(yaw - 90));
			float deltaZ = distance * (float) Math.cos(Math.toRadians(yaw - 90));
			x -= deltaX;
			z += deltaZ;
			pos.setX(x);
			pos.setZ(z);
		} catch (RuntimeException e) {
		}
	}

	private void strafeRight(float distance) {
		try {
			float x = pos.getX();
			float z = pos.getZ();
			float deltaX = distance * (float) Math.sin(Math.toRadians(yaw + 90));
			float deltaZ = distance * (float) Math.cos(Math.toRadians(yaw + 90));
			x -= deltaX;
			z += deltaZ;
			pos.setX(x);
			pos.setZ(z);
		} catch (RuntimeException e) {
		}
	}

	public void move(EDirection dir, float speed) {
		switch (dir) {
		case NORTH:
			this.forwards(speed);
			break;
		case SOUTH:
			this.backwards(speed);
			break;
		case WEST:
			this.strafeLeft(speed);
			break;
		case EAST:
			this.strafeRight(speed);
			break;
		case UP:
			this.up(speed);
			break;
		case DOWN:
			this.down(speed);
			break;
		}
	}

	public void move(EDirection dir) {
		this.move(dir, getSpeed());
	}

	public String toString() {
		return "Position absolute: ( " + pos.getX() + " | " + pos.getY() + " | " + pos.getZ() + " )";
	}

}
