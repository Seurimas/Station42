package com.station42.loot;

import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.basic.EntitySprite;
import com.station42.death.Health;
import com.station42.game.Station40Game;
import com.station42.world.World;

public class HealthBoost implements Loot {
	public static Entity spawn(Engine engine, float x, float y, World world) {
		Entity loot = new Entity("Health Boost", new HealthBoost(),
				new EntityLocation(x - 8, y - 8, 16),
				new EntitySprite(Station40Game.getSprites(), 48, 64, 16, 16));
		if (world != null)
			loot.setComponent(World.class, world);
		loot.setComponent(Loot.class, loot.getComponent(HealthBoost.class));
		engine.spawnEntity(loot);
		return loot;
	}

	@Override
	public void onLoot(Engine engine, Entity looter) {
		Health health = looter.getComponent(Health.class);
		if (health != null) {
			health.boost(5);
		}
	}
}
