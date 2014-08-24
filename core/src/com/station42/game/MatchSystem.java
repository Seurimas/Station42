package com.station42.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.station42.base.Engine;
import com.station42.base.EngineMessageListener;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.base.Engine.Message;
import com.station42.death.DeathMessage;
import com.station42.faction.EntityFaction;

public class MatchSystem implements EngineMessageListener, EngineRenderer,
		EngineUpdateListener {
	BitmapFont font;
	EntityFaction winner;
	float pointInterval = 5f;
	float lastPointChange = 0f;
	float winnerDuration = 5f;
	private static final TextureRegion RED_DOT = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 10, 64, 5, 5);
	private static final TextureRegion BLUE_DOT = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 15, 64, 5, 5);
	private static final TextureRegion WHITE_DOT = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 20, 64, 5, 5);
	public MatchSystem(BitmapFont font) {
		this.font = font;
	}
	@Override
	public void update(Engine engine, float delta) {
		if (winner != null) {
			winnerDuration -= delta;
			if (winnerDuration <= 0) {
				Station40Game.mainMenu();
				EntityFaction.clearScores();
			}
		}
		lastPointChange += delta;
		if (lastPointChange >= pointInterval) {
			lastPointChange -= pointInterval;
			int redCount = 0;
			int blueCount = 0;
			for (Entity portal : engine.getEntitiesWithComponent(ScoringPortal.class)) {
				EntityFaction faction = portal.getComponent(EntityFaction.class);
				if (faction == EntityFaction.red)
					redCount++;
				else if (faction == EntityFaction.blue)
					blueCount++;
			}
			int redLead = redCount - blueCount;
			if (redLead > 0)
				EntityFaction.blue.losePoints(redLead);
			else if (redLead < 0)
				EntityFaction.red.losePoints(-redLead);
			checkScore();
		}
	}

	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
		int redCount = 0;
		int blueCount = 0;
		int whiteCount = 0;
		for (Entity portal : engine.getEntitiesWithComponent(ScoringPortal.class)) {
			EntityFaction faction = portal.getComponent(EntityFaction.class);
			if (faction == EntityFaction.red)
				redCount++;
			else if (faction == EntityFaction.blue)
				blueCount++;
			else
				whiteCount++;
		}
		batch.end();
		engine.shapeRenderer.begin(ShapeType.Filled);
		engine.shapeRenderer.setColor(Color.GRAY);
		engine.shapeRenderer.rect(-35, viewport.height / 2 - 40, 70, 40);
		engine.shapeRenderer.end();
		batch.begin();
		if (winner == null) {
			font.setColor(Color.RED);
			font.draw(batch, "" + EntityFaction.red.getScore(), -30, viewport.height / 2);
			font.setColor(Color.BLUE);
			font.draw(batch, "" + EntityFaction.blue.getScore(), 20, viewport.height / 2);
			font.setColor(Color.WHITE);
			font.draw(batch, "Portals", -20, viewport.height / 2 - 15);
			font.setColor(Color.BLACK);
			font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), -viewport.width / 2, viewport.height / 2);
			for (int i = 0;i < whiteCount;i++) {
				batch.draw(WHITE_DOT, (i * WHITE_DOT.getRegionWidth()) - (whiteCount * WHITE_DOT.getRegionWidth()) / 2, viewport.height / 2 - 40);
			}
			for (int i = 0;i < redCount;i++) {
				batch.draw(RED_DOT, -(i * RED_DOT.getRegionWidth()) - ((whiteCount + 4) * WHITE_DOT.getRegionWidth()) / 2, viewport.height / 2 - 40);
			}
			for (int i = 0;i < blueCount;i++) {
				batch.draw(BLUE_DOT, (i * BLUE_DOT.getRegionWidth()) + ((whiteCount + 2) * WHITE_DOT.getRegionWidth()) / 2, viewport.height / 2 - 40);
			}
		} else {
			font.setColor(winner.getColor());
			font.draw(batch, winner.getName() + " WINS!", -20, -15);
		}
	}

	@Override
	public void receiveMessage(Engine engine, Message message) {
		if (message instanceof DeathMessage) {
			DeathMessage death = (DeathMessage) message;
			if (death.deceased.getComponent(EntityFaction.class) != null) {
				death.deceased.getComponent(EntityFaction.class).losePoints(5);
			}
		}
		checkScore();
	}
	
	private void checkScore() {
		if (winner == null) {
			winner = EntityFaction.getWinner();
			if (winner != null) {
				Station40Game.manager.get("sounds/Powerup68.wav", Sound.class).play(0.5f);
			}
		}
	}

}
