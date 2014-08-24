package com.station42.server;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;

public class Station40Server implements EngineUpdateListener {
	ArrayList<Connection> droppedConnections = new ArrayList<Connection>();
	ArrayList<Connection> newConnections = new ArrayList<Connection>();
	public Server server = new Server();
	public Station40Server() {
		server.start();
		ProtocolSetup.initialize(server.getKryo());
		try {
			server.bind(54555, 54666);
			server.addListener(new Listener() {

				@Override
				public void connected(Connection connection) {
					super.connected(connection);
				}

				@Override
				public void disconnected(Connection connection) {
					super.disconnected(connection);
				}

				@Override
				public void received(Connection connection, Object object) {
					// TODO Auto-generated method stub
					super.received(connection, object);
				}
				
			});
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
			sinceLast = 0;
			sendUpdate(engine);
		}
	}
	private void sendUpdate(Engine engine) {
		for (Entity connectedEntity : engine.getEntitiesWithComponent(Connection.class)) {
			if (droppedConnections.contains(connectedEntity.getComponent(Connection.class))) {
				engine.despawnEntity(connectedEntity);
				server.sendToAllUDP(connectedEntity.id);
			}
		}
		for (Connection connection : server.getConnections()) {
			for (Entity entity : engine.getEntitiesWithComponent(EntityFaction.class)) {
				if (entity.getComponent(Connection.class) != connection) {
					connection.sendUDP(entity);
				}
			}
		}
	}
}
