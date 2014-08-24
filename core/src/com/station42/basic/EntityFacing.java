package com.station42.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityFacing {
	float facing = 0;
	public EntityFacing() {
	}
	public float getRotation() {
		return facing;
	}
	public void setRotation(float i) {
		facing = i;
	}

}
