package com.station42.bullet;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.death.BulletDamageMessage;
import com.station42.world.Wall;
import com.station42.world.World;

public class BulletCollisionSystem implements EngineUpdateListener {
	
	@Override
	public void update(Engine engine, float delta) {
		for (Entity bulletEntity : engine.getEntitiesWithComponent(Bullet.class)) {
			Bullet bullet = bulletEntity.getComponent(Bullet.class);
			Entity nearestTarget = null;
			float nearestTimeDistance = delta + 1;
			for (Entity target : engine.getEntitiesWithComponent(EntityLocation.class)) {
				if (!bullet.isShooter(target) && World.visible(bulletEntity, target)) {
					EntityLocation location = target.getComponent(EntityLocation.class);
					float distance = bullet.intersectDurationCircle(location.getCenter(), location.getRadius());
					if (distance != -1 && distance <= delta && distance < nearestTimeDistance) {
						nearestTimeDistance = distance;
						nearestTarget = target;
					}
				}
			}
			for (Entity target : engine.getEntitiesWithComponent(Wall.class)) {
				if (!bullet.isShooter(target) && World.visible(bulletEntity, target)) {
					Wall barrier = target.getComponent(Wall.class);
					float distance = bullet.intersectDurationLine(barrier.start, barrier.end);
					if (distance != -1 && distance <= delta && distance < nearestTimeDistance) {
						nearestTimeDistance = distance;
						nearestTarget = target;
					}
				}
			}
			if (nearestTarget != null) {
//				System.out.println(nearestTarget.name + " HIT!");
				engine.despawnEntity(bulletEntity);
				engine.handleMessage(new BulletDamageMessage(bullet.getShooter(), nearestTarget, null));
			}
		}
	}

}
