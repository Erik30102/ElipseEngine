package Elipse.Core.ECS.BuiltIn.RenderSystem;

import org.joml.Matrix4f;

import Elipse.Core.ECS.Component;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.OrthograhicCamera;

public class CameraComponent implements Component {
	public float FOV;

	public boolean isActive = true;

	private transient OrthograhicCamera orthograhicCamera;

	public CameraComponent() {
		orthograhicCamera = new OrthograhicCamera();

		FOV = 5f;

		this.Resize(1, 1);
	}

	/**
	 * Resize camera and the projection matrix to fit the new aspect ratio
	 * 
	 * @param width  widht of the window
	 * @param height height of the window
	 */
	public void Resize(int width, int height) {
		orthograhicCamera.Resize(width, height);
	}

	/**
	 * Recalculate View Matrix based on camera position
	 * 
	 * @param positon the position of the camera
	 */
	public void adjustViewMatrix(Vector positon) {
		orthograhicCamera.Move(positon);
	}

	/**
	 * @return wheter the this camera is the active one
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @return the projection matrix used for later rendering
	 */
	public Matrix4f GetProjection() {
		return orthograhicCamera.GetProjection();
	}

	/**
	 * @return the view matrix used for later rendering
	 */
	public Matrix4f GetView() {
		return orthograhicCamera.GetView();
	}

	/**
	 * TODO: implement proper FOV
	 * 
	 * @param f the factor of which by it FOVs
	 */
	public void SetFOV(float f) {
		FOV = f;

		orthograhicCamera.SetFOV(f);
	}

	/**
	 * @return the current FOV
	 */
	public float GetFOV() {
		return FOV;
	}

	/**
	 * @param futur whether the camera should be active
	 */
	public void SetActive(boolean futur) {
		isActive = futur;
	}

	/**
	 * @return the internal represnettation of a camera
	 */
	public OrthograhicCamera GetCamera() {
		return orthograhicCamera;
	}
}
