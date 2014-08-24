package com.station42.hopping;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;
import com.station42.world.World;

public class OffensiveHackingSystem implements EngineUpdateListener {
	@Override
	public void update(Engine engine, float delta) {
		for (Entity hackedEntity : engine.getEntitiesWithComponent(HackingAction.class)) { // Because only players can be hacked.
			Hackable hackable = hackedEntity.getComponent(Hackable.class);
			if (hackable != null && hackable.getHacker() != null) {
				hackedEntity.setComponent(World.class, hackedEntity.getComponent(World.class).nextWorld());
				hackedEntity.getComponent(HackingAction.class).setTarget(null);
				HoppingAction hopping = hackedEntity.getComponent(HoppingAction.class);
				if (hopping != null) {
					hopping.setWantHopping(false);
					hopping.reset();
				}
				hackable.clearHacker();
			}
		}
	}

//	private void updateHack(Entity sentryEntity, Entity hacker) {
//		HackingAction hackingAction = hacker.getComponent(HackingAction.class);
//		if (hackingAction != null) {
//			if (hackingAction.isHacking(sentryEntity) && hackingAction.getHackingTime() > hackingAction.getTarget().getComponent(Hackable.class).getDuration()) {
//				EntityFaction faction = hacker.getComponent(EntityFaction.class);
//				sentryEntity.setComponent(EntityFaction.class, faction);
//				sentryEntity.setComponent(EntitySprite.class, faction.getTurretSprite());
//				hackingAction.complete();
//			} else if (hackingAction.isTryingToHack() && sentryEntity.getComponent(EntityFaction.class) != hacker.getComponent(EntityFaction.class)) {
//				hackingAction.setTarget(sentryEntity);
//			}
//		}
//	}
}
