package Elipse.Renderer.Lighting;

import java.util.List;

import Elipse.Core.Maths.Color;
import Elipse.Renderer.Opengl.Shader;

import java.util.ArrayList;

public class LightingManager {
	private List<InternalLight> lights = new ArrayList<InternalLight>();

	private Color ambientColor = new Color(255, 255, 255);
	private float ambientStrength = 1f;
	private static LightingManager INSTANCE;

	public LightingManager() {
		INSTANCE = this;
	}

	public static void Init() {
		INSTANCE = new LightingManager();
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
		shader.loadVector3("ambientColor", this.ambientColor.toShaderVector());
		shader.loadFloat("ambientStrenght", ambientStrength);

		shader.loadInt("lightCount", lights.size());

		for (int i = 0; i < lights.size(); i++) {
			lights.get(i).Bind(shader, i);
		}
	}

	public void setAmbientColor(Color color) {
		this.ambientColor = color;
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public float getAmbientStrength() {
		return ambientStrength;
	}

	public void setAmbientStrength(float strength) {
		this.ambientStrength = strength;
	}
}
