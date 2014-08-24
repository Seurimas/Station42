package com.station42.hacking;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;
import com.station42.world.World;

public class HackingSystem implements EngineUpdateListener {
	@Override
	public void update(Engine engine, float delta) {
//		for (Entity sentryEntity : engine.getEntitiesWithComponent(Sentry.class)) {
//			Sentry sentry = sentryEntity.getComponent(Sentry.class);
//			Hackable hackable = sentryEntity.getComponent(Hackable.class);
//			if (hackable != null) {
//				for (Entity hacker : hackable.getEntitiesInRange()) {
//					updateHack(sentryEntity, hacker);
//				}
//			}
//		}
		for (Entity hackerEntity : engine.getEntitiesWithComponent(HackingAction.class)) {
			HackingAction hackingAction = hackerEntity.getComponent(HackingAction.class);
			if (hackingAction != null) {
				Entity preferredHackTarget = hackingAction.getPreferredTarget(hackerEntity);
				if (preferredHackTarget != null)
					updateHack(preferredHackTarget, hackerEntity);
//				for (Entity hackableEntity : hackingAction.getEntitiesInRange()) {
//				}
			}
		}
	}

	private void updateHack(Entity sentryEntity, Entity hacker) {
		HackingAction hackingAction = hacker.getComponent(HackingAction.class);
		Hackable hackable = sentryEntity.getComponent(Hackable.class);
		if (hackingAction != null) {
			if (hackingAction.isHacking(sentryEntity) && hackingAction.getHackingTime() > hackable.getDuration()) {
//				EntityFaction faction = hacker.getComponent(EntityFaction.class);
//				sentryEntity.setComponent(EntityFaction.class, faction);
//				sentryEntity.setComponent(EntitySprite.class, faction.getTurretSprite());
				hackable.hack(hacker);
				hackingAction.complete();
			} else {
				if (hackingAction.isTryingToHack() && canHack(hacker, sentryEntity)) {
					hackingAction.setTarget(sentryEntity);
				}
			}
		}
	}

	public static boolean canHack(Entity hacker, Entity sentryEntity) {
		boolean canHack = World.visible(hacker, sentryEntity) &&
							sentryEntity.getComponent(EntityFaction.class) != hacker.getComponent(EntityFaction.class);
		return canHack;
	}
}
