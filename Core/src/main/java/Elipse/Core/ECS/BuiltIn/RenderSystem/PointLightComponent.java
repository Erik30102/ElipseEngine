package Elipse.Core.ECS.BuiltIn.RenderSystem;

import Elipse.Core.ECS.Component;
import Elipse.Core.Maths.Color;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.Lighting.PointLight;

public class PointLightComponent implements Component {

	private PointLight pointLight = new PointLight(new Vector(), new Color(), 1);

	public void SetColor(Color color) {
		this.pointLight.setColor(color);
	}

	public void SetIntensity(float intensity) {
		this.pointLight.setIntensity(intensity);
	}

	public float GetIntensity() {
		return this.pointLight.getIntensity();
	}

	public Color GetColor() {
		return this.pointLight.getColor();
	}

	public void SetPosition(Vector position) {
		this.pointLight.setPosition(position.getX(), position.getY());
	}
}
