package com.station42.bullet;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Entity;

public class Bullet {
	private static Vector2 temp = new Vector2();
	private Vector2 startingLocation;
	private Vector2 nextLocation;
	private Vector2 velocity;
	private Entity shooter;
	private float lifetime;
	public Bullet(Vector2 origin, Vector2 velocity, Entity shooter) {
		this(origin, velocity, shooter, 1);
	}
	public Bullet(Vector2 origin, Vector2 velocity, Entity shooter, float lifetime) {
		this.startingLocation = origin.cpy();
		this.velocity = velocity.cpy();
		nextLocation = startingLocation.cpy().add(velocity);
		this.shooter = shooter;
		this.lifetime = lifetime;
	}
	public float intersectDurationLine(Vector2 segmentStart, Vector2 segmentEnd) {
		if (Intersector.intersectSegments(startingLocation,  nextLocation, 
				segmentStart, segmentEnd, temp))
		{
			return temp.dst(startingLocation) / velocity.len();
		} else {
			return -1;
		}
	}
	public float intersectDurationCircle(Vector2 center, float radius) {
		float displacement = Intersector.intersectSegmentCircleDisplace(startingLocation, nextLocation, center, radius, temp);
		if (displacement != Float.POSITIVE_INFINITY) {
			return temp.scl(-displacement).add(center).dst(startingLocation) / velocity.len();
		} else {
			return -1;
		}
	}
	public boolean isShooter(Entity testedEntity) {
		return testedEntity == shooter;
	}
	public boolean isAlive() {
		return lifetime > 0;
	}
	public void update(float delta) {
		temp.set(velocity);
		temp.scl(delta);
		startingLocation.add(temp);
		nextLocation.add(temp);
		lifetime -= delta;
	}
	public Vector2 getCurrentLocation() {
		return startingLocation;
	}
	public Vector2 getNextLocation() {
		return nextLocation;
	}
	public Vector2 getNextLocation(float f) {
		return temp.set(velocity).scl(f).add(startingLocation);
	}
	public Entity getShooter() {
		return shooter;
	}
}
