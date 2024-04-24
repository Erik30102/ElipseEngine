package Elipse.Core.ECS.BuiltIn.BaseSystem;

import Elipse.Core.ECS.Entity;

public abstract class BaseComponent {
	protected transient Entity entity;

	final void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Gets called every frame
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public abstract void OnUpdate(float dt);

	/**
	 * Gets called every frame in the editor while editing
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public void OnUpdateEditor(float dt) {
	}

	/**
	 * Dispose all resources used by the component in runtime
	 */
	public void OnDispse() {
	}

	/**
	 * Dispose all resources used by the component in the editor
	 */
	public void OnDisposeEditor() {
	}

	/**
	 * Called when the component is added to the scene
	 */
	public void OnStart() {
	}
}
