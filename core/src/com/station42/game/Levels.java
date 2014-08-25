package com.station42.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.station42.base.Engine;
import com.station42.base.Entity;
import com.station42.death.SpawnRoom;
import com.station42.faction.EntityFaction;
import com.station42.loot.LootDrop;
import com.station42.sentries.SentrySpawner;
import com.station42.world.Room;
import com.station42.world.Wall;
import com.station42.world.World;

public class Levels {
	private enum RoomTypes {
		Scoring,
		Danger,
		Spawning,
		Looting,
	};
	private static final int ROOM_SIZE = Room.ROOM_SIZE;
	private static final int SENTRY_OFFSET = ROOM_SIZE / 4;
	private static RoomTypes Scoring = RoomTypes.Scoring;
	private static RoomTypes Danger = RoomTypes.Danger;
	private static RoomTypes Spawning = RoomTypes.Spawning;
	private static RoomTypes Looting = RoomTypes.Looting;
	private static final int GREEN = World.greenWorld.worldId;
	private static final int ORANGE = World.orangeWorld.worldId;
	public static RoomTypes[][][] roomGrid;
	public static final String[] levelNames = {
		"Capture Points",
		"King of the Hill",
		"Deathmatch"
	};
	public static void setRoom0(){ 
		roomGrid = new RoomTypes[2][][];
		roomGrid[0] = new RoomTypes[][] {
				new RoomTypes[] {Spawning, Danger, Scoring, Looting},
				new RoomTypes[] {Danger, Scoring, Danger, Danger},
				new RoomTypes[] {Looting, Looting, Danger, Danger},
				new RoomTypes[] {Danger, Looting, Danger, Looting},
			};
		roomGrid[1] = new RoomTypes[][] {
				new RoomTypes[] {Danger, Danger, Looting, Looting},
				new RoomTypes[] {Danger, Danger, Looting, Looting},
				new RoomTypes[] {Danger, Danger, Scoring, Danger},
				new RoomTypes[] {Looting, Scoring, Danger, Spawning},
			};
	}
	public static void setRoom1(){ 
		roomGrid = new RoomTypes[2][][];
		roomGrid[0] = new RoomTypes[][] {
				new RoomTypes[] {Spawning, Danger, Looting},
				new RoomTypes[] {Danger, Scoring, Danger},
				new RoomTypes[] {Looting, Danger, Danger},
			};
		roomGrid[1] = new RoomTypes[][] {
				new RoomTypes[] {Danger, Danger, Looting},
				new RoomTypes[] {Danger, Danger, Danger},
				new RoomTypes[] {Looting, Danger, Spawning},
			};
	}
	public static void setRoom2(){ 
		roomGrid = new RoomTypes[2][][];
		roomGrid[0] = new RoomTypes[][] {
				new RoomTypes[] {Spawning, Looting, Danger},
				new RoomTypes[] {Looting, Danger, Looting},
				new RoomTypes[] {Danger, Looting, Danger},
			};
		roomGrid[1] = new RoomTypes[][] {
				new RoomTypes[] {Spawning, Danger, Looting},
				new RoomTypes[] {Danger, Looting, Danger},
				new RoomTypes[] {Looting, Danger, Spawning},
			};
	}
	public void setupLevel(Engine engine) {
		setupWorld(engine, World.greenWorld, true);
		setupWorld(engine, World.orangeWorld, false);
	}
	public void setupWorld(Engine engine, World world, boolean spawnSharedSentries) {
		RoomTypes[][] worldRooms;
		RoomTypes[][] otherWorldRooms;
		if (world == World.greenWorld) {
			worldRooms = roomGrid[ORANGE];
			otherWorldRooms = roomGrid[GREEN];
		} else {
			worldRooms = roomGrid[GREEN];
			otherWorldRooms = roomGrid[ORANGE];
		}
		for (int x = 0;x < worldRooms.length;x++) {
			for (int y = 0;y < worldRooms[x].length;y++) {
				Entity newRoom = Room.spawn(engine, new Rectangle(x * ROOM_SIZE, y * ROOM_SIZE, ROOM_SIZE, ROOM_SIZE), 
						getUp(worldRooms, x, y), getRight(worldRooms, x, y), getDown(worldRooms, x, y), getLeft(worldRooms, x, y), world);
				if (worldRooms[x][y] == Spawning) {
					newRoom.setComponent(SpawnRoom.class, new SpawnRoom(world.getHomeFaction()));
				} else if (worldRooms[x][y] == Danger) {
					float centerX = newRoom.getComponent(Room.class).getCenterX();
					float centerY = newRoom.getComponent(Room.class).getCenterY();
					for (int i = 0;i < 4;i++) {
						float sentryX = centerX + (i == 0 ? -SENTRY_OFFSET : (i == 1 ? SENTRY_OFFSET : 0));
						float sentryY = centerY + (i == 2 ? -SENTRY_OFFSET : (i == 3 ? SENTRY_OFFSET : 0));
						EntityFaction sentryFaction = world.getHomeFaction();
						if (otherWorldRooms[x][y] == Danger)
							sentryFaction = null;
						Entity sentry = SentrySpawner.spawnSentry(engine, sentryX, sentryY, sentryFaction);
						if (otherWorldRooms[x][y] != Danger) {
							sentry.setComponent(World.class, world);
						} else if (!spawnSharedSentries) {
							engine.despawnEntity(sentry);
						}
					}
				} else if (worldRooms[x][y] == Scoring) {
					float centerX = newRoom.getComponent(Room.class).getCenterX();
					float centerY = newRoom.getComponent(Room.class).getCenterY();
					ScoringPortal.spawnPortal(engine, centerX, centerY, world);
				} else if (worldRooms[x][y] == Looting) {
					newRoom.setComponent(LootDrop.class, new LootDrop(5, 5));
				}
			}
		}
	}
	private boolean getLeft(RoomTypes[][] worldRooms, int x, int y) {
		return x == 0;
	}
	private boolean getDown(RoomTypes[][] worldRooms, int x, int y) {
		return y == 0;
	}
	private boolean getRight(RoomTypes[][] worldRooms, int x, int y) {
		return x == worldRooms.length - 1;
	}
	private boolean getUp(RoomTypes[][] worldRooms, int x, int y) {
		return y == worldRooms[0].length - 1;
	}
}
