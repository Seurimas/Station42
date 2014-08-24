package com.station42.hacking;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class HackingActionUpdater implements EngineUpdateListener{
	@Override
	public void update(Engine engine, float delta) {
		for (Entity entity : engine.getEntitiesWithComponent(HackingAction.class)) {
			EntityLocation hackerLocation = entity.getComponent(EntityLocation.class);
			HackingAction hackingAction = entity.getComponent(HackingAction.class);
			if (hackingAction != null) {
				hackingAction.calculateEntitiesInRange(engine, hackerLocation);
			}
			Entity target = hackingAction.getTarget();
			if (target != null) {
				float distance = target.getComponent(EntityLocation.class).getDistance(hackerLocation);
				if (distance < target.getComponent(Hackable.class).range && hackingAction.getHackingWanted()) {
					hackingAction.update(delta);
				} else {
					hackingAction.setTarget(null);
				}
			} else {
				HackingActionParticles particles = entity.getComponent(HackingActionParticles.class);
				if (particles != null && particles.alive())
					particles.die();
			}
		}
	}
}
