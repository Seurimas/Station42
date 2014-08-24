package com.station42.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.OrderedMap;
import com.station42.base.Engine;
import com.station42.faction.EntityFaction;
import com.station42.input.MouseAndKeyboardController;

public class Station40Game extends Game {
	public static interface MainMenuGetter {
		public Screen getScreen(SpriteBatch batch);
	}
	public static Station40Game instance;
	public static AssetManager manager = new AssetManager();
	static SpriteBatch batch;
	static MainMenuGetter getter;
//	public static boolean server;
	public static OrderedMap<Controller, EntityFaction> playerSelects = new OrderedMap<Controller, EntityFaction>();
	public Station40Game(MainMenuGetter getter) {
//		Station40Game.server = server;
		instance = this;
		Station40Game.getter = getter;
	}
	
	public static Actor getTitleActor() {
		return new Actor() {
			TextureRegion region = new TextureRegion(Station40Game.manager.get("sprites.png", Texture.class), 0, 80, 128, 24);
			{
				this.setSize(region.getRegionWidth(), region.getRegionHeight());
			}
			public void draw(Batch batch, float parentAlpha) {
				batch.draw(region, this.getCenterX() - region.getRegionWidth() / 2, this.getCenterY() - region.getRegionHeight() / 2);
			}
		};
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager.load("sprites.png", Texture.class);
		manager.load("red_hack", ParticleEffect.class);
		manager.load("blue_hack", ParticleEffect.class);
		manager.load("red_portal", ParticleEffect.class);
		manager.load("blue_portal", ParticleEffect.class);
		manager.load("white_portal", ParticleEffect.class);
		manager.load("data/uiskin.json", Skin.class);
		manager.load("sounds/Laser_Shoot97.wav", Sound.class);
		manager.load("sounds/Powerup68.wav", Sound.class);
		manager.load("sounds/Powerup50.wav", Sound.class);
	}

	public static Texture getSprites() {
		return manager.get("sprites.png", Texture.class);
	}

	@Override
	public void render () {
		if (manager.update()) {
			if (getScreen() == null) {
				mainMenu();
//				mainScreen = new GameScreen(batch, players);
//				this.setScreen(mainScreen);
			}
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if (this.getScreen() != null)
				this.getScreen().render(Gdx.graphics.getDeltaTime());
		} else {
//			System.out.println(manager.getProgress());
		}
	}
	public static void startGame() {
		instance.setScreen(new GameScreen(batch, playerSelects));
	}
	public static void mainMenu() {
		instance.setScreen(getter.getScreen(batch));
	}

	public static void addPlayer(EntityFaction entityFaction,
			Controller selected) {
		if (selected != MouseAndKeyboardController.None && selected != null)
			playerSelects.put(selected, entityFaction);
	}
}
