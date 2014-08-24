package com.station42.player.move;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.input.MouseAndKeyboardController;

public class PlayerMoveStateSetter implements EngineUpdateListener {
	Entity centeredEntity;
	int W, A, S, D;
	public PlayerMoveStateSetter(Entity entity) {
		this(entity, Keys.W, Keys.A, Keys.S, Keys.D);
	}
	public PlayerMoveStateSetter(Entity entity, int w, int a, int s, int d) {
		centeredEntity = entity;
		this.W = w;
		A = a;
		S = s;
		D = d;
	}
	public PlayerMoveStateSetter(Entity player, MouseAndKeyboardController controller) {
		this(player, controller.W, controller.A, controller.S, controller.D);
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityMoveState moveState = centeredEntity.getComponent(EntityMoveState.class);
		if (moveState != null) {
			float moveValue = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) ? 0.5f : 1;
			moveValue = Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) ? 0.5f : moveValue;
			moveState.setUp(Gdx.input.isKeyPressed(W) ? moveValue : 0);
			moveState.setLeft(Gdx.input.isKeyPressed(A) ? moveValue : 0);
			moveState.setDown(Gdx.input.isKeyPressed(S) ? moveValue : 0);
			moveState.setRight(Gdx.input.isKeyPressed(D) ? moveValue : 0);
		}
//		System.out.println(moveState);
	}

}
