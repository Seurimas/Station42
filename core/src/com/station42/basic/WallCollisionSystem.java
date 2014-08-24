package com.station42.basic;

import com.badlogic.gdx.math.Intersector;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;

public class WallCollisionSystem implements EngineUpdateListener {

	@Override
	public void update(Engine engine, float delta) {
		for (Entity wallEntity : engine.getEntitiesWithComponent(Wall.class)) {
			Wall wall = wallEntity.getComponent(Wall.class);
			for (Entity movingEntity : engine.getEntitiesWithComponent(EntityLocation.class)) {
				EntityLocation location = movingEntity.getComponent(EntityLocation.class);
				float distanceToWall = Intersector.distanceLinePoint(wall.start, wall.end, location.getCenter());
				distanceToWall -= location.getRadius();
				if (distanceToWall < 0 && Intersector.pointLineSide(wall.start, wall.end, location.getCenter()) == -1) {
					location.applyVelocity(-distanceToWall * wall.inwards.x, -distanceToWall * wall.inwards.y);
				}
			}
		}
	}

}
