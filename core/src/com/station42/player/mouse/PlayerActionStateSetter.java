package com.station42.player.mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.input.MouseAndKeyboardController;

public class PlayerActionStateSetter implements EngineUpdateListener {
	Entity centeredEntity;
	int Q, E;
	public PlayerActionStateSetter(Entity entity, int q, int e) {
		centeredEntity = entity;
		Q = q;
		E = e;
	}
	public PlayerActionStateSetter(Entity player, MouseAndKeyboardController controller) {
		this(player, controller.Q, controller.E);
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityMouseState mouseState = centeredEntity.getComponent(EntityMouseState.class);
		if (mouseState != null) {
			if (Q != -1)
				mouseState.setLeftDown(Gdx.input.isKeyPressed(Q));
			else
				mouseState.setLeftDown(Gdx.input.isButtonPressed(Buttons.LEFT));
			if (E != -1)
				mouseState.setRightDown(Gdx.input.isKeyPressed(E));
			else
				mouseState.setRightDown(Gdx.input.isButtonPressed(Buttons.RIGHT));
//			if (mouseState.isLeftDown()) {
//				System.out.println(unprojected);
//			}
		}
	}

}
