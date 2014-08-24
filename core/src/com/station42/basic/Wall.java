package com.station42.basic;

import com.badlogic.gdx.math.Vector2;

public class Wall {
	public final Vector2 start, end;
	public final Vector2 inwards;
	public Wall(Vector2 start, Vector2 end, Vector2 inwards) {
		this.start = start;
		this.end = end;
		this.inwards = inwards;
	}
}
