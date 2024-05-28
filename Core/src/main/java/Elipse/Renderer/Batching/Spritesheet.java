package Elipse.Renderer.Batching;

import java.util.ArrayList;
import java.util.List;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class Spritesheet extends Asset {
	private Texture2D texture;
	private List<Sprite> sprites;

	private int SpriteWidth;
	private int SpriteHeight;

	public Spritesheet(Texture2D texture2d, int SpriteWidth, int SpriteHeight) {
		this.texture = texture2d;
		this.sprites = new ArrayList<Sprite>();

		this.SpriteHeight = SpriteHeight;
		this.SpriteWidth = SpriteWidth;

		for (int y = 0; y < texture.GetHeight() - SpriteHeight; y += SpriteHeight) {
			for (int x = 0; x < texture.GetWidth() - SpriteWidth; x += SpriteWidth) {
				Vector uv0 = new Vector(x / (float) texture.GetWidth(), y / (float) texture.GetHeight());
				Vector uv1 = new Vector((x + SpriteWidth) / (float) texture.GetWidth(),
						(y + SpriteHeight) / (float) texture.GetHeight());
				Vector uv2 = new Vector((x + SpriteWidth) / (float) texture.GetWidth(), y / (float) texture.GetHeight());
				Vector uv3 = new Vector(x / (float) texture.GetWidth(), (y + SpriteHeight) / (float) texture.GetHeight());
				sprites.add(new Sprite(texture, new Vector[] { uv0, uv1, uv2, uv3 }));
			}
		}
	}

	public List<Sprite> getSprites() {
		return sprites;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public int getSpriteWidth() {
		return SpriteWidth;
	}

	public int getSpriteHeight() {
		return SpriteHeight;
	}

	@Override
	public AssetType GetAssetType() {
		return AssetType.SPRITESHEET;
	}

	public Sprite getSprite(int getTile) {
		return sprites.get(getTile);
	}
}
