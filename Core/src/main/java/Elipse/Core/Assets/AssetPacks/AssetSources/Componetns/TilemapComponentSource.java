package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;

import java.util.UUID;

public class TilemapComponentSource extends ComponentSource {

	public UUID tilemapID;

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.TilemapComponent;
	}

}
