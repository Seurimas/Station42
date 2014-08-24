package com.station42.basic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.world.World;

public class EntityRenderer implements EngineRenderer {
	Entity player;
	public EntityRenderer(Entity player) {
		this.player = player;
	}

	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		for (Entity entity : engine.getEntitiesWithComponent(EntitySprite.class)) {
			EntityLocation location = entity.getComponent(EntityLocation.class);
			if (location != null && World.visible(player, entity)) {
				EntitySprite sprite = entity.getComponent(EntitySprite.class);
				EntityFacing facing = entity.getComponent(EntityFacing.class);
				if (facing == null)
					batch.draw(sprite.region, location.getLeftX(), location.getBottomY());
				else
					batch.draw(sprite.region, location.getLeftX(), location.getBottomY(),
							location.getCenterX() - location.getLeftX(), location.getCenterY() - location.getBottomY(), 
							sprite.region.getRegionWidth(), sprite.region.getRegionWidth(), 
							1, 1, facing.getRotation(), false);
			}
		}
	}

}
