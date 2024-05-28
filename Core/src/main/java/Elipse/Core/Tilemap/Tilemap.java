package Elipse.Core.Tilemap;

import Elipse.Core.Assets.Asset;
import Elipse.Renderer.Batching.Sprite;
import Elipse.Renderer.Batching.Spritesheet;

public class Tilemap extends Asset {

	private Spritesheet spritesheet;
	private int width, height;
	private int[] tiles;

	public Tilemap(Spritesheet spritesheet, int width, int height, int[] tiles) {
		this.spritesheet = spritesheet;
		this.width = width;
		this.height = height;
		this.tiles = tiles;
	}

	public Tilemap(Spritesheet spritesheet, int width, int height) {
		this.spritesheet = spritesheet;
		this.width = width;
		this.height = height;

		this.tiles = new int[width * height];
	}

	public void SetTile(int x, int y, int tile) {
		tiles[y * width + x] = tile;
	}

	public Spritesheet GetSpritesheet() {
		return spritesheet;
	}

	public int GetWidth() {
		return width;
	}

	public int GetHeight() {
		return height;
	}

	public int[] GetTiles() {
		return tiles;
	}

	@Override
	public AssetType GetAssetType() {
		return AssetType.TILEMAP;
	}

	public int GetTile(int x, int y) {
		return tiles[y * width + x];
	}

}
