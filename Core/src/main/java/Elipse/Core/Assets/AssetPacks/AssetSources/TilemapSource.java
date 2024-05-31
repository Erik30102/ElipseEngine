package Elipse.Core.Assets.AssetPacks.AssetSources;

import java.util.UUID;
import java.io.Serializable;

public class TilemapSource implements AssetSource, Serializable {
	public int width, height;
	public UUID texturID;
	public int[] tiles;
}
