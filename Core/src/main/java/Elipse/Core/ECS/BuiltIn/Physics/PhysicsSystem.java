package Elipse.Core.ECS.BuiltIn.Physics;

import java.util.List;

import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.IEntityListener;
import Elipse.Core.Physics.PhyscisEngine;
import Elipse.Utils.Pair;

public class PhysicsSystem extends ECSSystem {

	@Override
	public void step(float dt) {
		PhyscisEngine.GetInstance().Step(dt);

		List<Pair<Entity, Component>> components = this.scene.GetComponents(RidgedBodyComponent.class);

		if (components == null)
			return;

		components.forEach((pair) -> {
			Entity e = pair.getKey();
			e.transform.position.set(((RidgedBodyComponent) pair.getValue()).GetPosition());
		});
	}

	@Override
	public void OnStart() {
		PhyscisEngine.Init();

		this.scene.AddListener(BoxColliderComponent.class, new IEntityListener<BoxColliderComponent>() {
			@Override
			public void OnEntityAdded(Entity entity, BoxColliderComponent component) {
				RidgedBodyComponent rg = scene.GetComponent(entity, RidgedBodyComponent.class);

				if (rg == null) {
					Logger.c_error("BoxCollider needs RidgetBody to work");
					return;
				}

				rg.SetPosition(entity.transform.position);
				rg.AddCollider(component.getCollider());
			}

			@Override
			public void OnEntityRemoved(Entity entity, BoxColliderComponent component) {

			}
		});
	}

}
