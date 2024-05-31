package Elipse.Core;

import org.lwjgl.glfw.GLFW;

import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.EventSystem.Events.WindowCloseEvent;
import Elipse.Core.EventSystem.Events.WindowResizeEvent;
import Elipse.Core.Input.Input;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Layers.LayerStack;
import Elipse.Renderer.Window;

public abstract class Application {

	private static Application INSTANCE;

	private boolean running = true;
	private Window window;
	private LayerStack layerStack;

	public Application() {
		this(900, 600, "Elipse Engine", true);
	}

	public Application(int width, int height, String title, boolean imgui) {
		INSTANCE = this;

		Logger.InitLogger();
		window = new Window(width, height, title, true, event -> this.OnEvent(event));

		layerStack = new LayerStack(imgui);

		OnInitialize();

		this.OnEvent(new WindowResizeEvent(width, height));
	}

	/**
	 * add all the layers here
	 */
	public abstract void OnInitialize();

	private double lastFrametime = 1;

	/**
	 * Runs the application loop
	 */
	public void Run() {
		while (running) {
			double dt = GLFW.glfwGetTime() - lastFrametime;
			lastFrametime = GLFW.glfwGetTime();

			Input.Update();

			layerStack.OnUpdate(dt);
			layerStack.OnImguiRender();

			window.OnUpdate();
		}
	}

	/**
	 * @return the Application class
	 */
	public static Application getApplication() {
		return INSTANCE;
	}

	/**
	 * Pushes a layer onto the layer stack and calls its OnAttach method.
	 *
	 * @param layer The layer to be pushed onto the stack
	 */
	public void PushLayer(Layer layer) {
		layer.OnAttach();
		layerStack.addLayer(layer);
	}

	private void OnEvent(Event event) {
		switch (event.GetEventType()) {
			case WindowClose:
				this.OnCloseEvent((WindowCloseEvent) event);
				break;
			default:
				break;
		}

		layerStack.OnEvent(event);
	}

	private void OnCloseEvent(WindowCloseEvent event) {
		Stop();
	}

	/**
	 * Stops the execution of the game
	 */
	public void Stop() {
		running = false;
		this.window.Shutdown();
	}

	/**
	 * @return The window class
	 */
	public Window GetWindow() {
		return this.window;
	}

}
