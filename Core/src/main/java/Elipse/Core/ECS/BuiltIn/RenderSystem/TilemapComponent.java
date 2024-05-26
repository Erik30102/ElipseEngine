package Elipse.Core.ECS.BuiltIn.RenderSystem;

import Elipse.Core.ECS.Component;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class TilemapComponent implements Component {

	private Texture2D[] grid;

	private int height, width;

	public TilemapComponent() {

	}

	public TilemapComponent(Texture2D[] grid, int width, int height) {
		this.grid = grid;
		this.height = height;
		this.width = width;
	}

	public TilemapComponent(int width, int height) {
		this.grid = new Texture2D[width * height];
		this.height = height;
		this.width = width;
	}

	public void ResetAndResize(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Texture2D[width * height];
	}

	public void SetTile(int x, int y, Texture2D tile) {
		grid[y * width + x] = tile;
	}

	public Texture2D[] GetGrid() {
		return grid;
	}

	public int GetWidth() {
		return width;
	}

	public int GetHeight() {
		return height;
	}
}
