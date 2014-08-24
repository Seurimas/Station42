package com.station40.game.android;

import java.util.HashMap;

import tv.ouya.console.api.OuyaController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.android.AndroidController;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.station42.faction.EntityFaction;

public class OuyaPlayerJoiner extends Actor {
	BitmapFont font = new BitmapFont();
	int state = 0;
	boolean changingState =  false;
	String[] stateStrings = new String[] {"Press O to join.",
		"You are on team RED.\nPress a button to change.",
		"You are on team BLUE.\nPress a button to change.",
	};
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.drawMultiLine(batch, stateStrings[state], getX(), getCenterY());
	}
	private Controller getControllerForPlayer() {
		for (Controller controller : Controllers.getControllers()) {
			if (controller instanceof AndroidController) {
				AndroidController ouyaController = (AndroidController) controller;
				if (OuyaController.getPlayerNumByDeviceId(ouyaController.getDeviceId()) == playerIndex)
					return controller;
			}
		}
		return null;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if (Ouya.isRunningOnOuya()) {
			Controller controller = getControllerForPlayer();
			if (controller != null) {
				boolean stateChange = controller.getButton(Ouya.BUTTON_O);
				if (changingState && !stateChange) {
					changingState = false; 
				} else if (!changingState && stateChange) {
					changingState = true;
					state+= 1;
					if (state >= stateStrings.length)
						state = 0;
				}
			}
		}
	}
	int playerIndex;
	public OuyaPlayerJoiner(int i) {
		playerIndex = i;
	}
	public Controller getController() {
		return getControllerForPlayer();
//		return OuyaController.getControllerByPlayer(playerIndex);
	}
	public EntityFaction getFaction() {
		if (state == 0)
			return null;
		else if (state == 1)
			return EntityFaction.red;
		else
			return EntityFaction.blue;
	}

}
