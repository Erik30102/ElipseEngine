package Elipse.Core.ECS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Utils.Pair;

public class Scene extends Asset implements Cloneable {

	private List<ECSSystem> systems = new ArrayList<ECSSystem>();

	private HashMap<Entity, List<Component>> entities = new HashMap<>();
	private HashMap<Class<? extends Component>, List<Pair<Entity, Component>>> componentDictionary = new HashMap<>();

	@SuppressWarnings("rawtypes")
	private HashMap<Class<? extends Component>, IEntityListener> listenerDictionary = new HashMap<>();

	/**
	 * Creates a new Entity with custom name
	 * 
	 * @return The newly created Entity.
	 */
	public Entity Create(String name) {
		Entity entity = new Entity(name);
		entity.setScene(this);
		entities.put(entity, new ArrayList<>());
		return entity;
	}

	/**
	 * Adds a listener for the specified component
	 * 
	 * @param component the component type to add the listener for
	 * @param listener  the listener
	 */
	public <T extends Component> void AddListener(Class<T> component, IEntityListener<T> listener) {
		listenerDictionary.put(component, listener);
	}

	/**
	 * Removes the listener for the specified component
	 * 
	 * @param component the component type to remove the listener for
	 */
	public void RemoveListener(Class<? extends Component> component) {
		listenerDictionary.remove(component);
	}

	/**
	 * Adds an array of ECSSystems to the list of systems to handle new set of
	 * Components
	 *
	 * @param systems the ECSSystems to be added
	 */
	public void AddSystem(ECSSystem... systems) {
		for (ECSSystem system : systems) {
			AddSystem(system);
		}
	}

	/**
	 * Adds an ECSSystem to the list of systems to handle new set of Components
	 *
	 * @param system the ECSSystem to be added
	 */
	public void AddSystem(ECSSystem system) {
		system.setScene(this);
		systems.add(system);

		system.OnStart();
	}

	/**
	 * Creates a new Entity with a default name based on the number of existing
	 * entities.
	 *
	 * @return The newly created Entity.
	 */
	public Entity Create() {
		return Create("Entity: " + (entities.size() + 1));
	}

	/**
	 * @return Retrun the internal map of the entities
	 */
	public HashMap<Entity, List<Component>> GetEntities() {
		return entities;
	}

	/**
	 * Retrieves an entity by its name.
	 *
	 * @param name the name of the entity to retrieve
	 * @return the entity with the specified name, or null if not found
	 */
	public Entity GetEntityByName(String name) {
		return entities.keySet().stream().filter(e -> e.GetName() == name).findFirst().orElse(null);
	}

	/**
	 * Adds a component to the specified entity and registers it within the ECS
	 *
	 * @param component the component to be added
	 * @param entity    the entity to which the component will be added
	 */
	@SuppressWarnings("unchecked")
	public void AddComponent(Component component, Entity entity) {
		entities.get(entity).add(component);
		if (!componentDictionary.containsKey(component.getClass())) {
			componentDictionary.put(component.getClass(), new ArrayList<>());
		}
		componentDictionary.get(component.getClass()).add(new Pair<>(entity, component));

		if (listenerDictionary.containsKey(component.getClass())) {
			listenerDictionary.get(component.getClass()).OnEntityAdded(entity, component);
		}
	}

	/**
	 * Removes a specifc component from an specific entity
	 * 
	 * @param component the class to remove
	 * @param entity    the entity to remove from
	 */
	public void RemoveComponent(Component component, Entity entity) {
		entities.get(entity).remove(component);
		componentDictionary.get(component.getClass()).removeIf(f -> f.getKey() == entity && f.getValue() == component);
	}

	public void RemoveEntity(Entity entity) {
		// TODO

		// componentDictionary.values().forEach(a -> a.removeIf(c -> c.entity ==
		// entity));
		// entities.remove(entity);
	}

	/**
	 * Get component of type from entity if it exists
	 * 
	 * @param entity    the entity
	 * 
	 * @param component the type of component
	 * 
	 * @return the component of the given type from the entity or null if it dosn't
	 *         exist
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T GetComponent(Entity entity, Class<T> component) {
		return (T) entities.get(entity).stream()
				.filter(c -> c.getClass() == component)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Get all components of a certain type
	 * 
	 * @param component the type of component
	 * 
	 * @return a list of all components of the given type
	 */
	public List<Pair<Entity, Component>> GetComponents(Class<? extends Component> component) {
		return componentDictionary.get(component);
	}

	/**
	 * Retrieves the list of components associated with the given entity.
	 *
	 * @param entity the entity for which to retrieve the components
	 * @return the list of components associated with the entity
	 */
	public List<Component> GetComponents(Entity entity) {
		return entities.get(entity);
	}

	/**
	 * Called every frame to update all systems and the entitys assoisiated with
	 * them
	 * 
	 * Only call this in runtime and not in editor mode
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public void OnRuntimeStep(float dt) {
		for (ECSSystem system : systems) {
			system.OnRuntimeStep(dt);
		}
	}

	/**
	 * Disposes all of the elements of the scene when the game is nolonger running
	 * but no the resources needed for displaying to viewport
	 */
	public void OnDisposeEditor() {
		for (ECSSystem system : systems) {
			system.OnEditorDispose();
		}
	}

	/**
	 * Dispose all resources used by the scene
	 */
	public void OnDisposeRuntime() {
		for (ECSSystem system : systems) {
			system.OnRuntimeDispose();
		}
	}

	/**
	 * Called every frame of the editor to have somthing display in the editor
	 * viewport while you are not playing
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public void OnEditorStep(float dt) {
		for (ECSSystem system : systems) {
			system.OnEditorStep(dt);
		}
	}

	/**
	 * @return a deep copy of the scene
	 */
	public Scene Copy() {
		try {
			return (Scene) this.clone();
		} catch (CloneNotSupportedException e) {
			Logger.c_warn("couldn't copy scene: ");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AssetType GetAssetType() {
		return AssetType.SCENE;
	}
}
