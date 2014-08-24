package com.station42.loot;

import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.basic.EntitySprite;
import com.station42.death.Health;
import com.station42.game.Station40Game;
import com.station42.hopping.HoppingAction;
import com.station42.world.World;

public class WorldSwap implements Loot {
	public static Entity spawn(Engine engine, float x, float y, World world) {
		Entity loot = new Entity("World Swap Boost", new WorldSwap(),
				new EntityLocation(x - 8, y - 8, 16),
				new EntitySprite(Station40Game.getSprites(), 64, 64, 16, 16));
		if (world != null)
			loot.setComponent(World.class, world);
		loot.setComponent(Loot.class, loot.getComponent(WorldSwap.class));
		engine.spawnEntity(loot);
		return loot;
	}

	@Override
	public void onLoot(Engine engine, Entity looter) {
		HoppingAction hopping = looter.getComponent(HoppingAction.class);
		if (hopping != null) {
			hopping.boost(3);
		}
	}
}
