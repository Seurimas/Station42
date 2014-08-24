package com.station42.world;

import com.badlogic.gdx.math.Vector2;

public class Wall {
	public final Vector2 start, end;
	public final Vector2 inwards;
	public final Room room;
	public Wall(Room room, Vector2 start, Vector2 end, Vector2 inwards) {
		this.room = room;
		this.start = start;
		this.end = end;
		this.inwards = inwards;
	}
}
