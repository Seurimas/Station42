package com.station42.player.mouse;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityMouseState {
	private boolean[] mouseStates = new boolean[2];
	private float x, y;
	public void setX(float f) {
		this.x = f;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setLeftDown(boolean buttonPressed) {
		mouseStates[0] = buttonPressed;
	}
	public void setRightDown(boolean buttonPressed) {
//		if (mouseStates[1])
//			System.out.println("DOWN!");
		mouseStates[1] = buttonPressed;
	}
	public boolean isLeftDown() {
		return mouseStates[0];
	}
	public boolean isRightDown() {
		return mouseStates[1];
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Vector2 getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
