package com.station42.player.move;

import com.badlogic.gdx.math.Intersector;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityFacing;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;

public class PlayerWalker implements EngineUpdateListener {
	Entity centeredEntity;
	public PlayerWalker(Entity entity) {
		centeredEntity = entity;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityLocation aabb = centeredEntity.getComponent(EntityLocation.class);
		EntityMoveState moveState = centeredEntity.getComponent(EntityMoveState.class);
		if (aabb != null && moveState != null) {
			aabb.applyVelocity(moveState.getHorizontal() * 2, moveState.getVertical() * 2);
			EntityFacing facing = centeredEntity.getComponent(EntityFacing.class);
			if (facing != null) {
				double radians = Math.atan2(moveState.getVertical(), moveState.getHorizontal());
				facing.setRotation(180 + (float) (radians / Math.PI * 180));
			}
		}
//		System.out.println(aabb);
	}

}
