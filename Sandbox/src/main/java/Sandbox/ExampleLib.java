package Sandbox;

import Elipse.Core.Logger;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public class ExampleLib extends BaseComponent {

	private float time = 2f;

	@Override
	public void OnUpdate(float dt) {
		time += dt;

		if (time >= 2) {
			time = 0;
			Logger.info("working from ExampleLib");
		}
	}
}
