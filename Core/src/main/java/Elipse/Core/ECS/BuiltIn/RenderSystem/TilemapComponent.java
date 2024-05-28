package Elipse.Core.ECS.BuiltIn.RenderSystem;

import Elipse.Core.ECS.Component;
import Elipse.Core.Tilemap.Tilemap;

public class TilemapComponent implements Component {

	private Tilemap tilemap;

	public TilemapComponent() {
		tilemap = null;
	}

	public Tilemap getTilemap() {
		return tilemap;
	}

	public void setTilemap(Tilemap tilemap) {
		this.tilemap = tilemap;
	}

}
