package Elipse.Core.Input;

import java.nio.DoubleBuffer;

import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import Elipse.Core.Application;

public class Input {
	private static Input instance = new Input();
	private Vector2d lastMousePosition = new Vector2d();
	private Vector2d deltaMouse = new Vector2d();

	/**
	 * @param KeyCode Key
	 * @return If the given Key Is Pressed
	 */
	public static boolean IsKeyPressed(int KeyCode) {
		return instance.IsKeyPressed_implemenation(KeyCode);
	}

	/**
	 * @param KeyCode Mouse Key
	 * @return If the given Mouse Button Is Pressed
	 */
	public static boolean IsMouseButtonPressed(int KeyCode) {
		return instance.IsMouseButtonPressed_implemenation(KeyCode);
	}

	public static void Update() {
		instance.Update_implementation();
	}

	public static Vector2d GetDeltaMouse() {
		return instance.deltaMouse;
	}

	/**
	 * @return The Current Absolute X Position of the Mouse
	 */
	public static float GetMousePosX() {
		return instance.GetMousePosX_implemenation();
	}

	/**
	 * @return The Current Absolute Y Position of the Mouse
	 */
	public static float GetMousePosY() {
		return instance.GetMousePosY_implemenation();
	}

	/**
	 * @return The Mouse Position in a Vector2
	 */
	public static Vector2d GetMousePos() {
		return instance.GetMousePos_implemenation();
	}

	private void Update_implementation() {
		deltaMouse = lastMousePosition.sub(GetMousePos());

		lastMousePosition = GetMousePos();
	}

	/*
	 * Actuall Implementation for the Getter of the Y Mouse pos
	 */
	private float GetMousePosX_implemenation() {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(Application.getApplication().GetWindow().GetPtr(), posX, null);

		return (float) posX.get(0);
	}

	/*
	 * Actuall Implementation for the Getter of the Mouse pos
	 */
	private Vector2d GetMousePos_implemenation() {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(Application.getApplication().GetWindow().GetPtr(), posX, posY);

		return new Vector2d(posX.get(0), posY.get(0));
	}

	/*
	 * Actuall Implementation for the Getter of the X Mouse pos
	 */
	private float GetMousePosY_implemenation() {
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(Application.getApplication().GetWindow().GetPtr(), null, posY);

		return (float) posY.get(0);
	}

	/*
	 * Actuall Implementation for Checking if Mouse Button is Pressed
	 */
	private boolean IsMouseButtonPressed_implemenation(int keyCode) {
		int state = GLFW.glfwGetMouseButton(Application.getApplication().GetWindow().GetPtr(), keyCode);
		return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT;
	}

	/*
	 * Actuall Implementation for Checking if Key is Pressed
	 */
	private boolean IsKeyPressed_implemenation(int KeyCode) {
		int state = GLFW.glfwGetKey(Application.getApplication().GetWindow().GetPtr(), KeyCode);
		return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT;
	}
}
