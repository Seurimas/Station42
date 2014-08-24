package com.station42.sentries;

public class Sentry {
	float lastFire = 0;
	public float fireSpeed;
	public float trackingDistance = 64;
	public Sentry(float fireSpeed) {
		this.fireSpeed = fireSpeed;
	}
}
