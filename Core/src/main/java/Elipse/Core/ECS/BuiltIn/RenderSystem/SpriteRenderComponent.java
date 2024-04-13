package Elipse.Core.ECS.BuiltIn.RenderSystem;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import Elipse.Core.Assets.Asset;
import Elipse.Core.ECS.Component;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFiltering;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFormat;
import Elipse.Renderer.Opengl.Texture.Texture.TextureWrapMode;

public class SpriteRenderComponent implements Component {
	private Texture2D texture;

	public SpriteRenderComponent() {
		// TODO: Non texture as default
		texture = new Texture2D(1, 1, TextureFormat.RGB8, TextureFiltering.NEAREST, TextureWrapMode.CLAMP_TO_BORDER);
		IntBuffer ib = BufferUtils.createIntBuffer(1);
		ib.put(new int[] {
				0xFF00FF
		}).rewind();

		texture.SetData(ib);
	}

	public Texture2D getTexture() {
		return texture;
	}

	public void SetTexture(Asset getAsset) {
		this.texture = (Texture2D) getAsset;
	}

}
