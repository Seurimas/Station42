package com.station42.player.mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;

public class PlayerMouseStateSetter implements EngineUpdateListener {
	OrthographicCamera camera;
	Entity centeredEntity;
	Vector3 unprojected = new Vector3();
	Vector3 screen = new Vector3();
	public PlayerMouseStateSetter(OrthographicCamera camera, Entity entity) {
		this.camera = camera;
		centeredEntity = entity;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityMouseState mouseState = centeredEntity.getComponent(EntityMouseState.class);
		if (mouseState != null) {
			screen.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			unprojected = camera.unproject(screen);
			mouseState.setX((int) unprojected.x);
			mouseState.setY((int) unprojected.y);
			mouseState.setLeftDown(Gdx.input.isButtonPressed(Buttons.LEFT));
			mouseState.setRightDown(Gdx.input.isButtonPressed(Buttons.RIGHT));
//			if (mouseState.isLeftDown()) {
//				System.out.println(unprojected);
//			}
		}
	}

}
