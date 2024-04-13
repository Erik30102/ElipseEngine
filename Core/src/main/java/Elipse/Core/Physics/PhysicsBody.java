package Elipse.Core.Physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;

public class PhysicsBody {

	private transient Body body;
	private Shape shape;

	public PhysicsBody() {
		shape = new PolygonShape();

	}

}
