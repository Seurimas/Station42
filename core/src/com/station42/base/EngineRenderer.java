package com.station42.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface EngineRenderer {
	public void render(Engine engine, SpriteBatch batch, Rectangle viewport);
}
