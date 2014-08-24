package com.station42.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntitySprite {
	TextureRegion region;
	public EntitySprite(Texture texture, int xOffset, int yOffset, int width, int height) {
		region = new TextureRegion(texture, xOffset, yOffset, width, height);
	}
	public TextureRegion getRegion() {
		return region;
	}

}
