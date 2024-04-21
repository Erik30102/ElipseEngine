package Elipse.Core.Physics.Collider;

import org.jbox2d.collision.shapes.Shape;

public abstract class Collider {
	protected Shape shape;

	public Shape getShape() {
		return shape;
	}
}
