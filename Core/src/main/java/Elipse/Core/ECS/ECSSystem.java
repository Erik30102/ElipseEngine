package Elipse.Core.ECS;

public abstract class ECSSystem {

	protected Scene scene;

	/**
	 * !!INTERNAL!! used for setting the scene of the system by the Scene itself
	 * when attaching to it
	 * 
	 * DO NOT CALL
	 * 
	 * @param scene the scene to attach the system to
	 */
	final void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Called when the system is started and added to the scene independently if in
	 * editor or runtime
	 */
	public void OnStart() {
	}

	/**
	 * Remove all resources used by the system
	 */
	public void OnRuntimeDispose() {
	}

	/**
	 * Remove all resources used by the system which are not needed for displaying
	 */
	public void OnEditorDispose() {

	}

	/**
	 * the gameplay loop of the game all aspects of the game which should be in the
	 * final game should be written in here
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public void OnRuntimeStep(float dt) {
	}

	/**
	 * The game loop in the editor when not currently playing just use for stuff
	 * like displaying bounding boxes or debug renderes
	 * 
	 * 
	 * @param dt the time delta between the last frame and this frame
	 */
	public void OnEditorStep(float dt) {
	}
}
