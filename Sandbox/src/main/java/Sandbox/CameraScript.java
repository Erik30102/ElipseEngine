package Sandbox;

import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public class CameraScript extends BaseComponent {

	private transient Entity player;

	@Override
	public void OnStart() {
		player = this.entity.GetScene().GetEntityByName("Player");
	}

	@Override
	public void OnUpdate(float dt) {
		if (player == null) {
			player = this.entity.GetScene().GetEntityByName("Player");
		}

		float diffX = this.entity.transform.position.getX() - player.transform.position.getX();
		float diffY = this.entity.transform.position.getY() - player.transform.position.getY();

		this.entity.transform.move(-diffX / 4, -diffY / 4);
	}

}
