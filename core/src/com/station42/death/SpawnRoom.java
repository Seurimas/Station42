package com.station42.death;

import com.station42.base.Entity;
import com.station42.faction.EntityFaction;

public class SpawnRoom {
	EntityFaction spawningFaction;
	public SpawnRoom(EntityFaction faction) {
		spawningFaction = faction;
	}
	public boolean canSpawn(Entity respawnedPlayer) {
		return respawnedPlayer.getComponent(EntityFaction.class) == spawningFaction;
	}
}
