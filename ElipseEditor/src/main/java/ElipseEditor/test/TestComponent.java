package ElipseEditor.test;

import Elipse.Core.Logger;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public class TestComponent extends BaseComponent {
	private float time = 0;

	public float timer = 2f;

	@Override
	public void OnUpdate(float dt) {
		time += dt;

		if (time >= timer) {
			time = 0;
			Logger.info(this.entity.GetName());
		}
	}
}
