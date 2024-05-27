package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import java.util.UUID;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;

public class SpriteRenderComponentSource extends ComponentSource {

	public boolean isInternal;
	public UUID textureUuid;

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.SpriteRenderComponent;
	}

}
