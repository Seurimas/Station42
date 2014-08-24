package com.station42.bullet;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
//import com.station40.server.BulletRequest;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.game.Station40Game;
import com.station42.world.World;

public final class BulletSpawner {
	static Vector2 temp = new Vector2();
	public static Entity spawnBullet(Entity shooter, Vector2 target, float velocity, float lifetime) {
		EntityLocation shooterLocation = shooter.getComponent(EntityLocation.class);
		if (shooterLocation != null) {
			Vector2 velocityVector = new Vector2(target);
			velocityVector.sub(shooterLocation.getCenter());
			velocityVector.nor().scl(velocity);
			Station40Game.manager.get("sounds/Laser_Shoot97.wav", Sound.class).play(0.5f);
			Entity bulletEntity = new Entity("Bullet", new Bullet(shooterLocation.getCenter(), velocityVector, shooter));
			bulletEntity.setComponent(World.class, shooter.getComponent(World.class));
			return bulletEntity;
		}
		return null;
	}

	public static Entity spawnBullet(Entity centeredEntity, float x, float y, float vel, float life) {
		return spawnBullet(centeredEntity, temp.set(x, y), vel, life);
	}

//	public static Entity requestSpawnBullet(Entity centeredEntity, float x, float y, float vel, float life) {
//		Entity baseSpawnedBullet = spawnBullet(centeredEntity, temp.set(x, y), vel, life);
//		baseSpawnedBullet.setComponent(BulletRequest.class, baseSpawnedBullet);
//		return baseSpawnedBullet;
//	}
}
