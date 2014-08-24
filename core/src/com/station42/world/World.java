package com.station42.world;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
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
	HashMap<Rectangle, Texture> optimizedRegions = new HashMap<Rectangle, Texture>();
	public Texture getBackground(SpriteBatch batch, Rectangle bounds) {
		Rectangle normalBounds = new Rectangle(bounds).setPosition(0, 0);
		if (!optimizedRegions.containsKey(bounds))
		{
			TextureRegion tileRegion = getBackground();
			tileRegion.getTexture().getTextureData().prepare();
			Texture newTexture = new Texture((int)bounds.x, (int)bounds.y, tileRegion.getTexture().getTextureData().getFormat());
			Pixmap newPixmap = newTexture.getTextureData().consumePixmap();
			Pixmap tilePixmap = tileRegion.getTexture().getTextureData().consumePixmap();
			for (int x = 0;x < bounds.width;x+=tileRegion.getRegionWidth()) {
				for (int y = 0;y < bounds.width;y+= tileRegion.getRegionHeight()) {
					newPixmap.drawPixmap(tilePixmap, x, y,
							tileRegion.getRegionX(), tileRegion.getRegionY(), 
							tileRegion.getRegionWidth(), tileRegion.getRegionHeight());
				}
			}
			optimizedRegions.put(normalBounds, newTexture);
		}
		return optimizedRegions.get(normalBounds);
	}
}
