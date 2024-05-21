package Elipse.Renderer.Lighting;

import Elipse.Core.Maths.Color;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.Opengl.Shader;

public class PointLight extends InternalLight {

	private Color color;
	private float intensity;

	public PointLight(Vector position, Color color, float intensity) {
		this.position = position;
		this.color = color;
		this.intensity = intensity;

		LightingManager.GetInstance().AddLight(this);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getIntensity() {
		return intensity;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void Bind(Shader shader, int slot) {
		shader.loadVector3("pointLights[" + slot + "].color", color.toShaderVector());
		shader.loadVector2("pointLights[" + slot + "].position", position);
		shader.loadFloat("pointLights[" + slot + "].brightness", intensity);
	}

}
