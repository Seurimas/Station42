package com.station40.game.android;

import java.util.HashMap;

import tv.ouya.console.api.OuyaController;
import tv.ouya.console.api.OuyaInputMapper;

import android.content.Context;

import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.station42.faction.EntityFaction;
import com.station42.game.MainMenuScreen;
import com.station42.game.Station40Game;
import com.station42.game.Station40Game.MainMenuGetter;
import com.station42.input.MouseAndKeyboardController;

public class OuyaMenuScreen implements Screen {
	public static MainMenuGetter getter = new MainMenuGetter() {
		
		@Override
		public Screen getScreen(SpriteBatch batch) {
			return new OuyaMenuScreen(batch);
		}
	};
	Stage stage;
	Table table;
	private Skin skin;
	HashMap<Controller, EntityFaction> activePlayers = new HashMap<Controller, EntityFaction>();
	OuyaPlayerJoiner[] joiners = new OuyaPlayerJoiner[] {
			new OuyaPlayerJoiner(0),
			new OuyaPlayerJoiner(1),
			new OuyaPlayerJoiner(2),
			new OuyaPlayerJoiner(3),
	};
	public OuyaMenuScreen(SpriteBatch batch) {
		stage = new Stage(new FitViewport(800, 600), batch);
		setupSkin();
		OuyaController.init(AndroidLauncher.context);
		table = new Table();
		table.setFillParent(true);
		table.add(Station40Game.getTitleActor()).fill().colspan(4).row();
		for (int i = 0; i < joiners.length;i++)
		{
			table.add(joiners[i]).expand().fill();
		}
		table.row();
		table.add(new Label("Press Y to begin", skin)).colspan(4);
		stage.addActor(table);
	}
	private void setupSkin() {
		skin = Station40Game.manager.get("data/uiskin.json", Skin.class);
		Pixmap redPixmap = new Pixmap(1, 1, Format.RGBA8888);
		redPixmap.setColor(Color.RED);
		redPixmap.fill();
		skin.add("red", new Texture(redPixmap));
		Pixmap bluePixmap = new Pixmap(1, 1, Format.RGBA8888);
		bluePixmap.setColor(Color.BLUE);
		bluePixmap.fill();
		skin.add("blue", new Texture(bluePixmap));
		
		skin.add("red", new Button.ButtonStyle(skin.newDrawable("red"), skin.newDrawable("blue"), skin.newDrawable("blue")));
		skin.add("blue", new Button.ButtonStyle(skin.newDrawable("blue"), skin.newDrawable("red"), skin.newDrawable("red")));
	}
	public void render(float delta) {
		for (Controller controller : Controllers.getControllers()) {
			if (controller.getButton(Ouya.BUTTON_Y))
				start();
		}
		stage.act();
		stage.draw();
		table.debug();
		Table.drawDebug(stage);
	}

	private void start() {
//		Controller[] controllers = new Controller[4];
		Station40Game.playerSelects.clear();
		for (int i = 0;i < joiners.length;i++) {
			Station40Game.addPlayer(joiners[i].getFaction(), joiners[i].getController());
		}
		Station40Game.startGame();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
