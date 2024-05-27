package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;
import Elipse.Core.Maths.Color;

public class PointLightComponentSource extends ComponentSource {

	public Color color;
	public float intensity;

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.PointLightComponent;
	}

}
