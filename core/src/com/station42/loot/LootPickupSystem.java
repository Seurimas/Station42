package com.station42.loot;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;
import com.station42.game.ScoringPortal;
import com.station42.optimizations.RoomResident;
import com.station42.world.World;

public class LootPickupSystem implements EngineUpdateListener {

	@Override
	public void update(Engine engine, float delta) {
		for (Entity healthBoostEntity : engine.getEntitiesWithComponent(HealthBoost.class)) {
			findGrabber(engine, healthBoostEntity, HealthBoost.class);
		}
		for (Entity healthBoostEntity : engine.getEntitiesWithComponent(PointBoost.class)) {
			findGrabber(engine, healthBoostEntity, PointBoost.class);
		}
		for (Entity healthBoostEntity : engine.getEntitiesWithComponent(SpeedBoost.class)) {
			findGrabber(engine, healthBoostEntity, SpeedBoost.class);
		}
		for (Entity healthBoostEntity : engine.getEntitiesWithComponent(WorldSwap.class)) {
			findGrabber(engine, healthBoostEntity, WorldSwap.class);
		}
	}
	public void findGrabber(Engine engine, Entity lootDrop, Class<? extends Loot> componentClass) {
		if (lootDrop.getComponent(Loot.class) == null)
			return;
		EntityLocation lootLocation = lootDrop.getComponent(EntityLocation.class);
		if (lootLocation != null) {
			Iterable<Entity> possibleLooters;
			if (lootDrop.getComponent(RoomResident.class) != null)
				possibleLooters = lootDrop.getComponent(RoomResident.class).getNeighboringResidentsWithComponent(EntityLocation.class);
			else
				possibleLooters = engine.getEntitiesWithComponent(EntityLocation.class);
			for (Entity looter : possibleLooters) {
				if (looter.getComponent(Looter.class) == null)
					continue;
				EntityLocation looterLocation = looter.getComponent(EntityLocation.class);
				if (World.visible(looter, lootDrop) && looterLocation.collides(lootLocation)) {
					engine.despawnEntity(lootDrop);
					((Loot)lootDrop.getComponent(componentClass)).onLoot(engine, looter);
				}
			}
		}
	}
}
