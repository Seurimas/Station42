package com.station42.hacking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.player.mouse.EntityMouseState;

public class PlayerHacker implements EngineUpdateListener {
	Entity player;
	public PlayerHacker(Entity player) {
		this.player = player;
	}
	@Override
	public void update(Engine engine, float delta) {
		HackingAction hackingAction = player.getComponent(HackingAction.class);
		EntityMouseState mouseState = player.getComponent(EntityMouseState.class);
		if (hackingAction != null && mouseState != null) {
			hackingAction.setWantHacking(mouseState.isLeftDown());
		}
	}
}
