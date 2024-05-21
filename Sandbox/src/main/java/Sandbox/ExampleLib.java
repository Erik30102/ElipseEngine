package Sandbox;

import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;
import Elipse.Core.Input.Input;
import Elipse.Core.Input.KeyCode;

public class ExampleLib extends BaseComponent {

	public float speed = 300;

	@Override
	public void OnUpdate(float dt) {
		int deltax = 0;
		int deltay = 0;

		if (Input.IsKeyPressed(KeyCode.EL_KEY_W)) {
			deltay += 1;
		}
		if (Input.IsKeyPressed(KeyCode.EL_KEY_S)) {
			deltay -= 1;
		}
		if (Input.IsKeyPressed(KeyCode.EL_KEY_A)) {
			deltax -= 1;
		}
		if (Input.IsKeyPressed(KeyCode.EL_KEY_D)) {
			deltax += 1;
		}

		RidgedBodyComponent rg = entity.GetComponent(RidgedBodyComponent.class);

		if (rg != null) {
			rg.ApplyImpulse(deltax * speed * dt, deltay * speed * dt);
		}

	}
}
