package com.station42.hacking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.game.Station40Game;

public class HackingActionParticles {
	ParticleEffect particleEffect;
	Entity hacker;
	public HackingActionParticles(Entity hacker, ParticleEffect particleEffect) {
		this.particleEffect = particleEffect;
//		particleEffect = Station40Game.manager.get("red_hack", ParticleEffect.class);
		this.hacker = hacker;
//		particleEffect = new ParticleEffect();
//		particleEffect.load(Gdx.files.internal("red_hack"), Gdx.files.internal("./"));
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
		if (!alive())
			return;
		EntityLocation hackerLocation = hacker.getComponent(EntityLocation.class);
		HackingAction hackingAction = hacker.getComponent(HackingAction.class);
		if (hackingAction != null) {
			Entity target = hackingAction.getTarget();
			if (target != null) {
				EntityLocation targetLocation = target.getComponent(EntityLocation.class);
				double radians = Math.atan2(targetLocation.getCenterY() - hackerLocation.getCenterY(),
						targetLocation.getCenterX() - hackerLocation.getCenterX());
				float degrees = (int) ((radians / Math.PI) * 180);
				while (degrees < 0)
					degrees += 360;
				particleEffect.setPosition(hackerLocation.getCenterX(), hackerLocation.getCenterY());
				for (ParticleEmitter emitter : particleEffect.getEmitters()) {
					emitter.getAngle().setHigh(degrees);
				}
				particleEffect.draw(batch);
			}
		}
	}
	public void die() {
		dead = true;
		particleEffect.allowCompletion();
	}
	public boolean alive() {
		return !dead;
	}
}
