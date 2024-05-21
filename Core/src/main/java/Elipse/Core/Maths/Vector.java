package Elipse.Core.Maths;

/**
 * Represents a 2D vector with x and y components.
 */
public class Vector {

	/**
	 * X component of the vector.
	 */
	private float x;

	/**
	 * Y component of the vector.
	 */
	private float y;

	/**
	 * Creates a new Vector object with default values (0, 0).
	 */
	public Vector() {
		this(0.0f, 0.0f);
	}

	/**
	 * Creates a new Vector object with specified x and y components.
	 * 
	 * @param x The x component of the vector.
	 * @param y The y component of the vector.
	 */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	/**
	 * Gets the x component of the vector.
	 * 
	 * @return The x component of the vector.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the x component of the vector.
	 * 
	 * @param x The new value for the x component.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the y component of the vector.
	 * 
	 * @return The y component of the vector.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y component of the vector.
	 * 
	 * @param y The new value for the y component.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Adds another Vector to this vector and returns a new vector with the
	 * result.
	 * 
	 * @param other The vector to add.
	 * @return A new Vector object containing the sum.
	 */
	public Vector add(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	/**
	 * Subtracts another Vector from this vector and returns a new vector with the
	 * result.
	 * 
	 * @param other The vector to subtract.
	 * @return A new Vector object containing the difference.
	 */
	public Vector subtract(Vector other) {
		return new Vector(this.x - other.x, this.y - other.y);
	}

	/**
	 * Multiplies this vector by a scalar value and returns a new vector with the
	 * result.
	 * 
	 * @param scalar The scalar value to multiply by.
	 * @return A new Vector object containing the product.
	 */
	public Vector multiply(float scalar) {
		return new Vector(this.x * scalar, this.y * scalar);
	}

	/**
	 * Calculates the magnitude (length) of this vector.
	 * 
	 * @return The magnitude of the vector.
	 */
	public float magnitude() {
		return (float) Math.sqrt(this.x * this.x + this.y * this.y);
	}

	/**
	 * Normalizes this vector (scales it to have a magnitude of 1).
	 */
	public void normalize() {
		float mag = magnitude();
		if (mag > 0.0f) {
			this.x /= mag;
			this.y /= mag;
		}
	}

	/**
	 * Returns a string representation of this vector in the format "(x, y)".
	 * 
	 * @return A string representation of the vector.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Sets the x and y components of the vector to the specified values.
	 *
	 * @param x the new value for the x component
	 * @param y the new value for the y component
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the x and y components of this vector to the values of the given vector.
	 *
	 * @param vector the vector whose x and y components will be copied to this
	 *               vector
	 */
	public void set(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	/**
	 * Adds the specified values to the x and y components of the vector.
	 * 
	 * @param x the value to add to the x component
	 * @param y the value to add to the y component
	 */
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
}