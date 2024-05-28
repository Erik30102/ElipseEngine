package Elipse.Renderer.Batching;

import Elipse.Core.Maths.Vector;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class Sprite {
	private Texture2D texture;
	private Vector[] uv;

	public Sprite(Texture2D texture, Vector[] uv) {
		this.texture = texture;
		this.uv = uv;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public Vector[] getUv() {
		return uv;
	}
}
