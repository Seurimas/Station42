package com.station42.player.move;

import com.badlogic.gdx.math.Rectangle;

public class EntityMoveState {
	private enum Directions {
		UP,
		LEFT,
		DOWN,
		RIGHT
	};
	private float[] moveStates = new float[4];
	public float getHorizontal() {
		return -moveStates[Directions.LEFT.ordinal()] + moveStates[Directions.RIGHT.ordinal()];
	}
	public float getVertical() {
		return moveStates[Directions.UP.ordinal()] - moveStates[Directions.DOWN.ordinal()];
	}
	public void setUp(float value) {
		moveStates[Directions.UP.ordinal()] = value;
	}
	public void setDown(float value) {
		moveStates[Directions.DOWN.ordinal()] = value;
	}
	public void setLeft(float value) {
		moveStates[Directions.LEFT.ordinal()] = value;
	}
	public void setRight(float value) {
		moveStates[Directions.RIGHT.ordinal()] = value;
	}
	public String toString() {
		return "" + getHorizontal() + ", " + getVertical();
	}
	public boolean isAnalog() {
		return true;
	}
}
