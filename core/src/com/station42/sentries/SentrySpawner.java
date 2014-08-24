package com.station42.sentries;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityFacing;
import com.station42.basic.EntityLocation;
import com.station42.basic.EntitySprite;
import com.station42.faction.EntityFaction;
import com.station42.game.Station40Game;
import com.station42.hacking.Hackable;
import com.station42.optimizations.RoomResident;

public final class SentrySpawner {
	public static Entity spawnSentry(Engine engine, float x, float y, EntityFaction team) {
		Entity sentry = new Entity("Sentry",
				new Sentry(0.25f),
				new RoomResident(),
				new EntityFacing(),
				new Hackable("Turncoat", 80, 1f),
				new EntityLocation(x - 16, y - 16, 32), 
				team != null ? team.getTurretSprite() : getNeutralTurretSprite());
		if (team != null) {
			sentry.setComponent(EntityFaction.class, team);
		}
		engine.spawnEntity(sentry);
		return sentry;
	}

	private static EntitySprite getNeutralTurretSprite() {
		return new EntitySprite(Station40Game.manager.get("sprites.png", Texture.class), 96, 32, 32, 32);
	}
}
