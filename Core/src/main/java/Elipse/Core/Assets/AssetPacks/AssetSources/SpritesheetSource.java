package Elipse.Core.Assets.AssetPacks.AssetSources;

import java.io.Serializable;
import java.util.UUID;

public class SpritesheetSource implements Serializable, AssetSource {
	public UUID textureID;
	public int width, height;
}
