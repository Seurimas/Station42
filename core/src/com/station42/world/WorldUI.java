package com.station42.world;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.sun.prism.GraphicsPipeline.ShaderType;

public class WorldUI implements EngineRenderer {
	Entity player;
	BitmapFont font;
	public WorldUI(Entity player, BitmapFont font) {
		this.player = player;
		this.font = font;
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		float ouyaBorder = 0.05f;
		Vector3 newLocation = new Vector3();
		newLocation.set(-64f / viewport.width, -1 + 32f / viewport.height + ouyaBorder, 0);
		newLocation.mul(engine.inverted);
		font.setColor(player.getComponent(World.class).getColor());
		font.draw(batch, "Current world:" + player.getComponent(World.class).name, newLocation.x, newLocation.y);
	}

}
