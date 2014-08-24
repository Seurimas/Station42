package com.station42.hopping;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.player.mouse.EntityMouseState;

public class PlayerHopper implements EngineUpdateListener {
	Entity player;
	public PlayerHopper(Entity player) {
		this.player = player;
	}
	@Override
	public void update(Engine engine, float delta) {
		HoppingAction hoppingAction = player.getComponent(HoppingAction.class);
		EntityMouseState mouseState = player.getComponent(EntityMouseState.class);
		if (hoppingAction != null && mouseState != null) {
			hoppingAction.setWantHopping(mouseState.isRightDown());
		}
	}
}
