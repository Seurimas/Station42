package com.station42.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.world.World;
import com.sun.prism.GraphicsPipeline.ShaderType;

public class RoomRenderer implements EngineRenderer {
	Entity player;
	public RoomRenderer(Entity player) {
		this.player = player;
	}

	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
//		batch.end();
//		engine.shapeRenderer.begin(ShapeType.Filled);
		for (Entity entity : engine.getEntitiesWithComponent(Room.class)) {
			Room room = entity.getComponent(Room.class);
			World entityWorld = entity.getComponent(World.class);
			if (World.visible(player, entity)) {
				TextureRegion region =  entityWorld.getBackground();
				for (int x = 0;x < room.bounds.width;x+=region.getRegionWidth()) {
					for (int y = 0;y < room.bounds.width;y+= region.getRegionHeight()) {
						batch.draw(region, room.bounds.x + x, room.bounds.y + y);
					}
				}
//				if (entityWorld == null)
//					engine.shapeRenderer.setColor(Color.GRAY);
//				else
//					engine.shapeRenderer.setColor(entityWorld.getColor());
//				engine.shapeRenderer.rect(room.bounds.x, room.bounds.y,
//						room.bounds.width, room.bounds.height);
			}
		}
//		engine.shapeRenderer.end();
//		batch.begin();
	}

}
