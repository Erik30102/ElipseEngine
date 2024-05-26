package Sandbox;

import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.TilemapComponent;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class RoomScript extends BaseComponent {

	private transient boolean started = false;
	public Texture2D walltexture;
	public Texture2D floortexture;

	@Override
	public void OnUpdate(float dt) {
		if (!started) {
			TilemapComponent tilemap = this.entity.GetComponent(TilemapComponent.class);

			if (tilemap == null)
				return;

			tilemap.ResetAndResize(30, 30);
			for (int x = 0; x < 30; x++) {
				for (int y = 0; y < 30; y++) {
					if ((x == 0 || y == 0 || x == 29 || y == 29))
						tilemap.SetTile(x, y, walltexture);
					else
						tilemap.SetTile(x, y, floortexture);
				}
			}

			started = true;
		}

	}

}
