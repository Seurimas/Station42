package com.station42.server;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.station42.base.Entity;
import com.station42.server.serializers.EntitySerializer;

public class ProtocolSetup {
	public static void initialize(Kryo kryo) {
		kryo.setRegistrationRequired(false);
		kryo.register(Entity.class, new EntitySerializer());
	}
}
