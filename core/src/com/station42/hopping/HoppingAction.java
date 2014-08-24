package com.station42.hopping;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class HoppingAction {
	public static final float duration = 3;
	private boolean wantHacking = false;
	private float hackingTime = 0;
	public void setWantHopping(boolean value) {
		wantHacking = value;
	}
	public boolean isTryingToHop() {
		return wantHacking;
	}
	public float getCompletionPercent() {
		if (wantHacking) {
			float timeNeeded = duration;
			float timeSpent = hackingTime;
			return Math.min(timeSpent / timeNeeded, 1);
		} else {
			return 0;
		}
	}
	public void update(float delta) {
		hackingTime += delta;
	}
	public float getHackingTime() {
		return hackingTime;
	}
	public void complete() {
		hackingTime = 0;
	}
	public void reset() {
		hackingTime = 0;
	}
}
