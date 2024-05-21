package Elipse.Renderer.Lighting;

import Elipse.Core.Maths.Vector;
import Elipse.Renderer.Opengl.Shader;

public abstract class InternalLight {

	protected Vector position;

	/**
	 * Binds the light data to the shader at the given slot
	 * 
	 * @param shader the shader to bind
	 * @param slot   the slot to bind the light data to
	 */
	public abstract void Bind(Shader shader, int slot);

	/**
	 * Sets the position of the light
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	/**
	 * Returns the position of the object.
	 *
	 * @return the position of the object as a Vector
	 */
	public Vector getPosition() {
		return this.position;
	}
}
