package com.station42.optimizations;

import com.badlogic.gdx.math.Rectangle;
import com.station42.basic.EntityLocation;

public class CroppingHelper {
	static Rectangle temp = new Rectangle();
	public static boolean inView(EntityLocation view, Rectangle other, Rectangle viewport) {
		temp.set(viewport).setCenter(view.getCenter());
		if (other.overlaps(temp))
			return true;
		else
			return false;
	}
}
