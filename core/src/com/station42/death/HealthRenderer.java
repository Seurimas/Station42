package com.station42.death;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.world.World;

public class HealthRenderer implements EngineRenderer {
	Entity player;
	TextureRegion region;
	public HealthRenderer(Entity player, TextureRegion region) {
		this.player = player;
		this.region = region;
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		for (Entity entity : engine.getEntitiesWithComponent(Health.class)) {
			EntityLocation location = entity.getComponent(EntityLocation.class);
			if (location != null && World.visible(player, entity)) {
				Health health = entity.getComponent(Health.class);
				for (int i = 0;i < health.getHealth();i++) {
					batch.draw(region, location.getLeftX() + (region.getRegionWidth() - 1) * i, location.getBottomY() - region.getRegionHeight());
				}
			}
		}
	}

}
