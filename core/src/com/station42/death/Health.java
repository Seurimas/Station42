package com.station42.death;

import com.station42.base.Engine;

public class Health {
	private int maxHealth;
	private int currentHealth;
	public Health(int maxHealth) {
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	public int getHealth() {
		return currentHealth;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public boolean damage(Engine engine, int i) {
		if (currentHealth <= 0)
			return false;
		currentHealth -= i;
		return currentHealth <= 0;
	}
	public boolean heal(int i) {
		if (currentHealth > maxHealth)
			return false;
		currentHealth += i;
		if (currentHealth > maxHealth) {
			currentHealth = maxHealth;
		}
		return currentHealth >= maxHealth;
	}
}
