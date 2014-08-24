package com.station42.hacking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.faction.EntityFaction;

public class HackingActionRenderer implements EngineRenderer, EngineUpdateListener {
	public HackingActionRenderer() {
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
//		batch.end();
		for (Entity entity : engine.getEntitiesWithComponent(HackingAction.class)) {
			HackingAction hackingAction = entity.getComponent(HackingAction.class);
			HackingActionParticles particles = entity.getComponent(HackingActionParticles.class);
			if (hackingAction.getHackingTime() != 0) {
				if (particles == null) {
					particles = new HackingActionParticles(entity, entity.getComponent(EntityFaction.class).getHackingEffect());
					particles.begin();
//					System.out.println("Beginning new!");
					entity.setComponent(HackingActionParticles.class, particles);
				} else if (!particles.alive() && hackingAction.getHackingWanted()) {
					particles.begin();
//					System.out.println("Beginning old!");
				}
			} else {
				if (particles != null && particles.alive()) {
					particles.die();
//					System.out.println("Killing old!");
				}
			}
		}
		for (Entity entity : engine.getEntitiesWithComponent(HackingActionParticles.class)) {
			HackingActionParticles particles = entity.getComponent(HackingActionParticles.class);
			particles.draw(batch);
		}
//		batch.begin();
	}
	@Override
	public void update(Engine engine, float delta) {
		for (Entity entity : engine.getEntitiesWithComponent(HackingActionParticles.class)) {
			HackingActionParticles particles = entity.getComponent(HackingActionParticles.class);
			particles.update(delta);
		}
	}
}
