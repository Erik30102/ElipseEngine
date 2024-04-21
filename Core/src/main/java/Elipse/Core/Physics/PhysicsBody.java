package Elipse.Core.Physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import Elipse.Core.Maths.Vector;
import Elipse.Core.Physics.Collider.Collider;

public class PhysicsBody {

	private float density = 1;
	private float friction = 0;

	private transient Body body;
	public BodyType bodyType;

	public enum BodyType {
		DYNAMIC, STATIC, KINEMATIC
	}

	private static org.jbox2d.dynamics.BodyType GetBodyType(BodyType bodyType) {
		switch (bodyType) {
			case DYNAMIC:
				return org.jbox2d.dynamics.BodyType.DYNAMIC;
			case STATIC:
				return org.jbox2d.dynamics.BodyType.STATIC;
			case KINEMATIC:
				return org.jbox2d.dynamics.BodyType.KINEMATIC;
			default:
				return null;
		}
	}

	public PhysicsBody(BodyType bodyType) {
		BodyDef def = new BodyDef();
		def.type = GetBodyType(bodyType);

		body = PhyscisEngine.GetInstance().CreateBody(def);
	}

	public void AddCollider(Collider collider) {
		FixtureDef def = new FixtureDef();
		def.shape = collider.getShape();
		def.density = this.density;
		def.friction = this.friction;

		this.body.createFixture(def);
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float GetX() {
		return body.getPosition().x;
	}

	public float GetY() {
		return body.getPosition().y;
	}

	public Vector GetPosition() {
		return new Vector(GetX(), GetY());
	}

	public void ApplyImpulse(Vector impuls) {
		body.applyForce(new Vec2(impuls.x, impuls.y), new Vec2(this.GetX(), this.GetY()));
	}

	public void ApplyImpulse(Vector impuls, Vector location) {
		body.applyForce(new Vec2(impuls.x, impuls.y), new Vec2(location.x, location.y));
	}

	public void SetPosition(Vector position) {
		body.setTransform(new Vec2(position.x, position.y), body.getAngle());
	}

	public void ApplyImpulse(float x, float y) {
		this.ApplyImpulse(new Vector(x, y));
	}
}
