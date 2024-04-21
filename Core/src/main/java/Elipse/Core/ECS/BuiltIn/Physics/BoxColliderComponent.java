package Elipse.Core.ECS.BuiltIn.Physics;

import Elipse.Core.ECS.Component;
import Elipse.Core.Physics.Collider.BoxCollider;

public class BoxColliderComponent implements Component {
	public float width = 0.5f;
	public float height = 0.5f;

	private BoxCollider collider;

	public BoxColliderComponent() {
		collider = new BoxCollider(width, height);
	}

	public BoxCollider getCollider() {
		return collider;
	}
}
