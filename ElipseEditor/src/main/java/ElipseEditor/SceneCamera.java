package ElipseEditor;

import org.joml.Vector2d;

import Elipse.Core.Input.Input;
import Elipse.Core.Input.KeyCode;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.OrthograhicCamera;

public class SceneCamera {
	private OrthograhicCamera camera;

	private Vector position;

	public SceneCamera() {
		camera = new OrthograhicCamera();
		position = new Vector(0, 0);

		this.Resize(1, 1);
		this.camera.Move(new Vector());
	}

	/**
	 * Resizes the projection matrix to fit the aspect ratio
	 * 
	 * @param width  the width of the viewport
	 * @param height the height of the viewport
	 */
	public void Resize(int width, int height) {
		camera.Resize(width, height);
	}

	/**
	 * Gets called every frame to update the camera and for it to move on input
	 */
	public void OnUpdate() {
		if (Input.IsKeyPressed(KeyCode.EL_KEY_LEFT_ALT)) {
			if (Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_1)) {
				Vector2d delta = Input.GetDeltaMouse();

				position.add((float) delta.x * 0.02f, (float) -delta.y * 0.02f);
				this.camera.Move(position);
			}
		}
	}

	/**
	 * @return the Internal camera used for rendering
	 */
	public OrthograhicCamera GetCamera() {
		return this.camera;
	}
}
