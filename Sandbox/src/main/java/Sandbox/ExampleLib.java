package Sandbox;

import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;
import Elipse.Core.Input.Input;
import Elipse.Core.Input.KeyCode;

public class ExampleLib extends BaseComponent {

	public float movementSpeed = 100;
	public float decellSpeed = 5;

	private transient RidgedBodyComponent rg;

	@Override
	public void OnUpdate(float dt) {
		if (rg == null)
			rg = entity.GetComponent(RidgedBodyComponent.class);

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

		this.move(deltax, deltay);

	}

	private void move(int deltaX, int deltaY) {
		float targetY = deltaY * this.movementSpeed;
		float targetX = deltaX * this.movementSpeed;

		float speedDiffY = targetY - rg.GetVelocity().getY() * this.decellSpeed;
		float speedDiffX = targetX - rg.GetVelocity().getX() * this.decellSpeed;

		float movementY = (float) Math.pow(Math.abs(speedDiffY) * 30, 0.9)
				* Math.signum(speedDiffY);
		float movementX = (float) Math.pow(Math.abs(speedDiffX) * 30, 0.9)
				* Math.signum(speedDiffX);

		rg.ApplyImpulse(movementX, movementY);
	}
}
