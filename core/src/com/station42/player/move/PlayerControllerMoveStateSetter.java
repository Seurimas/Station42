package com.station42.player.move;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;

public class PlayerControllerMoveStateSetter implements EngineUpdateListener {
	Entity centeredEntity;
	Controller controller;
	private int horizontalCode;
	private int verticalCode;
	private int leftButtonCode;
	private int rightButtonCode;
	public PlayerControllerMoveStateSetter(Controller controller, Entity entity, 
			int horizontalAxisCode, int verticalAxisCode) {
		this.controller = controller;
		centeredEntity = entity;
		horizontalCode = horizontalAxisCode;
		verticalCode = verticalAxisCode;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityMoveState moveState = centeredEntity.getComponent(EntityMoveState.class);
		if (moveState != null) {
			float right = controller.getAxis(horizontalCode);
			float down = controller.getAxis(verticalCode);
			if (right * right + down * down < 0.1)
			{
				moveState.setUp(0);
				moveState.setDown(0);
				moveState.setLeft(0);
				moveState.setRight(0);
			} else {
				moveState.setUp(down < 0 ? -down : 0);
				moveState.setDown(down > 0 ? down : 0);
				moveState.setLeft(right < 0 ? -right : 0);
				moveState.setRight(right > 0 ? right : 0);
			}
//			float moveValue = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) ? 0.5f : 1;
//			moveValue = Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) ? 0.5f : moveValue;
//			moveState.setUp(Gdx.input.isKeyPressed(Keys.W) ? moveValue : 0);
//			moveState.setLeft(Gdx.input.isKeyPressed(Keys.A) ? moveValue : 0);
//			moveState.setDown(Gdx.input.isKeyPressed(Keys.S) ? moveValue : 0);
//			moveState.setRight(Gdx.input.isKeyPressed(Keys.D) ? moveValue : 0);
		}
//		System.out.println(moveState);
	}

}
