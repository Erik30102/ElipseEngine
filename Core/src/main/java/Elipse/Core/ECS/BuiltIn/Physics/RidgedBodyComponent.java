package Elipse.Core.ECS.BuiltIn.Physics;

import Elipse.Core.ECS.Component;
import Elipse.Core.Maths.Vector;
import Elipse.Core.Physics.PhysicsBody;
import Elipse.Core.Physics.Collider.Collider;
import Elipse.Core.Physics.PhysicsBody.BodyType;

public class RidgedBodyComponent implements Component {
	public final PhysicsBody rg;
	private boolean RotationLocked = false;

	public RidgedBodyComponent() {
		rg = new PhysicsBody(BodyType.DYNAMIC);
	}

	public void ApplyImpulse(Vector impuls) {
		rg.ApplyImpulse(impuls);
	}

	public void ApplyImpulse(Vector impuls, Vector pos) {
		rg.ApplyImpulse(impuls, pos);
	}

	public void AddCollider(Collider collider) {
		rg.AddCollider(collider);
	}

	public float GetX() {
		return rg.GetX();
	}

	public float GetY() {
		return rg.GetY();
	}

	public Vector GetPosition() {
		return rg.GetPosition();
	}

	public Vector GetVelocity() {
		return rg.GetVelocity();
	}

	public void SetPosition(Vector position) {
		rg.SetPosition(position);
	}

	public void ApplyImpulse(float x, float y) {
		rg.ApplyImpulse(x, y);
	}

	public boolean IsRotationLocked() {
		return this.RotationLocked;
	}

	public void SetIsRotationLocked(boolean isRotationLocked) {
		this.RotationLocked = isRotationLocked;
	}
}
