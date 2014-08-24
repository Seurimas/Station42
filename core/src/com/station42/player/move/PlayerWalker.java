package com.station42.player.move;

import com.badlogic.gdx.math.Intersector;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityFacing;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;
import com.station42.loot.SpeedBoost;

public class PlayerWalker implements EngineUpdateListener {
	Entity centeredEntity;
	public PlayerWalker(Entity entity) {
		centeredEntity = entity;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityLocation aabb = centeredEntity.getComponent(EntityLocation.class);
		EntityMoveState moveState = centeredEntity.getComponent(EntityMoveState.class);
		int speed = 100;
		if (centeredEntity.getComponent(SpeedBoost.class) != null) {
			speed *= 2;
			centeredEntity.getComponent(SpeedBoost.class).duration -= delta;
			if (centeredEntity.getComponent(SpeedBoost.class).duration <= 0) {
				centeredEntity.setComponent(SpeedBoost.class, null);
			}
		}
		if (aabb != null && moveState != null) {
			aabb.applyVelocity(moveState.getHorizontal() * speed * delta, moveState.getVertical() * speed * delta);
			EntityFacing facing = centeredEntity.getComponent(EntityFacing.class);
			if (facing != null) {
				double radians = Math.atan2(moveState.getVertical(), moveState.getHorizontal());
				facing.setRotation(180 + (float) (radians / Math.PI * 180));
			}
		}
//		System.out.println(aabb);
	}

}
