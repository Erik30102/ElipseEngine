package Elipse.Core.ECS.BuiltIn.RenderSystem;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Elipse.Core.ECS.Component;
import Elipse.Core.Maths.Vector;

// TODO: abstraction to seperate camera class

public class CameraComponent implements Component {
	public float zoom;

	public boolean isActive = true;

	private transient Matrix4f projectionMatrix;
	private transient Matrix4f viewMatrix;

	public CameraComponent() {
		zoom = 5f;

		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();

		this.Resize(1, 1);
	}

	public void Resize(int w, int h) {
		float aspectRatio = (float) w / h;

		float width = zoom * 0.5f * aspectRatio;
		float height = zoom / 2;

		projectionMatrix.identity();
		projectionMatrix.ortho(-width, width, -height, height, 0f, 100f);
	}

	public void adjustViewMatrix(Vector positon) {
		Vector3f cameraFront = new Vector3f(0, 0, -1);
		Vector3f cameraUp = new Vector3f(0, 1, 0);

		viewMatrix.identity();
		viewMatrix = viewMatrix.lookAt(
				new Vector3f(
						positon.x,
						positon.y, 20),
				cameraFront.add(positon.x, positon.y, 0), cameraUp);
	}

	public boolean isActive() {
		return isActive;
	}

	public Matrix4f GetProjection() {
		return projectionMatrix;
	}

	public Matrix4f GetView() {
		return viewMatrix;
	}

	public void SetZoom(float f) {
		zoom = f;
	}

	public float GetZoom() {
		return zoom;
	}

	public void SetActive(boolean b) {
		isActive = b;
	}
}
