package com.station42.loot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineMessageListener;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.base.Engine.Message;
import com.station42.death.DeathMessage;
import com.station42.faction.EntityFaction;
import com.station42.world.Room;
import com.station42.world.World;

public class LootDropSystem implements EngineUpdateListener {
	@Override
	public void update(Engine engine, float delta) {
		for (Entity portal : engine.getEntitiesWithComponent(LootDrop.class)) {
			LootDrop loot = portal.getComponent(LootDrop.class);
			Room room = portal.getComponent(Room.class);
			World world = portal.getComponent(World.class);
			loot.sinceLast += delta;
			if (loot.sinceLast >= loot.interval) {
				loot.sinceLast -= loot.interval;
				if (LootDrop.random.nextInt(loot.chance) == 0) {
					if (engine.stillSpawned(loot.lastDrop))
						continue;
					switch (LootDrop.random.nextInt(4)) {
					case 0:
						loot.lastDrop = HealthBoost.spawn(engine, room.getCenterX(), room.getCenterY(), world);
						break;
					case 1:
						loot.lastDrop = PointBoost.spawn(engine, room.getCenterX(), room.getCenterY(), world);
						break;
					case 2:
						loot.lastDrop = SpeedBoost.spawn(engine, room.getCenterX(), room.getCenterY(), world);
						break;
					case 3:
						loot.lastDrop = WorldSwap.spawn(engine, room.getCenterX(), room.getCenterY(), world);
						break;
					}
				}
			}
		}
	}

}
