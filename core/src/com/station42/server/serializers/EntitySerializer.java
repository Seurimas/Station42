package com.station42.server.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.station42.base.Entity;

public class EntitySerializer extends Serializer<Entity> {
	public Entity read(Kryo kryo, Input input, Class<Entity> type) {
		return (Entity) kryo.readClassAndObject(input);
	}

	@Override
	public void write(Kryo kryo, Output output, Entity object) {
		for (Object component : object.getComponents()) {
			Registration serializer = kryo.getRegistration(component.getClass());
			if (serializer.getId() != -1) {
				kryo.writeClassAndObject(output, component);
			}
		}
	}

}
