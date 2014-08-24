package com.station42.base;

import java.util.Collection;
import java.util.HashMap;

import com.station42.player.move.EntityMoveState;

public class Entity {
	public final String name;
	public Entity(String name) {
		this.name = name;
	}
	HashMap<Class<?>, Object> components = new HashMap<Class<?>, Object>();
	public int id;
	public Entity(String name, Object... components) {
		this(name);
		for (Object component : components) {
			this.components.put(component.getClass(), component);
		}
	}
	public <T> T getComponent(Class<? extends T> componentType) {
		return (T) components.get(componentType);
	}
	public <T> void setComponent(Class<? extends T> componentType, T component) {
		components.put(componentType, component);
	}
	public Collection<Object> getComponents() {
		return components.values();
	}
}
