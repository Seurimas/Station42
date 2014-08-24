package com.station42.death;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineMessageListener;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.base.Engine.Message;
import com.station42.basic.EntityLocation;
import com.station42.world.Room;
import com.station42.world.World;

public class PlayerRespawnSystem implements EngineMessageListener, EngineRenderer, EngineUpdateListener {
	Entity respawnedPlayer;
	public PlayerRespawnSystem(Entity player) {
		respawnedPlayer = player;
	}
	@Override
	public void receiveMessage(Engine engine, Message message) {
		if (message instanceof DeathMessage) {
			DeathMessage death = (DeathMessage) message;
			if (death.deceased == respawnedPlayer) {
				engine.despawnEntity(respawnedPlayer);
				Health health = respawnedPlayer.getComponent(Health.class);
				if (health != null)
					health.heal(999);
				ttl = 1;
				playerDead = true;
			}
		}
	}
	float ttl = 1;
	boolean playerDead = true;
	@Override
	public void update(Engine engine, float delta) {
		ttl -= delta;
		if (playerDead && ttl <= 0) {
			EntityLocation location = respawnedPlayer.getComponent(EntityLocation.class);
			if (location != null) {
				for (Entity spawnRoom : engine.getEntitiesWithComponent(SpawnRoom.class)) {
					if (spawnRoom.getComponent(SpawnRoom.class).canSpawn(respawnedPlayer)) {
						Room room = spawnRoom.getComponent(Room.class);
						location.setCenter(room.getCenterX(), room.getCenterY());
						respawnedPlayer.setComponent(World.class, spawnRoom.getComponent(World.class));
						break;
					}
				}
			}
			engine.spawnEntity(respawnedPlayer);
			playerDead = false;
		}
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
	}
	
}
