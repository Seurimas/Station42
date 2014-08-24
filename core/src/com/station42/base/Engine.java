package com.station42.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.station42.basic.EntityRenderer;
import com.station42.game.GameScreen;

public class Engine {
	public interface Message {
		
	}
	public final SpriteBatch batch;
	public final ShapeRenderer shapeRenderer;
	public Engine(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		this.batch = batch;
		this.shapeRenderer = shapeRenderer;
	}
	HashMap<Class<?>, ArrayList<Entity>> cachedQueries = new HashMap<Class<?>, ArrayList<Entity>>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	public Iterable<Entity> getEntitiesWithComponent(final Class<?> component) {
		if (cachedQueries.containsKey(component)) {
			return cachedQueries.get(component);
		}
		return new Iterable<Entity>() {
			ArrayList<Entity> foundEntities = new ArrayList<Entity>();
			@Override
			public void forEach(Consumer<? super Entity> arg0) {
				throw new RuntimeException("UNIMPLEMENTED FOREACH");
			}

			@Override
			public Iterator<Entity> iterator() {
				return new Iterator<Entity>() {
					Entity next = null;
					Iterator<Entity> base = entities.iterator();
					@Override
					public void forEachRemaining(Consumer<? super Entity> arg0) {
						throw new RuntimeException("UNIMPLEMENTED FOREACHREMAINING");
					}
					private void shiftToNext() {
						if (next != null)
							return;
						while (base.hasNext()) {
							Entity possibleNext = base.next();
							if (possibleNext.getComponent(component) != null) {
								next = possibleNext;
								break;
							}
						}
					}
					@Override
					public boolean hasNext() {
						shiftToNext();
						if (next == null) {
							cachedQueries.put(component, foundEntities);
						} else {
							foundEntities.add(next);
						}
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
	ArrayList<Entity> despawnedEntities = new ArrayList<Entity>();
	public void despawnEntity(Entity entity) {
		despawnedEntities.add(entity);
	}
	ArrayList<Entity> spawnedEntities = new ArrayList<Entity>();
	public void spawnEntity(Entity entity) {
		spawnedEntities.add(entity);
	}
	private void flushEntities() {
		entities.addAll(spawnedEntities);
		spawnedEntities.clear();
		entities.removeAll(despawnedEntities);
		despawnedEntities.clear();
		cachedQueries.clear();
	}
	ArrayList<Message> messages = new ArrayList<Message>();
	public void handleMessage(Message message) {
		for (EngineMessageListener listener : messageListeners) {
			listener.receiveMessage(this, message);
		}
	}
//	ArrayList<EngineRenderer> renderers = new ArrayList<EngineRenderer>();
	HashMap<Rectangle, ArrayList<EngineRenderer>> splitRenderers = new HashMap<Rectangle, ArrayList<EngineRenderer>>();
//	public void addRenderer(EngineRenderer renderer) {
//		renderers.add(renderer);
//	}
	ArrayList<EngineUpdateListener> systems = new ArrayList<EngineUpdateListener>();
	public void addSystem(EngineUpdateListener system) {
		systems.add(system);
	}
	ArrayList<EngineMessageListener> messageListeners = new ArrayList<EngineMessageListener>();
	public Matrix4 inverted;
	public void addMessageListener(EngineMessageListener messageListener) {
		messageListeners.add(messageListener);
	}
	public void update(float delta) {
		for (EngineUpdateListener system : systems) {
			system.update(this, delta);
		}
		flushEntities();
	}
	public void render(SpriteBatch batch, OrthographicCamera mainCamera, Rectangle viewport) {
		batch.setProjectionMatrix(mainCamera.combined);
		shapeRenderer.setProjectionMatrix(mainCamera.combined);
		inverted = batch.getProjectionMatrix().cpy().inv();
		batch.begin();
//		if (viewport != GameScreen.fullViewport) {
//			for (EngineRenderer renderer : renderers) {
//				renderer.render(this, batch, viewport);
//			}
//		}
		ArrayList<EngineRenderer> viewportRenderers = splitRenderers.get(viewport);
		if (viewportRenderers != null) {
			for (EngineRenderer renderer : viewportRenderers) {
				renderer.render(this, batch, viewport);
			}
		}
		batch.end();
	}
	public void addRenderer(Rectangle viewport, EngineRenderer entityRenderer) {
		if (!splitRenderers.containsKey(viewport))
			splitRenderers.put(viewport, new ArrayList<EngineRenderer>());
		splitRenderers.get(viewport).add(entityRenderer);
	}
	public boolean stillSpawned(Entity lastDrop) {
		return entities.contains(lastDrop) && !despawnedEntities.contains(lastDrop);
	}
}
