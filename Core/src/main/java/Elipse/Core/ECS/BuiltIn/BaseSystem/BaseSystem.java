package Elipse.Core.ECS.BuiltIn.BaseSystem;

import java.util.List;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.IEntityListener;
import Elipse.Utils.Pair;

public class BaseSystem extends ECSSystem implements IEntityListener<BaseComponentWrapper> {

	@Override
	public void OnRuntimeStep(float dt) {
		List<Pair<Entity, Component>> comp = scene.GetComponents(BaseComponentWrapper.class);

		if (comp == null)
			return;

		comp.forEach(c -> {
			((BaseComponentWrapper) c.getValue()).OnUpdate(dt);
		});
	}

	@Override
	public void OnEditorDispose() {
		List<Pair<Entity, Component>> comp = scene.GetComponents(BaseComponentWrapper.class);

		if (comp == null)
			return;

		comp.forEach(c -> {
			((BaseComponentWrapper) c.getValue()).OnDisposeEditor();
		});
	}

	@Override
	public void OnRuntimeDispose() {
		List<Pair<Entity, Component>> comp = scene.GetComponents(BaseComponentWrapper.class);

		if (comp == null)
			return;

		comp.forEach(c -> {
			((BaseComponentWrapper) c.getValue()).OnDispse();
		});
	}

	@Override
	public void OnEditorStep(float dt) {
		List<Pair<Entity, Component>> comp = scene.GetComponents(BaseComponentWrapper.class);

		if (comp == null)
			return;

		comp.forEach(c -> {
			((BaseComponentWrapper) c.getValue()).OnUpdateEditor(dt);
		});
	}

	@Override
	public void OnStart() {
		this.scene.AddListener(BaseComponentWrapper.class, this);
	}

	@Override
	public void OnEntityAdded(Entity entity, BaseComponentWrapper component) {
		component.SetEntity(entity);

		component.OnStart();
	}

	@Override
	public void OnEntityRemoved(Entity entity, BaseComponentWrapper component) {
		component.OnDispse();
	}
}
