package com.station42.game;

import com.badlogic.gdx.audio.Sound;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;

public class ScoringPortalHackingSystem implements EngineUpdateListener {
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
//		for (Entity hackerEntity : engine.getEntitiesWithComponent(HackingAction.class)) {
//			HackingAction hackingAction = hackerEntity.getComponent(HackingAction.class);
//			if (hackingAction != null) {
//				for (Entity hackableEntity : hackingAction.getEntitiesInRange()) {
//					updateHack(hackableEntity, hackerEntity);
//				}
//			}
//		}
		for (Entity portalEntity : engine.getEntitiesWithComponent(ScoringPortal.class)) {
			Hackable hackable = portalEntity.getComponent(Hackable.class);
			if (hackable != null && hackable.getHacker() != null) {
				Entity hacker = hackable.getHacker();
				EntityFaction faction = hacker.getComponent(EntityFaction.class);
				portalEntity.setComponent(EntityFaction.class, faction);
				portalEntity.getComponent(ScoringPortal.class).switchFaction(faction);
				Station40Game.manager.get("sounds/Powerup50.wav", Sound.class).play(0.5f);
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
