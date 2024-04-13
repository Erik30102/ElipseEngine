package Elipse.Core.Physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;;

public class PhyscisEngine {

	private World world;

	private final static int VELOCITYITERNATION = 6;
	private final static int POSITIONITERNATION = 2;

	public enum BodyType {
		DYNAMIC, STATIC, KINEMATIC
	}

	public PhyscisEngine() {
		world = new World(new Vec2(0, 0));
	}

	public void SetGravity(float x, float y) {
		world.setGravity(new Vec2(x, y));
	}

	public BodyDef CreateBody() {
		BodyDef def = new BodyDef();

		return def;
	}

	public void Step(float dt) {
		world.step(dt, VELOCITYITERNATION, POSITIONITERNATION);
	}
}
