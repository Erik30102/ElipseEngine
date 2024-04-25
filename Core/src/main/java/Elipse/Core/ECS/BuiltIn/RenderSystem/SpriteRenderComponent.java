package Elipse.Core.ECS.BuiltIn.RenderSystem;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

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

	/**
	 * Returns the texture of the sprite renderer.
	 *
	 * @return the texture of the sprite renderer
	 */
	public Texture2D getTexture() {
		return texture;
	}

	/**
	 * Sets the texture of the sprite renderer to new texture
	 * 
	 * @param texture the texture to set the sprite renderer to
	 */
	public void SetTexture(Texture2D texture) {
		this.texture = texture;
	}

}
