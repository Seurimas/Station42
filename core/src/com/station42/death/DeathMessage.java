package com.station42.death;

import com.station42.base.Entity;
import com.station42.base.Engine.Message;

public class DeathMessage implements Message {
	public Entity killer, deceased;
	public DeathMessage(Entity shooter, Entity target) {
		killer = shooter;
		deceased = target;
	}

}
