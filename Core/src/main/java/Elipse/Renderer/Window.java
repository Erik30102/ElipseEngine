package Elipse.Renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import Elipse.Core.Logger;
import Elipse.Core.EventSystem.IEventListener;
import Elipse.Core.EventSystem.Events.CharEvent;
import Elipse.Core.EventSystem.Events.KeyPressedEvent;
import Elipse.Core.EventSystem.Events.KeyReleasedEvent;
import Elipse.Core.EventSystem.Events.MouseButtonPressedEvent;
import Elipse.Core.EventSystem.Events.MouseButtonReleasedEvent;
import Elipse.Core.EventSystem.Events.MouseMovedEvent;
import Elipse.Core.EventSystem.Events.MouseScrollEvent;
import Elipse.Core.EventSystem.Events.WindowCloseEvent;
import Elipse.Core.EventSystem.Events.WindowResizeEvent;

public class Window {
	private int width, height;
	private String title;
	private long windowPtr;
	// private boolean vsync;

	private IEventListener EventCallback;

	/**
	 * @param width  The width of the window
	 * 
	 * @param height The height of the window
	 * 
	 * @param title  The title of the window
	 * 
	 * @param vsync  if vsync should be enabled
	 */
	public Window(int width, int height, String title, boolean vsync, IEventListener EventCallback) {
		this.width = width;
		this.height = height;
		this.title = title;
		// this.vsync = vsync;

		this.EventCallback = EventCallback;

		Logger.c_info(
				"Creating Window: " + title + " Of Size: " + this.width + ":" + this.height + " Vsync: " + (vsync ? "true"
						: "false"));

		if (!GLFW.glfwInit()) {
			Logger.c_error("Couldn't Initialize GLFW Window");
		}

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE); // TODO: varaibility on window hints

		this.windowPtr = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);
		if (this.windowPtr == 0) {
			Logger.c_error("Unable to create GLFW Window");
		}
		GLFW.glfwMakeContextCurrent(this.windowPtr);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(this.windowPtr);
		Logger.c_info("GLFW Window created");

		GL.createCapabilities();

		this.SetupCallbacks();
	}

	private void SetupCallbacks() {
		GLFW.glfwSetErrorCallback((error, description) -> {
			Logger.c_error("GLFW Error " + error + ": " + description);
		});

		GLFW.glfwSetWindowSizeCallback(this.windowPtr, (window, width, height) -> {
			this.height = height;
			this.width = width;

			WindowResizeEvent event = new WindowResizeEvent(width, height);
			EventCallback.OnEvent(event);
		});

		GLFW.glfwSetKeyCallback(this.windowPtr, (w, key, scancode, action, mods) -> {
			switch (action) {
				case GLFW.GLFW_PRESS: {
					KeyPressedEvent event = new KeyPressedEvent(key, false);
					EventCallback.OnEvent(event);
				}
					break;
				case GLFW.GLFW_RELEASE: {
					KeyReleasedEvent event = new KeyReleasedEvent(key);
					EventCallback.OnEvent(event);
				}
					break;
				case GLFW.GLFW_REPEAT: {
					KeyPressedEvent event = new KeyPressedEvent(key, true);
					EventCallback.OnEvent(event);
				}
					break;
			}
		});

		GLFW.glfwSetWindowCloseCallback(this.windowPtr, (window) -> {
			WindowCloseEvent event = new WindowCloseEvent();
			EventCallback.OnEvent(event);
		});

		GLFW.glfwSetMouseButtonCallback(this.windowPtr, (w, button, action, mods) -> {
			switch (action) {
				case GLFW.GLFW_PRESS: {
					MouseButtonPressedEvent event = new MouseButtonPressedEvent(button);
					EventCallback.OnEvent(event);
				}
					break;
				case GLFW.GLFW_RELEASE: {
					MouseButtonReleasedEvent event = new MouseButtonReleasedEvent(button);
					EventCallback.OnEvent(event);
				}
					break;
			}
		});

		GLFW.glfwSetScrollCallback(this.windowPtr, (w, xOffset, yOffset) -> {
			MouseScrollEvent event = new MouseScrollEvent(xOffset, yOffset);
			EventCallback.OnEvent(event);
		});

		GLFW.glfwSetCursorPosCallback(this.windowPtr, (window, xPos, yPos) -> {
			MouseMovedEvent event = new MouseMovedEvent(xPos, yPos);
			EventCallback.OnEvent(event);
		});

		GLFW.glfwSetCharCallback(this.windowPtr, (w, c) -> {
			CharEvent event = new CharEvent(c);
			EventCallback.OnEvent(event);
		});
	}

	// TODO: these things

	public void SetFullscreen() {
		GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
	}

	public void SetBorderlessWindowed() {
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

	}

	public void SetWindowed() {
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
	}

	/**
	 * @return The height of the window
	 */
	public int GetHeight() {
		return this.height;
	}

	/**
	 * @return The width of the window
	 */
	public int GetWidth() {
		return this.width;
	}

	/**
	 * Swap buffer and Poll events
	 * has to be called every frame
	 */
	public void OnUpdate() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.windowPtr);
	}

	/**
	 * Shuts down the window by destroying the window pointer.
	 */
	public void Shutdown() {
		GLFW.glfwDestroyWindow(this.windowPtr);
	}

	/**
	 * @return get the windowPtr
	 */
	public long GetPtr() {
		return this.windowPtr;
	}
}
