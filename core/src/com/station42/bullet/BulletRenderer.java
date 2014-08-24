package com.station42.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.world.World;
import com.sun.prism.GraphicsPipeline.ShaderType;

public class BulletRenderer implements EngineRenderer {
	Entity player;
	public BulletRenderer(Entity player) {
		this.player = player;
	}

	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		batch.end();
		engine.shapeRenderer.begin(ShapeType.Line);
		for (Entity entity : engine.getEntitiesWithComponent(Bullet.class)) {
			Bullet bullet = entity.getComponent(Bullet.class);
			World entityWorld = entity.getComponent(World.class);
			if (World.visible(player, entity)) {
				if (entityWorld == null)
					engine.shapeRenderer.setColor(Color.WHITE);
				else
					engine.shapeRenderer.setColor(entityWorld.getColor());
				engine.shapeRenderer.line(bullet.getCurrentLocation(), bullet.getNextLocation(1 / 30f));
			}
		}
		engine.shapeRenderer.end();
		batch.begin();
	}

}
