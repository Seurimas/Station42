package com.station42.basic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityLocation {
	private Rectangle aabb;
	private Vector2 centerLocation = new Vector2();
	public EntityLocation(float size) {
		this(0, 0, size);
	}
	public EntityLocation(float x, float y, float size) {
		aabb = new Rectangle(x, y, size, size);
		centerLocation.x = x + size / 2;
		centerLocation.y = y + size / 2;
	}
	public void applyVelocity(float x, float y) {
		aabb.x += x;
		aabb.y += y;
		centerLocation.x += x;
		centerLocation.y += y;
	}
	public float getCenterX() {
		return centerLocation.x;
	}
	public float getCenterY() {
		return centerLocation.y;
	}
	public String toString() {
		return "[" + aabb.x + ", " + aabb.y + "] -> [" + (aabb.x + aabb.width) + ", " + (aabb.y + aabb.height) + "]";  
	}
	public float getLeftX() {
		return aabb.x;
	}
	public float getBottomY() {
		return aabb.y;
	}
	public Vector2 getCenter() {
		return centerLocation;
	}
	public float getRadius() {
		return aabb.width / 2;
	}
	public float getDistance(EntityLocation otherEntity) {
		return centerLocation.dst(otherEntity.centerLocation);
	}
	public void setCenter(int x, int y) {
		centerLocation.set(x, y);
		aabb.x = x - aabb.width / 2;
		aabb.y = y - aabb.height / 2;
	}
}
