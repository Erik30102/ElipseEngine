package Elipse.Core.Physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import Elipse.Core.Maths.Vector;;

public class PhyscisEngine {

	private static PhyscisEngine INSTANCE;

	private World world;

	private final static int VELOCITYITERNATION = 6;
	private final static int POSITIONITERNATION = 2;

	public static void Init() {
		new PhyscisEngine();
	}

	public PhyscisEngine() {
		world = new World(new Vec2(0, 0));

		INSTANCE = this;
	}

	public void SetGravity(float x, float y) {
		world.setGravity(new Vec2(x, y));
	}

	public void SetGravity(Vector g) {
		this.SetGravity(g.getX(), g.getY());
	}

	public Body CreateBody(BodyDef def) {
		return world.createBody(def);
	}

	public void Step(float dt) {
		world.step(dt, VELOCITYITERNATION, POSITIONITERNATION);
	}

	public static PhyscisEngine GetInstance() {
		return INSTANCE;
	}
}
