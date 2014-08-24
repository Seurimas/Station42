package com.station42.hopping;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;
import com.station42.world.World;

public class HoppingSystem implements EngineUpdateListener {
	@Override
	public void update(Engine engine, float delta) {
		for (Entity hackerEntity : engine.getEntitiesWithComponent(HoppingAction.class)) {
			HoppingAction hackingAction = hackerEntity.getComponent(HoppingAction.class);
			if (hackingAction != null && hackingAction.isTryingToHop()) {
				hackingAction.update(delta);
				if (hackingAction.getCompletionPercent() >= 1) {
					World currentWorld = hackerEntity.getComponent(World.class);
					hackerEntity.setComponent(World.class, currentWorld.nextWorld());
					hackingAction.complete();
				}
			}
		}
	}
}
