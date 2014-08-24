package com.station42.sentries;

import com.badlogic.gdx.math.Vector2;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityFacing;
import com.station42.basic.EntityLocation;
import com.station42.bullet.BulletSpawner;
import com.station42.faction.EntityFaction;
import com.station42.game.ScoringPortal;
import com.station42.world.World;

public class FactionSentrySystem implements EngineUpdateListener {

	@Override
	public void update(Engine engine, float delta) {
		for (Entity sentryEntity : engine.getEntitiesWithComponent(Sentry.class)) {
			Sentry sentry = sentryEntity.getComponent(Sentry.class);
			sentry.lastFire += delta;
			if (sentry.lastFire >= sentry.fireSpeed) {
				Vector2 target = getNearestEnemy(engine, sentryEntity);
				if (target != null) {
					Entity bullet = BulletSpawner.spawnBullet(sentryEntity, target, 256, 1);
					engine.spawnEntity(bullet);
					sentry.lastFire = 0;
					EntityFacing facing = sentryEntity.getComponent(EntityFacing.class);
					if (facing != null) {
						EntityLocation sentryLocation = sentryEntity.getComponent(EntityLocation.class);
						facing.setRotation((float) (Math.atan2(-target.y + sentryLocation.getCenterY(), 
								-target.x + sentryLocation.getCenterX()) / Math.PI * 180));
					}
				}
			}
		}
	}

//	private Entity getNearestEnemy(Engine engine, Entity sentryEntity) {
	private Vector2 getNearestEnemy(Engine engine, Entity sentryEntity) {
		Sentry sentry = sentryEntity.getComponent(Sentry.class);
		EntityFaction sentryFaction = sentryEntity.getComponent(EntityFaction.class);
		EntityLocation sentryLocation = sentryEntity.getComponent(EntityLocation.class);
		if (sentryLocation == null)
			return null;
//		Entity nearestTarget = null;
		Vector2 nearestTarget = null;
		float distanceToNearest = sentry.trackingDistance;
		for (Entity possibleTarget : engine.getEntitiesWithComponent(EntityFaction.class)) {
			if (sentryEntity == possibleTarget || possibleTarget.getComponent(Sentry.class) != null || possibleTarget.getComponent(ScoringPortal.class) != null)
				continue;
			EntityFaction targetFaction = possibleTarget.getComponent(EntityFaction.class);
			if (targetFaction != sentryFaction && World.visible(sentryEntity, possibleTarget)) {
				EntityLocation targetLocation = possibleTarget.getComponent(EntityLocation.class);
				if (targetLocation != null) {
					float distance = targetLocation.getDistance(sentryLocation);
					if (distance < distanceToNearest) {
//						nearestTarget = possibleTarget;
						nearestTarget = targetLocation.getCenter();
						distanceToNearest = distance;
					}
				}
			}
		}
		return nearestTarget;
	}
	
}
