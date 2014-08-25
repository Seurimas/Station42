package com.station42.hacking;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.Entity;
import com.station42.basic.EntitySprite;
import com.sun.prism.GraphicsPipeline.ShaderType;

public class HackingUI implements EngineRenderer {
	Entity player;
	BitmapFont font;
	public HackingUI(Entity player, BitmapFont font) {
		this.player = player;
		this.font = font;
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		float ouyaBorder = 0.05f;
		Vector3 newLocation = new Vector3();
		HackingAction hackingAction = player.getComponent(HackingAction.class);
		if (hackingAction != null) {
			Entity targetEntity = hackingAction.getPreferredTarget(player);
			if (targetEntity != null) {
				EntitySprite sprite = targetEntity.getComponent(EntitySprite.class);
				Hackable hackable = targetEntity.getComponent(Hackable.class);
				if (sprite != null) {
					newLocation.set(-1 + ouyaBorder, -1 + (32f / viewport.height) + ouyaBorder, 0);
					newLocation.mul(engine.inverted);
					batch.draw(sprite.getRegion(), newLocation.x, newLocation.y);
				}
				if (hackable != null) {
					TextBounds bounds = font.getBounds(hackable.description);
					newLocation.set(-1 + ouyaBorder, -1 + (32f / viewport.height) + ouyaBorder, 0);
					newLocation.mul(engine.inverted);
					font.draw(batch, hackable.description, newLocation.x, newLocation.y);
					batch.end();
					newLocation.set(-1 + (64f / viewport.width) + ouyaBorder, -1 + (64f / viewport.height) + ouyaBorder, 0);
					newLocation.mul(engine.inverted);
					engine.shapeRenderer.begin(ShapeType.Filled);
					engine.shapeRenderer.setColor(Color.WHITE);
					engine.shapeRenderer.rect(newLocation.x, newLocation.y - bounds.height, viewport.width / 2 * hackingAction.getCompletionPercent(), 24);
					engine.shapeRenderer.end();
					batch.begin();
				}
			}
		}
	}

}
