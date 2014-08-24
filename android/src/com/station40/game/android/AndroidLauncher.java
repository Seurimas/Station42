package com.station40.game.android;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.station42.game.Station40Game;

public class AndroidLauncher extends AndroidApplication {
	public static Context context;

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		AndroidLauncher.context = getApplicationContext();
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Station40Game(OuyaMenuScreen.getter), config);
//		System.out.println(Gdx.files.getLocalStoragePath());
//		Debug.startMethodTracing(Gdx.files.getLocalStoragePath() + "trace.trace");
	}
}
