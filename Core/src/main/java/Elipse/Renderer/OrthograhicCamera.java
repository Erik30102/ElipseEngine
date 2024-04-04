package Elipse.Renderer;

import org.joml.Matrix4f;

public class OrthograhicCamera {
	private Matrix4f transform;

	private Matrix4f projection;

	private float zoom = 5f;

	public OrthograhicCamera() {

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

		projection = new Matrix4f();
		projection
				.setOrtho(0, zoom * aspectRatio, zoom, 0, -10, 10);
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
}
