package com.station42.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;
//import com.station40.server.ProtocolSetup;
//import com.station40.server.Station40Client;
//import com.station40.server.Station40Server;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.basic.EntityFacing;
import com.station42.basic.EntityLocation;
import com.station42.basic.EntityRenderer;
import com.station42.basic.EntitySprite;
import com.station42.bullet.BulletCollisionSystem;
import com.station42.bullet.BulletRenderer;
import com.station42.bullet.BulletUpdateSystem;
import com.station42.death.BulletDamageSystem;
import com.station42.death.Health;
import com.station42.death.HealthRenderer;
import com.station42.death.PlayerRespawnSystem;
import com.station42.faction.EntityFaction;
import com.station42.hacking.Hackable;
import com.station42.hacking.HackingAction;
import com.station42.hacking.HackingActionRenderer;
import com.station42.hacking.HackingActionUpdater;
import com.station42.hacking.HackingSystem;
import com.station42.hacking.HackingUI;
import com.station42.hacking.PlayerHacker;
import com.station42.hopping.HoppingAction;
import com.station42.hopping.HoppingSystem;
import com.station42.hopping.HoppingUI;
import com.station42.hopping.OffensiveHackingSystem;
import com.station42.hopping.PlayerHopper;
import com.station42.input.MouseAndKeyboardController;
import com.station42.loot.LootDropSystem;
import com.station42.loot.LootPickupSystem;
import com.station42.loot.Looter;
import com.station42.optimizations.RoomResident;
import com.station42.optimizations.RoomResidentSystem;
import com.station42.player.PlayerTrackingCameraSystem;
import com.station42.player.mouse.EntityMouseState;
import com.station42.player.mouse.PlayerActionStateSetter;
import com.station42.player.mouse.PlayerControllerMouseStateSetter;
import com.station42.player.mouse.PlayerGunSystem;
import com.station42.player.mouse.PlayerMouseStateSetter;
import com.station42.player.move.EntityMoveState;
import com.station42.player.move.PlayerControllerMoveStateSetter;
import com.station42.player.move.PlayerMoveStateSetter;
import com.station42.player.move.PlayerWalker;
import com.station42.sentries.FactionSentrySystem;
import com.station42.sentries.Sentry;
import com.station42.sentries.SentryHackingSystem;
import com.station42.sentries.SentrySpawner;
import com.station42.world.Room;
import com.station42.world.RoomRenderer;
import com.station42.world.Wall;
import com.station42.world.WallCollisionSystem;
import com.station42.world.WallRenderer;
import com.station42.world.WorldUI;

