package com.station42.loot;

import java.util.Random;

import com.station42.base.Entity;

public class LootDrop {
	static final Random random = new Random();
	public float interval, sinceLast;
	public int chance;
	public Entity lastDrop;
	public LootDrop(float interval, int chance) {
		this.interval = interval;
		sinceLast = 0;
		this.chance = chance;
	}
}
