package Elipse.Core.ECS.BuiltIn.BaseSystem;

import Elipse.Core.ECS.Entity;

public abstract class BaseComponent {
	protected Entity entity;

	final void setEntity(Entity entity) {
		this.entity = entity;
	}

	public abstract void OnUpdate(float dt);
}