public class GameScreen implements Screen {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Engine engine;
	OrderedMap<Rectangle, OrthographicCamera> cameras = new OrderedMap<Rectangle, OrthographicCamera>();
	MatchSystem matchSystem;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	public static final Rectangle fullViewport = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//	Station40Server server;
//	Station40Client client;
	public GameScreen(SpriteBatch batch, OrderedMap<Controller, EntityFaction> playerSelects) {
		this.batch = batch;
		shapeRenderer = new ShapeRenderer();
		engine = new Engine(batch, shapeRenderer);
//		if (!Station40Game.server) {
//		for (Controller controller : Controllers.getControllers())
		setupPlayers(playerSelects);
//			client = new Station40Client(player);
//			engine.addSystem(client);
//		} else {
//			server = new Station40Server();
//			engine.addSystem(server);
//		}
		ScoringPortalRenderer portalRenderer = new ScoringPortalRenderer(null);
		engine.addSystem(new BulletCollisionSystem());
		engine.addSystem(new BulletUpdateSystem());
		engine.addSystem(new FactionSentrySystem());
		engine.addSystem(new HoppingSystem());
		engine.addSystem(new HackingSystem());
		engine.addSystem(new SentryHackingSystem());
		engine.addSystem(new OffensiveHackingSystem());
		engine.addSystem(new ScoringPortalHackingSystem());
		engine.addSystem(new HackingActionUpdater());
		engine.addSystem(new WallCollisionSystem());
		engine.addSystem(new LootDropSystem());
		engine.addSystem(new RoomResidentSystem());
		engine.addSystem(new LootPickupSystem());
		engine.addSystem(portalRenderer);
		HackingActionRenderer hackingActionRenderer = new HackingActionRenderer();
		matchSystem = new MatchSystem(new BitmapFont());
		engine.addSystem(hackingActionRenderer);
		engine.addSystem(matchSystem);
		engine.addRenderer(fullViewport, matchSystem);
		engine.addMessageListener(matchSystem);
		engine.addMessageListener(new BulletDamageSystem());
		setupLevel();
		Station40Game.mainTheme = Station40Game.manager.get("sounds/main_theme.wav", Music.class);
		Station40Game.mainTheme.setLooping(true);
		Station40Game.mainTheme.play();
		OrthographicCamera mainCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		mainCamera.lookAt(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
		cameras.put(fullViewport, mainCamera);
		for (Rectangle viewport : cameras.keys()) {
			if (viewport != fullViewport)
				engine.addRenderer(viewport, hackingActionRenderer);
		}
	}

	private void setupLevel() {
		new Levels().setupLevel(engine);
	}

	private void setupPlayers(OrderedMap<Controller, EntityFaction> playerSelects) {
		for (Controller controller : playerSelects.keys()) {
			if (controller != MouseAndKeyboardController.None) { 
				Rectangle viewport;
				int i = cameras.size; // Player count
				if (playerSelects.size == 2) {
					viewport = new Rectangle((i % 2) * SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
				} else {
					viewport = new Rectangle((i % 2) * SCREEN_WIDTH / 2, ((3 - i) / 2) * SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
				}
				OrthographicCamera camera = setupPlayer(playerSelects.get(controller), viewport, controller);
				if (playerSelects.size == 2) {
					camera.viewportHeight *= 2;
				}
				cameras.put(viewport, camera);
			}
		}
	}

	

	private OrthographicCamera setupPlayer(EntityFaction faction, Rectangle viewport, Controller controller) {
		Texture sprites = Station40Game.manager.get("sprites.png", Texture.class);
		OrthographicCamera camera = new OrthographicCamera(400, 300);
		Entity player = new Entity("Player",
				new EntityLocation(-16, -16, 32), 
				new EntityMouseState(), 
				new EntityMoveState(),
				new EntityFacing(),
				new Hackable("Force Hop", 25, 1f),
				new Health(5),
				new HackingAction(),
				new HoppingAction(),
				new Looter(),
				new RoomResident(),
				faction,
				faction.getPlayerSprite(),
				faction.getStarting());
		engine.addRenderer(viewport, new RoomRenderer(player));
		if (!(controller instanceof MouseAndKeyboardController))
			engine.addSystem(new PlayerControllerMouseStateSetter(controller, player, 
					4, 3, Ouya.isRunningOnOuya() ? Ouya.BUTTON_O : 5, Ouya.isRunningOnOuya() ? Ouya.BUTTON_A : 6));
		else
			engine.addSystem(new PlayerMoveStateSetter(player, (MouseAndKeyboardController) controller));
		
		if (!(controller instanceof MouseAndKeyboardController))
			engine.addSystem(new PlayerControllerMoveStateSetter(controller, player, Ouya.isRunningOnOuya() ? Ouya.AXIS_LEFT_X : 1, Ouya.isRunningOnOuya() ? Ouya.AXIS_LEFT_Y : 0));
		else
			engine.addSystem(new PlayerActionStateSetter(player, (MouseAndKeyboardController) controller));
//			engine.addSystem(new PlayerMouseStateSetter(camera, player));
		
		engine.addSystem(new PlayerTrackingCameraSystem(camera, viewport, player));
//		engine.addSystem(new PlayerGunSystem(player));
		ScoringPortalRenderer portalRenderer = new ScoringPortalRenderer(player);
		PlayerRespawnSystem respawner = new PlayerRespawnSystem(player);
		engine.addMessageListener(respawner);
		engine.addRenderer(viewport, respawner);
		engine.addRenderer(viewport, new EntityRenderer(player));
		engine.addRenderer(viewport, portalRenderer);
		engine.addRenderer(viewport, new BulletRenderer(player));
		engine.addRenderer(viewport, new WallRenderer(player));
		engine.addRenderer(viewport, new HealthRenderer(player, new TextureRegion(sprites, 0, 16, 3, 4)));
		engine.addRenderer(viewport, new HackingUI(player, new BitmapFont()));
		engine.addRenderer(viewport, new HoppingUI(player, new BitmapFont()));
		engine.addRenderer(viewport, new WorldUI(player, new BitmapFont()));
		engine.addSystem(respawner);
		engine.addSystem(new PlayerWalker(player));
		engine.addSystem(new PlayerHacker(player));
		engine.addSystem(new PlayerHopper(player));
//		engine.spawnEntity(player);
		return camera;
	}

	@Override
	public void render(float delta) {
		engine.update(delta);
		for (Rectangle splitScreen : cameras.keys()) {
			Rectangle viewport = splitScreen;
			if (viewport.getHeight() != 0) {
				Gdx.gl.glViewport((int)(viewport.x / SCREEN_WIDTH * Gdx.graphics.getWidth()), 
						(int)(viewport.y / SCREEN_HEIGHT * Gdx.graphics.getHeight()), 
						(int)(viewport.width / SCREEN_WIDTH * Gdx.graphics.getWidth()),
						(int)(viewport.height / SCREEN_HEIGHT * Gdx.graphics.getHeight()));
				engine.render(batch, cameras.get(splitScreen), viewport);
			}
		}
//		engine.render(batch, delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
