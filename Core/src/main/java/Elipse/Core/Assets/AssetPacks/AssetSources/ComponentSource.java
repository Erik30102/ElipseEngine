package Elipse.Core.Assets.AssetPacks.AssetSources;

import java.io.Serializable;

public abstract class ComponentSource implements Serializable {
	public enum ComponentType {
		SpriteRenderComponent, CameraComponent,
		RidgedBodyComponent, BoxColliderComponent,
		PointLightComponent, TilemapComponent, BaseComponent
	}

	public abstract ComponentType GetComponentType();
}
