package Elipse.Core.ECS.BuiltIn.RenderSystem;

import Elipse.Core.Assets.Asset;
import Elipse.Core.ECS.Component;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class SpriteRenderComponent implements Component {
	private Texture2D texture;

	public SpriteRenderComponent() {
		// TODO: Non texture as default
	}

	public Texture2D getTexture() {
		return texture;
	}

	public void SetTexture(Asset getAsset) {
		this.texture = (Texture2D) getAsset;
	}

}
