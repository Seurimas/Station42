package com.station42.world;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.optimizations.RoomResident;

public class Room {
	public static Entity spawn(Engine engine, Rectangle bounds, 
			boolean up, boolean right, boolean down, boolean left, Object... otherComponents) {
		Room room = new Room(bounds, up, right, down, left);
		Entity roomEntity = new Entity("Room", otherComponents);
		roomEntity.setComponent(Room.class, room);
		engine.spawnEntity(roomEntity);
		for (Entity wall : room.walls) {
			for (Object component : otherComponents) {
				wall.setComponent(component.getClass(), component);
			}
			engine.spawnEntity(wall);
		}
		return roomEntity;
	}
	Rectangle bounds;
	ArrayList<Entity> walls = new ArrayList<Entity>();
	public ArrayList<Entity> residents = new ArrayList<Entity>();
	public void addResident(Entity resident) {
		residents.add(resident);
		RoomResident residential = resident.getComponent(RoomResident.class);
		if (residential != null) {
			residential.addRoom(this);
		}
	}
	public void removeResident(Entity resident) {
		residents.remove(resident);
		RoomResident residential = resident.getComponent(RoomResident.class);
		if (residential != null) {
			residential.removeRoom(this);
		}
	}
	public Room(Rectangle bounds, boolean upClosed, boolean rightClosed, boolean bottomClosed, boolean leftClosed) {
		this.bounds = bounds;
		addWall(bounds, 
				0, upClosed);
		addWall(bounds, 
				1, rightClosed);
		addWall(bounds, 
				2, bottomClosed);
		addWall(bounds, 
				3, leftClosed);
	}
	private void addWall(Rectangle bounds, int direction, boolean closed) {
		int minX = (int)bounds.x;
		int minY = (int)bounds.y;
		int maxX = (int)(bounds.x + bounds.width);
		int maxY = (int)(bounds.y + bounds.height);
		int xMod, yMod;
		int x0, x1, y0, y1;
		Vector2 in;
		switch (direction) {
		case 0: // Top wall
			x0 = minX;
			x1 = maxX;
			y0 = maxY;
			y1 = maxY;
			xMod = 64;
			yMod = 0;
			in = new Vector2(0, -1);
			break;
		case 1: // Right wall
			x0 = maxX;
			x1 = maxX;
			y0 = maxY;
			y1 = minY;
			xMod = 0;
			yMod = -64;
			in = new Vector2(-1, 0);
			break;
		case 2: // Bottom wall
			x1 = minX;
			x0 = maxX;
			y0 = minY;
			y1 = minY;
			xMod = -64;
			yMod = 0;
			in = new Vector2(0, 1);
			break;
		case 3: // Left wall
			x0 = minX;
			x1 = minX;
			y0 = minY;
			y1 = maxY;
			xMod = 0;
			yMod = 64;
			in = new Vector2(1, 0);
			break;
		default:
			throw new RuntimeException("BAD VALUE");
		}
		if (closed) {
			walls.add(new Entity("Room wall", new Wall(this, new Vector2(x0, y0), 
					new Vector2(x1, y1),
					in)));
		} else {
			walls.add(new Entity("Room wall", new Wall(this, new Vector2(x0, y0), 
					new Vector2((x0 + x1) / 2 - xMod, (y0 + y1) / 2 - yMod),
					in)));
			walls.add(new Entity("Room wall", new Wall(this, new Vector2((x0 + x1) / 2 + xMod, (y0 + y1) / 2 + yMod),
					new Vector2(x1, y1),
					in)));
		}
	}
	public ArrayList<Entity> getWalls() {
		return walls;
	}
	public int getCenterX() {
		return (int) (bounds.x + bounds.width / 2);
	}
	public int getCenterY() {
		return (int) (bounds.y + bounds.height / 2);
	}
}
