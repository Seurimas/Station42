package com.station42.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;

public class PlayerTrackingCameraSystem implements EngineUpdateListener {
	Entity playerEntity;
	OrthographicCamera camera;
	public PlayerTrackingCameraSystem(OrthographicCamera camera, Rectangle viewport, Entity entity) {
		this.camera = camera;
		playerEntity = entity;
	}
	@Override
	public void update(Engine engine, float delta) {
		EntityLocation entityPoint = playerEntity.getComponent(EntityLocation.class);
		camera.position.set((int)entityPoint.getCenterX(), (int)entityPoint.getCenterY(), 1);
		camera.update();
	}
}
