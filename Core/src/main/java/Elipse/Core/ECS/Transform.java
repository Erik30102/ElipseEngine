package Elipse.Core.ECS;

import org.joml.Vector2f;

import Elipse.Core.Maths.Vector;

/**
 * Represents a 2D transformation of an entity in a game engine.
 * This class stores the position, scale, and rotation of an entity.
 */
public class Transform {

	public final Vector position;

	public final Vector scale;

	public float rotation;

	/**
	 * Constructs a new Transform with the specified position, scale, and rotation.
	 *
	 * @param position The initial position of the entity.
	 * @param scale    The initial scale of the entity.
	 * @param rotation The initial rotation of the entity in degrees.
	 */
	public Transform(Vector position, Vector scale, float rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}

	/**
	 * Constructs a new Transform with the default values:
	 * - position: (0, 0)
	 * - scale: (1, 1)
	 * - rotation: 0 degrees
	 */
	public Transform() {
		this(new Vector(0, 0), new Vector(1, 1), 0);
	}

	/**
	 * Moves the entity by the specified vector amount.
	 *
	 * @param vec The amount to move the entity by on the X and Y axes.
	 */
	public void move(Vector vec) {
		this.position.add(vec);
	}

	/**
	 * Moves the entity by the specified amounts on the X and Y axes.
	 *
	 * @param x The amount to move the entity on the X axis.
	 * @param y The amount to move the entity on the Y axis.
	 */
	public void move(float x, float y) {
		this.position.add(x, y);
	}

	/**
	 * Sets the position of the entity to the specified vector.
	 *
	 * @param vec The new position of the entity.
	 */
	public void setPosition(Vector vec) {
		this.position.set(vec);
	}

	/**
	 * Sets the position of the entity to the specified coordinates.
	 *
	 * @param x The new X position of the entity.
	 * @param y The new Y position of entity.
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	/**
	 * Rotates the entity by the specified angle in degrees.
	 *
	 * @param angle The amount to rotate the entity in degrees.
	 */
	public void rotate(float angle) {
		this.rotation += angle;
	}

	/**
	 * Sets the rotation of the entity to the specified angle in degrees.
	 *
	 * @param angle The new rotation of the entity in degrees.
	 */
	public void setRotation(float angle) {
		this.rotation = angle;
	}

	/**
	 * Gets the current position of the entity.
	 *
	 * @return A copy of the current position vector.
	 */
	public Vector GetPosition() {
		return new Vector(position);
	}

	/**
	 * Gets the current scale of the entity.
	 *
	 * @return A copy of the current scale vector.
	 */
	public Vector GetScale() {
		return new Vector(scale);
	}

	/**
	 * Sets the scale of the entity to the specified vector.
	 *
	 * @param scale The new scale of the entity.
	 */
	public void setScale(Vector scale) {
		this.scale.set(scale);
	}

	/**
	 * Sets the scale of the entity to the specified values.
	 *
	 * @param x The new scale on the X axis.
	 * @param y The new scale on the Y axis.
	 */
	public void setScale(float x, float y) {
		this.scale.set(x, y);
	}
}
