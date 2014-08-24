package com.station42.hacking;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class HackingAction {
	private boolean wantHacking = false;
	private Entity hackingTarget;
	private float hackingTime = 0;
	public void setWantHacking(boolean value) {
		wantHacking = value;
	}
	public boolean isHacking(Entity target) {
		return hackingTarget == target;
	}
	public boolean isTryingToHack() {
		return wantHacking && hackingTarget == null;
	}
	public float getCompletionPercent() {
		if (hackingTarget != null) {
			Hackable hackable = hackingTarget.getComponent(Hackable.class);
			float timeNeeded = hackable.duration;
			float timeSpent = hackingTime;
			return Math.min(timeSpent / timeNeeded, 1);
		} else {
			return 0;
		}
	}
	public void setTarget(Entity target) {
		if (!isHacking(target)) {
			hackingTarget = target;
			hackingTime = 0;
		}
	}
	public void update(float delta) {
		if (hackingTarget != null) {
			hackingTime += delta;
		} else {
			hackingTime = 0;
			hackingTarget = null;
		}
	}
	public float getHackingTime() {
		return hackingTime;
	}
	public void complete() {
		hackingTarget = null;
	}
	public Entity getTarget() {
		return hackingTarget;
	}
	public boolean getHackingWanted() {
		return wantHacking;
	}
	ArrayList<Entity> entitiesInRange = new ArrayList<Entity>();
	public void calculateEntitiesInRange(Engine engine, EntityLocation myLocation) {
		entitiesInRange.clear();
		for (Entity possibleEntity : engine.getEntitiesWithComponent(Hackable.class)) {
			EntityLocation location = possibleEntity.getComponent(EntityLocation.class);
			Hackable hackable = possibleEntity.getComponent(Hackable.class);
			if (location.getDistance(myLocation) <= hackable.range) {
				entitiesInRange.add(possibleEntity);
			}
		}
	}
	public ArrayList<Entity> getEntitiesInRange() {
		return entitiesInRange;
	}
	public Entity getPreferredTarget(Entity hacker) {
		float bestDistance = 9999;
		Entity bestTarget = null;
		for (Entity possibleEntity : entitiesInRange) {
			if (HackingSystem.canHack(hacker, possibleEntity))
			{
				float distance = hacker.getComponent(EntityLocation.class).getDistance(possibleEntity.getComponent(EntityLocation.class));
				if (distance < bestDistance) {
					bestTarget = possibleEntity;
					bestDistance = distance;
				}
			}
//				return possibleEntity;
		}
		return bestTarget;
	}
}
