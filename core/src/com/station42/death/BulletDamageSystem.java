package com.station42.death;

import com.station42.base.Engine;
import com.station42.base.EngineMessageListener;
import com.station42.base.Engine.Message;

public class BulletDamageSystem implements EngineMessageListener {
	@Override
	public void receiveMessage(Engine engine, Message message) {
		if (message instanceof BulletDamageMessage) {
			BulletDamageMessage bulletDamage = (BulletDamageMessage) message;
			Health targetHealth = bulletDamage.target.getComponent(Health.class);
			if (targetHealth != null) {
				if (targetHealth.damage(engine, 1)) {
					engine.handleMessage(new DeathMessage(bulletDamage.shooter, bulletDamage.target));
				}
			}
		}
	}
}
