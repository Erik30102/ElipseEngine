package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;

public class RidgetbodyComponentSource extends ComponentSource {

	public boolean isRotationLocked;

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.RidgedBodyComponent;
	}

}
