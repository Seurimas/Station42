package com.station42.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.station42.base.Entity;
import com.station42.faction.EntityFaction;
import com.station42.game.Station40Game;

public class World {
	public static final World orangeWorld = new World("Orange", Color.ORANGE);
	public static final World greenWorld = new World("Green", Color.GREEN);
	Color theme;
	String name;
	TextureRegion region;
	TextureRegion wallRegion;
	private World(String name, Color theme) {
		this.name = name;
		this.theme = theme;
	}
	public Color getColor() {
		return theme;
	}
	public static boolean visible(Entity viewer, Entity viewed) {
		World entityWorld = viewed.getComponent(World.class);
		if (viewer == null) {
			return true;
		} else if (viewer.getComponent(World.class) == null) {
			return true;
		} else if (entityWorld == null) {
			return true;
		} else if (entityWorld == viewer.getComponent(World.class)) {
			return true;
		} else {
			return false;
		}
	}
	public static Color getColor(Entity viewed) {
		World entityWorld = viewed.getComponent(World.class);
		if (entityWorld == null) {
			return Color.WHITE;
		} else {
			return entityWorld.theme;
		}
	}
	public World nextWorld() {
		if (this == orangeWorld)
			return greenWorld;
		else if (this == greenWorld)
			return orangeWorld;
		return null;
	}
	public TextureRegion getBackground() {
		if (region == null) {
			if (this == orangeWorld)
				region = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 80, 32, 16, 16);
			else if (this == greenWorld)
				region = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 64, 32, 16, 16);
		}
		return region;
	}
	public EntityFaction getHomeFaction() {
		if (this == orangeWorld)
			return EntityFaction.red;
		else if (this == greenWorld)
			return EntityFaction.blue;
		return null;
	}
}
