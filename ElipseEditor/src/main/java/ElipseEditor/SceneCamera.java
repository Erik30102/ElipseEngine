package ElipseEditor;

import org.joml.Vector2d;

import Elipse.Core.Logger;
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

	public void Resize(int width, int height) {
		camera.Resize(width, height);
	}

	public void OnUpdate() {
		if (Input.IsKeyPressed(KeyCode.EL_KEY_LEFT_ALT)) {
			if (Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_1)) {
				Vector2d delta = Input.GetDeltaMouse();

				position.add((float) delta.x * 0.02f, (float) -delta.y * 0.02f);
				this.camera.Move(position);
			}
		}
	}

	public OrthograhicCamera GetCamera() {
		return this.camera;
	}
}
