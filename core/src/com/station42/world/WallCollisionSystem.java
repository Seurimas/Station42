package com.station42.world;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.player.move.EntityMoveState;

public class WallCollisionSystem implements EngineUpdateListener {
	Rectangle temp = new Rectangle();
	@Override
	public void update(Engine engine, float delta) {
		for (Entity wallEntity : engine.getEntitiesWithComponent(Wall.class)) {
			Wall wall = wallEntity.getComponent(Wall.class);
			for (Entity movingEntity : engine.getEntitiesWithComponent(EntityMoveState.class)) {
				EntityLocation location = movingEntity.getComponent(EntityLocation.class);
				float minX = Math.min(wall.start.x, wall.end.x);
				float maxX = Math.max(wall.start.x, wall.end.x);
				float minY = Math.min(wall.start.y, wall.end.y);
				float maxY = Math.max(wall.start.y, wall.end.y);
				if (location.getBox().overlaps(temp.set(minX, minY, maxX - minX, maxY - minY))) {
					float distanceToWall = Intersector.distanceLinePoint(wall.start, wall.end, location.getCenter());
					distanceToWall -= location.getRadius();
					if (distanceToWall < 0 && Intersector.pointLineSide(wall.start, wall.end, location.getCenter()) == -1) {
						location.applyVelocity(-distanceToWall * wall.inwards.x, -distanceToWall * wall.inwards.y);
					}
				}
			}
		}
	}

}
