package com.station42.hopping;

import javafx.scene.text.FontBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
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

public class HoppingUI implements EngineRenderer {
	Entity player;
	BitmapFont font;
	public HoppingUI(Entity player, BitmapFont font) {
		this.player = player;
		this.font = font;
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		float ouyaBorder = 0.05f;
		String actionString = "Hop worlds";
		Vector3 newLocation = new Vector3();
		HoppingAction hackingAction = player.getComponent(HoppingAction.class);
		if (hackingAction != null) {
			TextBounds bounds = font.getBounds(actionString);
			newLocation.set(1 - bounds.width * 2 / viewport.width - ouyaBorder, -1 + bounds.height * 2 / viewport.height + ouyaBorder, 0);
			newLocation.mul(engine.inverted);
			font.draw(batch, actionString, newLocation.x, newLocation.y);
			batch.end();
			newLocation.set(1 - ouyaBorder, -1 + (64f / viewport.height) + ouyaBorder, 0);
			newLocation.mul(engine.inverted);
			engine.shapeRenderer.begin(ShapeType.Filled);
			engine.shapeRenderer.setColor(Color.WHITE);
			engine.shapeRenderer.rect(newLocation.x - viewport.width / 2 * hackingAction.getCompletionPercent(), newLocation.y - bounds.height, viewport.width / 2 * hackingAction.getCompletionPercent(), 24);
			engine.shapeRenderer.end();
			batch.begin();
		}
	}

}
