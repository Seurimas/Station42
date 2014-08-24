package com.station42.player.mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class PlayerControllerMouseStateSetter implements EngineUpdateListener {
	Controller controller;
	Entity centeredEntity;
	Vector3 unprojected = new Vector3();
	Vector3 screen = new Vector3();
	private int horizontalCode;
	private int verticalCode;
	private int leftButtonCode;
	private int rightButtonCode;
	public PlayerControllerMouseStateSetter(Controller controller, Entity entity, 
			int horizontalAxisCode, int verticalAxisCode,
			int leftButtonCode, int rightButtonCode) {
		this.controller = controller;
		centeredEntity = entity;
		horizontalCode = horizontalAxisCode;
		verticalCode = verticalAxisCode;
		this.leftButtonCode = leftButtonCode;
		this.rightButtonCode = rightButtonCode;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityMouseState mouseState = centeredEntity.getComponent(EntityMouseState.class);
		if (mouseState != null) {
			EntityLocation location = centeredEntity.getComponent(EntityLocation.class);
			float baseX = controller.getAxis(horizontalCode);
			float baseY = -controller.getAxis(verticalCode);
//			System.out.println(baseX + "," + baseY);
//			System.out.println(baseX + "," + baseY);
			if (baseX * baseX + baseY * baseY > 0.1f) {
				mouseState.setX(location.getCenterX() + baseX * 128);
				mouseState.setY(location.getCenterY() + baseY * 128);
			}
			mouseState.setLeftDown(controller.getButton(leftButtonCode));
			mouseState.setRightDown(controller.getButton(rightButtonCode));
//			if (mouseState.isLeftDown()) {
//				System.out.println(unprojected);
//			}
		}
	}

}
