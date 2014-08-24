package com.station42.hacking;

import java.util.ArrayList;

import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class Hackable {
	String description;
	float range;
	float duration;
	private Entity hacker;
	public Hackable(String description, 
			float range, 
			float duration) {
		this.description = description;
		this.range = range;
		this.duration = duration;
	}
	public float getDuration() {
		return duration;
	}
	public void hack(Entity hacker) {
		this.hacker = hacker;
	}
	public Entity getHacker() {
		return hacker;
	}
	public void clearHacker() {
		this.hacker = null;
	}
}
