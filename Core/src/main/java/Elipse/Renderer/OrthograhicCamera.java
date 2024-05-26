package Elipse.Renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Elipse.Core.Maths.Vector;

public class OrthograhicCamera {
	private Matrix4f transform;

	private Matrix4f projection;

	private Matrix4f inversProjection;
	private Matrix4f inversTransform;

	private float zoom = 5f;

	/**
	 * Internal Representation of a camera for 2D rendering prettymuch no need to
	 * use this just use the CameraComponent in the ECS System
	 */
	public OrthograhicCamera() {
		projection = new Matrix4f();
		transform = new Matrix4f();
	}

	/**
	 * Resize the viewport based on the given width and height.
	 *
	 * @param width  the width of the viewport
	 * @param height the height of the viewport
	 * @return void
	 */
	public void Resize(int width, int height) {
		float aspectRatio = (float) width / height;

		float _width = zoom * 0.5f * aspectRatio;
		float _height = zoom * 0.5f;

		projection.identity();
		projection.ortho(-_width, _width, -_height, _height, 0f, 100f);

		inversProjection = projection.invert(new Matrix4f());
	}

	/**
	 * Retrieves the projection matrix.
	 *
	 * @return the projection matrix
	 */
	public Matrix4f GetProjection() {
		return projection;
	}

	/**
	 * A description of the entire Java function.
	 *
	 * @return description of return value
	 */
	public Matrix4f GetView() {
		return transform;
	}

	public Matrix4f GetInversView() {
		return this.inversTransform;
	}

	public Matrix4f GetInversProjection() {
		return this.inversProjection;
	}

	/**
	 * TODO: implement proper zoom
	 * 
	 * @param f the factor of which by it zooms
	 */
	public void SetZoom(float f) {
		zoom = f;
	}

	/**
	 * @return the current zoom
	 */
	public float GetZoom() {
		return zoom;
	}

	public void Move(Vector positon) {
		Vector3f cameraFront = new Vector3f(0, 0, -1);
		Vector3f cameraUp = new Vector3f(0, 1, 0);
		transform.identity();
		transform = transform.lookAt(
				new Vector3f(
						positon.getX(),
						positon.getY(), 20),
				cameraFront.add(positon.getX(), positon.getY(), 0), cameraUp);

		inversTransform = transform.invert(new Matrix4f());
	}
}
