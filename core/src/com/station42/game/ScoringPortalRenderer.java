package com.station42.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineRenderer;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.faction.EntityFaction;
import com.station42.world.World;

public class ScoringPortalRenderer implements EngineRenderer, EngineUpdateListener {
	Entity player;
	public ScoringPortalRenderer(Entity player) {
		this.player = player;
	}
	@Override
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport) {
//		batch.end();
		for (Entity entity : engine.getEntitiesWithComponent(ScoringPortal.class)) {
			ScoringPortal scoringPortal = entity.getComponent(ScoringPortal.class);
			if (scoringPortal != null && World.visible(player, entity)) {
				scoringPortal.draw(batch);
			}
		}
//		batch.begin();
	}
	@Override
	public void update(Engine engine, float delta) {
		for (Entity entity : engine.getEntitiesWithComponent(ScoringPortal.class)) {
			ScoringPortal scoringPortal = entity.getComponent(ScoringPortal.class);
			if (scoringPortal != null) {
				scoringPortal.update(delta);
			}
		}
	}
}
