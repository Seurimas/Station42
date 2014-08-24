package com.station42.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;

public class Station40Client implements EngineUpdateListener {
	public Client client = new Client();
	Entity myEntity;
	public Station40Client(Entity myEntity) {
		this.myEntity = myEntity;
		client.start();
		ProtocolSetup.initialize(client.getKryo());
		try {
//			client.connect(54555, new IntAd, 54666, 0);
			client.connect(50000, InetAddress.getByName("localhost"), 54555, 54666);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	float updateInterval = 0.1f;
	float sinceLast = 0;
	@Override
	public void update(Engine engine, float delta) {
		sinceLast += delta;
		if (sinceLast > updateInterval) {
			try {
				client.update(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sinceLast = 0;
			sendUpdate(engine);
		}
	}
	private void sendUpdate(Engine engine) {
		if (client.isConnected())
			client.sendUDP(myEntity);
	}
}
