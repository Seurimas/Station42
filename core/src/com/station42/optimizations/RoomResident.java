package com.station42.optimizations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.station42.base.Entity;
import com.station42.basic.EntityLocation;
import com.station42.faction.EntityFaction;
import com.station42.world.Room;

public class RoomResident {
	ArrayList<Room> rooms =  new ArrayList<Room>();
	public RoomResident() {
		
	}
	public Iterable<Entity> getNeighboringResidentsWithComponent(final Class<?> component) {
		return new Iterable<Entity>() {
			@Override
			public void forEach(Consumer<? super Entity> arg0) {
				throw new RuntimeException("UNIMPLEMENTED FOREACH");
			}

			@Override
			public Iterator<Entity> iterator() {
				return new Iterator<Entity>() {
					Entity next = null;
					Entity nextPossible = null;
					Iterator<Room> roomsIterator = rooms.iterator();
					Iterator<Entity> base;
					@Override
					public void forEachRemaining(Consumer<? super Entity> arg0) {
						throw new RuntimeException("UNIMPLEMENTED FOREACHREMAINING");
					}
					private void shiftToNextPossible() {
						while (base == null || !base.hasNext()) {
							while (roomsIterator.hasNext()) {
								Room possibleRoom = roomsIterator.next();
								if (possibleRoom.residents.size() != 0) {
									base = possibleRoom.residents.iterator();
									break;
								}
							}
						}
						if (base != null && base.hasNext())
							nextPossible = base.next();
					}
					private void shiftToNext() {
						if (next != null)
							return;
						shiftToNextPossible();
						while (nextPossible != null) {
							if (nextPossible.getComponent(component) != null) {
								next = nextPossible;
								break;
							}
							shiftToNextPossible();
						}
					}
					@Override
					public boolean hasNext() {
						shiftToNext();
						return next != null;
					}

					@Override
					public Entity next() {
						shiftToNext();
						Entity returned = next;
						next = null;
						return returned;
					}

					@Override
					public void remove() {
						throw new RuntimeException("UNIMPLEMENTED REMOVE");
					}
				};
			}

			@Override
			public Spliterator<Entity> spliterator() {
				throw new RuntimeException("UNIMPLEMENTED SPLITERATOR");
			}
		};
	}
	public void addRoom(Room room) {
		if (!rooms.contains(room))
			rooms.add(room);
	}
	public void removeRoom(Room room) {
		rooms.remove(room);
	}
	
}
