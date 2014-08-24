package com.station42.loot;

import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.basic.EntitySprite;
import com.station42.game.Station40Game;
import com.station42.world.World;

public class SpeedBoost {
	public static Entity spawn(Engine engine, float x, float y, World world) {
		Entity loot = new Entity("Speed Boost", new SpeedBoost(),
				new EntityLocation(x - 8, y - 8, 16),
				new EntitySprite(Station40Game.getSprites(), 32, 64, 16, 16));
		if (world != null)
			loot.setComponent(World.class, world);
		engine.spawnEntity(loot);
		return loot;
	}
}
