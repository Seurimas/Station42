package com.station42.game;

import java.util.HashMap;

import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
import com.station42.game.Station40Game.MainMenuGetter;
import com.station42.input.MouseAndKeyboardController;

public class MainMenuScreen implements Screen {
	public static MainMenuGetter getter = new MainMenuGetter() {
		
		@Override
		public Screen getScreen(SpriteBatch batch) {
			return new MainMenuScreen(batch);
		}
	};
	Stage stage;
	Table table;
	private Skin skin;
	OrderedMap<Button, SelectBox<Controller>> playerSelects;
	public MainMenuScreen(SpriteBatch batch) {
		stage = new Stage(new FitViewport(800, 600), batch);
		setupSkin();
		table = new Table();
		table.setFillParent(true);
		table.add(Station40Game.getTitleActor()).fill().colspan(2).row();
		playerSelects = new OrderedMap<Button, SelectBox<Controller>>();
		addPlayerRow(table, 0);
		addPlayerRow(table, 1);
		addPlayerRow(table, 2);
		addPlayerRow(table, 3);
		addPlayButton(table);
		stage.addActor(table);
	}
	private void addPlayButton(Table table2) {
		TextButton playButton = new TextButton("Play!", skin);
		table2.add(playButton).size(128, 16).colspan(2).center();
		playButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Station40Game.playerSelects.clear();
				for (Entry<Button, SelectBox<Controller>> player : playerSelects.entries()) {
					Station40Game.addPlayer(
							player.key.isChecked() ? EntityFaction.blue : EntityFaction.red, 
							player.value.getSelected());
				}
				Station40Game.startGame();
			}
		});
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
	private void onSelect(SelectBox<Controller> changed) {
		for (SelectBox<Controller> other : playerSelects.values()) {
			if (other != changed) {
				if (other.getSelected() == changed.getSelected() && changed.getSelected() != MouseAndKeyboardController.None) {
					if (changed.getSelectedIndex() < changed.getItems().size - 1) {
						changed.setSelectedIndex(changed.getSelectedIndex() + 1);
					} else {
						changed.setSelectedIndex(0);
						onSelect(changed);
						return;
					}
				}
			}
		}
	}
	private void addPlayerRow(Table table, int playerIndex) {
		Button teamButton = new Button(skin, "red");
		teamButton.setChecked(playerIndex % 2 == 1);
		table.add(teamButton).size(16, 16).pad(8);
		final SelectBox<Controller> controllerList = new SelectBox<Controller>(skin);
		Controller[] controllers;
		int i = 0;
		controllers = new Controller[Controllers.getControllers().size + 4 + (playerIndex >= 2 ? 1 : 0)];
		if (playerIndex >= 2)
		{
			controllers[i] = MouseAndKeyboardController.None;
			i++;
		}
		controllers[i] = MouseAndKeyboardController.WASD;
		i++;
		controllers[i] = MouseAndKeyboardController.TFGH;
		i++;
		controllers[i] = MouseAndKeyboardController.IJKL;
		i++;
		controllers[i] = MouseAndKeyboardController.ARROWS;
		i++;
		for (Controller controller : Controllers.getControllers()) {
			controllers[i] = controller;
			i++;
		}
		controllerList.setItems(controllers);
		controllerList.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSelect(controllerList);
			}
		});
		controllerList.setSelectedIndex(0);
		onSelect(controllerList);
//		controllerList.setItems(baseArray);
		table.add(controllerList).right().row();
		playerSelects.put(teamButton, controllerList);
	}
	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
//		table.debug();
//		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
