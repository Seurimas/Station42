package com.station42.loot;

import com.station42.base.Engine;
import com.station42.base.Entity;

public interface Loot {
	public void onLoot(Engine engine, Entity looter);
}
