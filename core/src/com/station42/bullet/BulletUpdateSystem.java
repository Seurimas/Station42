package com.station42.bullet;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;

public class BulletUpdateSystem implements EngineUpdateListener {

	@Override
	public void update(Engine engine, float delta) {
		for (Entity bulletEntity : engine.getEntitiesWithComponent(Bullet.class)) {
			Bullet bullet = bulletEntity.getComponent(Bullet.class);
			bullet.update(delta);
			if (!bullet.isAlive())
				engine.despawnEntity(bulletEntity);
		}
	}

}
