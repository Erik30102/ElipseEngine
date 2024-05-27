package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;

public class CameraComponentSource extends ComponentSource {

	public float FOV;
	public boolean isActive;

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.CameraComponent;
	}

}
