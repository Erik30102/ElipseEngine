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

	/**
	 * Adds a collider the the given physics body IMPORTANT: a collider without a
	 * body dosnt work every entity that has a collider must also have a Ridgidbody
	 * component
	 * 
	 * @param collider
	 */
	public void AddCollider(Collider collider) {
		FixtureDef def = new FixtureDef();
		def.shape = collider.getShape();
		def.density = this.density;
		def.friction = this.friction;

		this.body.createFixture(def);
	}

	/**
	 * @return the density of the object has an impact on collision resolution
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * @param density set the density of the object
	 */
	public void setDensity(float density) {
		this.density = density;
	}

	/**
	 * @return the x cordinate of the object in the physics simulation
	 */
	public float GetX() {
		return body.getPosition().x;
	}

	/**
	 * @return the y cordinate of the object in the physics simulation
	 */
	public float GetY() {
		return body.getPosition().y;
	}

	/**
	 * @return the position as a vector in the physics simulation
	 */
	public Vector GetPosition() {
		return new Vector(GetX(), GetY());
	}

	/**
	 * Apply an impulse at the location of the body so as if the body makes the
	 * impulse itself
	 * 
	 * @param impuls the 2d vector representation of the impuls
	 */
	public void ApplyImpulse(Vector impuls) {
		body.applyForce(new Vec2(impuls.getX(), impuls.getY()), new Vec2(this.GetX(), this.GetY()));
	}

	/**
	 * Same as ApplyImpulse but with a set location from which the impulse is
	 * comming from
	 * 
	 * @param impuls   the 2d vector representation of the impuls
	 * @param location the 2d location from where the impulse is applied
	 */
	public void ApplyImpulse(Vector impuls, Vector location) {
		body.applyForce(new Vec2(impuls.getX(), impuls.getY()), new Vec2(location.getX(), location.getY()));
	}

	/**
	 * Sets the position of the object to the specified vector
	 * 
	 * @param position the new position of the object
	 */
	public void SetPosition(Vector position) {
		body.setTransform(new Vec2(position.getX(), position.getY()), body.getAngle());
	}

	/**
	 * Apply an impulse at the location of the body so as if the body makes the
	 * impulse itself
	 * 
	 * @param x the x component of the impulse
	 * @param y the y component of the impulse
	 */
	public void ApplyImpulse(float x, float y) {
		this.ApplyImpulse(new Vector(x, y));
	}

	public Vector GetVelocity() {
		return new Vector(this.body.getLinearVelocity().x, this.body.getLinearVelocity().y);
	}
}
