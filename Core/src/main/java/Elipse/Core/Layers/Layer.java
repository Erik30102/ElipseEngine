package Elipse.Core.Layers;

import Elipse.Core.EventSystem.Events.Event;

public abstract class Layer {

	/**
	 * Sends every event to layer if set handeld to true it dosnt get passed to
	 * next layer
	 * 
	 * @param event
	 */
	public abstract void OnEvent(Event event);

	/**
	 * Gets Called When appending a this layer
	 */
	public abstract void OnAttach();

	/**
	 * Gets called every Frame
	 * 
	 * @param dt delta time from last frame
	 */
	public abstract void OnUpdate(double dt);

	/**
	 * For Rendering ImGui Objects
	 * ImGui objects can only be rendered in this method
	 */
	public void OnImguiRender() {

	}
}