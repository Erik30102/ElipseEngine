package Elipse.Core.Maths;

/**
 * Represents a 3D vector with x, y, and z components.
 */
public class Vector3 {

	/** X component of the vector. */
	private float x;

	/** Y component of the vector. */
	private float y;

	/** Z component of the vector. */
	private float z;

	/**
	 * Default constructor that initializes the vector to (0, 0, 0).
	 */
	public Vector3() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}

	/**
	 * Constructor that initializes the vector with specific x, y, and z values.
	 * 
	 * @param x The x component of the vector.
	 * @param y The y component of the vector.
	 * @param z The z component of the vector.
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the x component of the vector.
	 * 
	 * @return The x component.
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
	 * Returns the y component of the vector.
	 * 
	 * @return The y component.
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
	 * Returns the z component of the vector.
	 * 
	 * @return The z component.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the z component of the vector.
	 * 
	 * @param z The new value for the z component.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Adds another Vector3 to this vector and returns a new Vector3 with the
	 * result.
	 * 
	 * @param other The Vector3 to add.
	 * @return A new Vector3 representing the sum.
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Subtracts another Vector3 from this vector and returns a new Vector3 with the
	 * result.
	 * 
	 * @param other The Vector3 to subtract.
	 * @return A new Vector3 representing the difference.
	 */
	public Vector3 subtract(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Scales this vector by a scalar value and returns a new Vector3 with the
	 * result.
	 * 
	 * @param scalar The scalar value to multiply by.
	 * @return A new Vector3 representing the scaled vector.
	 */
	public Vector3 scale(float scalar) {
		return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
	}

	/**
	 * Calculates the magnitude (length) of this vector.
	 * 
	 * @return The magnitude of the vector.
	 */
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes this vector (scales it to a unit length of 1).
	 * This method modifies the current vector.
	 */
	public void normalize() {
		float mag = magnitude();
		if (mag > 0) {
			this.x /= mag;
			this.y /= mag;
			this.z /= mag;
		}
	}

	/**
	 * Calculates the dot product of this vector with another Vector3.
	 * 
	 * @param other The other vector to perform the dot product with.
	 * @return The dot product of the two vectors.
	 */
	public float dotProduct(Vector3 other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Returns a string representation of the vector in the format "(x, y, z)".
	 * 
	 * @return A string representation of the vector.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
