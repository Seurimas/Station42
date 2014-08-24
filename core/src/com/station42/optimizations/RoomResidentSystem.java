package com.station42.optimizations;

import com.station42.base.Engine;
import com.station42.base.EngineUpdateListener;
import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.world.Room;
import com.station42.world.World;

public class RoomResidentSystem implements EngineUpdateListener {
	Room[][][] rooms;
	@Override
	public void update(Engine engine, float delta) {
		if (rooms == null)
			initializeRoomMap(engine);
		if (rooms != null) { // Initialization succeeded
			for (Room[][] world : rooms) {
				for (Room[] roomRow : world) {
					for (Room room : roomRow) {
						room.clearResidents();
					}
				}
			}
			for (Entity roomEntity : engine.getEntitiesWithComponent(RoomResident.class)) {
				EntityLocation location = roomEntity.getComponent(EntityLocation.class);
				int leftX = (int)((location.getCenterX() - Room.ROOM_SIZE / 2) / Room.ROOM_SIZE);
				int bottomY = (int)((location.getCenterY() - Room.ROOM_SIZE / 2) / Room.ROOM_SIZE);
				int rightX = leftX + 1;
				int topY = bottomY + 1;
				int[] worldIds;
				if (roomEntity.getComponent(World.class) == null)
					worldIds = new int[] { 0, 1};
				else
					worldIds = new int[] { roomEntity.getComponent(World.class).worldId };
				for (int world : worldIds) {
					for (int x = Math.max(leftX, 0);x < rooms[world].length && x <= rightX;x++) {
						for (int y = Math.max(bottomY, 0);y < rooms[world][x].length && y <= topY;y++) {
							rooms[world][x][y].addResident(roomEntity);
						}
					}
				}
			}
		}
	}
	private void initializeRoomMap(Engine engine) {
		int maxX = -1;
		int maxY = -1;
		for (Entity roomEntity : engine.getEntitiesWithComponent(Room.class)) {
			Room room = roomEntity.getComponent(Room.class);
			if (room.bounds.x / Room.ROOM_SIZE > maxX) {
				maxX = (int) (room.bounds.x / Room.ROOM_SIZE);
			}
			if (room.bounds.y / Room.ROOM_SIZE > maxY) {
				maxY = (int) (room.bounds.y / Room.ROOM_SIZE);
			}
		}
		if (maxX != -1 && maxY != -1) {
			rooms = new Room[2][maxX + 1][maxY + 1];
			for (Entity roomEntity : engine.getEntitiesWithComponent(Room.class)) {
				Room room = roomEntity.getComponent(Room.class);
				int roomX = (int) ((room.bounds.x + 5) / Room.ROOM_SIZE);
				int roomY = (int) ((room.bounds.y + 5) / Room.ROOM_SIZE);
				if (rooms[roomEntity.getComponent(World.class).worldId][roomX][roomY] != null)
					throw new RuntimeException("BAD ROOMS!");
				rooms[roomEntity.getComponent(World.class).worldId][roomX][roomY] = room;
			}
		}
	}

}
