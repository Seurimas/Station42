package com.station42.faction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.station42.basic.EntitySprite;
import com.station42.game.Station40Game;
import com.station42.world.World;

public class EntityFaction {
	public final static EntityFaction blue = new EntityFaction("Blue", Color.BLUE);
	public final static EntityFaction red = new EntityFaction("Red", Color.RED);
	Color teamColor;
	String name;
	int score = 50;
	public static void clearScores(int points) {
		blue.score = points;
		red.score = points;
	}
	public static void clearScores() {
		clearScores(50);
	}
	public static EntityFaction getWinner() {
		if (blue.score <= 0 && red.score >= blue.score)
			return red;
		else if (red.score <= 0 && blue.score >= red.score)
			return blue;
		else
			return null;
	}
	private EntityFaction(String name, Color teamColor) {
		this.name = name;
		this.teamColor = teamColor;
	}
	public Color getColor() {
		return teamColor;
	}
	public World getStarting() {
		if (this == blue)
			return World.greenWorld;
		else
			return World.orangeWorld;
	}
	public String getName() {
		return name;
	}
	public ParticleEffect getHackingEffect() {
		if (this == blue) {
			return new ParticleEffect(Station40Game.manager.get("blue_hack", ParticleEffect.class));
		}
		else
			return new ParticleEffect(Station40Game.manager.get("red_hack", ParticleEffect.class));
	}
	public EntitySprite getTurretSprite() {
		Texture sprites = Station40Game.manager.get("sprites.png", Texture.class);
		if (this == blue)
			return new EntitySprite(sprites, 0, 32, 32, 32);
		else
			return new EntitySprite(sprites, 32, 32, 32, 32);
	}
	public EntitySprite getPlayerSprite() {
		Texture sprites = Station40Game.manager.get("sprites.png", Texture.class);
		if (this == blue)
			return new EntitySprite(sprites, 64, 0, 32, 32);
		else
			return new EntitySprite(sprites, 96, 0, 32, 32);
	}
	public void losePoints(int i) {
		score-=i;
	}
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
}
