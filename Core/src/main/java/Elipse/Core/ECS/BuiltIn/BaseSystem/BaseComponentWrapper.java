package Elipse.Core.ECS.BuiltIn.BaseSystem;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Entity;

public class BaseComponentWrapper implements Component {

	private BaseComponent comp;

	final void SetEntity(Entity entity) {
		comp.setEntity(entity);
	}

	public BaseComponentWrapper(BaseComponent comp) {
		this.comp = comp;
	}

	public void OnUpdate(float dt) {
		this.comp.OnUpdate(dt);
	}

	public void OnUpdateEditor(float dt) {
		this.comp.OnUpdateEditor(dt);
	}

	public void OnDispse() {
		this.comp.OnDispse();
	}

	public void OnDisposeEditor() {
		this.comp.OnDisposeEditor();
	}

	public void OnStart() {
		this.comp.OnStart();
	}

	public BaseComponent GetComponent() {
		return this.comp;
	}
}
