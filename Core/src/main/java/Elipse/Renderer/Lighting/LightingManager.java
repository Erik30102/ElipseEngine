package Elipse.Renderer.Lighting;

import java.util.List;

import Elipse.Renderer.Opengl.Shader;

import java.util.ArrayList;

public class LightingManager {
	private List<InternalLight> lights = new ArrayList<InternalLight>();

	private static LightingManager INSTANCE;

	public LightingManager() {
		INSTANCE = this;
	}

	/**
	 * @return the current instance of the lighting manager in a static way
	 */
	public static LightingManager GetInstance() {
		return INSTANCE;
	}

	/**
	 * Adds a light to the lighting manager
	 *
	 * @param light the light to add
	 */
	public void AddLight(InternalLight light) {
		lights.add(light);
	}

	/**
	 * Binds the given shader and loads the lights into the shader.
	 *
	 * @param shader the shader to bind
	 */
	public void Bind(Shader shader) {
		shader.loadInt("lightCount", lights.size());

		for (int i = 0; i < lights.size(); i++) {
			lights.get(i).Bind(shader, i);
		}
	}
}
