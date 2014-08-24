package com.station42.player.mouse;

import com.badlogic.gdx.math.Vector2;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.bullet.Bullet;
import com.station42.bullet.BulletSpawner;

public class PlayerGunSystem implements EngineUpdateListener {
	Entity centeredEntity;
	float lastFire = 0;
	public PlayerGunSystem(Entity entity) {
		centeredEntity = entity;
	}
	@Override
	public void update(Engine engine, float delta) {
		lastFire += delta;
		if (lastFire >= 0.25) {
			EntityLocation location = centeredEntity.getComponent(EntityLocation.class);
			EntityMouseState mouseState = centeredEntity.getComponent(EntityMouseState.class);
			if (location != null && mouseState != null && mouseState.isLeftDown()) {
				Entity bullet = BulletSpawner.spawnBullet(centeredEntity, mouseState.getX(), mouseState.getY(), 512, 1);
				engine.spawnEntity(bullet);
				lastFire = 0;
//				Vector2 velocity = new Vector2(mouseState.getX(), mouseState.getY());
//				velocity.sub(location.getCenter());
//				velocity.nor().scl(256);
//				engine.spawnedEntity(new Entity("Bullet", new Bullet(location.getCenter(), velocity, centeredEntity)));
//				lastFire = 0;
			}
		}
	}

}
