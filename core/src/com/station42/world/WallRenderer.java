package com.station42.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.bullet.Bullet;
import com.station42.optimizations.CroppingHelper;

public class WallRenderer implements EngineRenderer {
	Entity player;
	public WallRenderer(Entity player) {
		this.player = player;
	}

	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		batch.end();
		engine.shapeRenderer.begin(ShapeType.Line);
		for (Entity entity : engine.getEntitiesWithComponent(Wall.class)) {
			Wall wall = entity.getComponent(Wall.class);
			World world = entity.getComponent(World.class);
			Room room = wall.room;
//			if (world != null) {
//				TextureRegion wallRegion = world.getWalls();
//				for (int x = (int) wall.start.x;x <= wall.end.x;x+=wallRegion.getRegionWidth()) {
//					for (int y = (int) wall.start.x;y <= wall.end.x;y+=wallRegion.getRegionHeight()) {
//						batch.draw(wallRegion, x, y);
//					}
//				}
//			}
			if (World.visible(player, entity) && CroppingHelper.inView(player.getComponent(EntityLocation.class), room.bounds, viewport)) {
				engine.shapeRenderer.setColor(Color.BLACK);
				engine.shapeRenderer.line(wall.start, wall.end);
			}
		}
		engine.shapeRenderer.end();
		batch.begin();
	}
}
