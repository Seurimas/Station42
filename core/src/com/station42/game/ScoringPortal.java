package com.station42.game;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;
import com.station42.sentries.Sentry;
import com.station42.world.World;

public class ScoringPortal {
	public static Entity spawnPortal(Engine engine, float x, float y, World world) {
		Entity sentry = new Entity("Portal",
				new Hackable("Capture", 50, 5f),
				new EntityLocation(x - 8, y - 8, 16));
		sentry.setComponent(ScoringPortal.class, new ScoringPortal(sentry));
		engine.spawnEntity(sentry);
		return sentry;
	}
	private static ParticleEffect getParticleEffect(EntityFaction faction) {
		if (faction == EntityFaction.blue) {
			return new ParticleEffect(Station40Game.manager.get("blue_portal", ParticleEffect.class));
		} else if (faction == EntityFaction.red) {
			return new ParticleEffect(Station40Game.manager.get("red_portal", ParticleEffect.class));
		} else {
			return new ParticleEffect(Station40Game.manager.get("white_portal", ParticleEffect.class));
		}
	}
	ParticleEffect particleEffect;
	Entity portal;
	public ScoringPortal(Entity portal) {
		this.portal = portal;
		particleEffect = getParticleEffect(portal.getComponent(EntityFaction.class));
	}
	public boolean dead = false;
	public void begin() {
		dead = false;
		particleEffect.reset();
		particleEffect.start();
	}
	public void update(float delta) {
		particleEffect.update(delta);
	}
	public void draw(SpriteBatch batch) {
		EntityLocation portalLocation = portal.getComponent(EntityLocation.class);
		if (portalLocation != null) {
			particleEffect.setPosition(portalLocation.getCenterX(), portalLocation.getCenterY());
			particleEffect.draw(batch);
		}
	}
	public void switchFaction(EntityFaction faction) {
		particleEffect = getParticleEffect(faction);
	}
}
