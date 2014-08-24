package com.station42.death;

import com.badlogic.gdx.math.Vector2;
import com.station42.base.Entity;
import com.station42.base.Engine.Message;

public class BulletDamageMessage implements Message {
	Entity shooter;
	Entity target;
	public BulletDamageMessage(Entity shooter, Entity target, Vector2 collisionPoint) {
		this.shooter = shooter;
		this.target = target;
	}
}