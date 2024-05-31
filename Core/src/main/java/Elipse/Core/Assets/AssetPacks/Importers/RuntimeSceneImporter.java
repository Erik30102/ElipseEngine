package Elipse.Core.Assets.AssetPacks.Importers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.IRuntimeAssetImporter;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.EntitySource;
import Elipse.Core.Assets.AssetPacks.AssetSources.SceneSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.BaseComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.BoxColliderComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.CameraComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.PointLightComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.RidgetbodyComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.SpriteRenderComponentSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.Componetns.TilemapComponentSource;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseSystem;
import Elipse.Core.ECS.BuiltIn.Physics.BoxColliderComponent;
import Elipse.Core.ECS.BuiltIn.Physics.PhysicsSystem;
import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.PointLightComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.TilemapComponent;
import Elipse.Core.Physics.PhyscisEngine;
import Elipse.Core.Project.Project;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class RuntimeSceneImporter implements IRuntimeAssetImporter {

	@Override
	public AssetSource Serialize(Asset asset) {
		if (asset.GetAssetType() != AssetType.SCENE) {
			Logger.c_error("Tryed serialzing a non scene asset with the scene serializer");
			return null;
		}
		SceneSource sceneSource = new SceneSource();

		Scene scene = (Scene) asset;
		HashMap<Entity, List<Component>> entities = scene.GetEntities();
		EntitySource[] entitySources = new EntitySource[entities.size()];

		for (int i = 0; i < entities.size(); i++) {
			EntitySource entitySource = new EntitySource();
			Entity entity = entities.keySet().toArray(new Entity[1])[i];

			entitySource.name = entity.GetName();
			entitySource.position = entity.transform.position;
			entitySource.scale = entity.transform.scale;
			entitySource.rotation = entity.transform.rotation;

			List<Component> components = entities.get(entity);

			List<ComponentSource> componentSources = new ArrayList<>();

			for (int j = 0; j < components.size(); j++) {
				Component component = components.get(j);

				if (component instanceof CameraComponent) {
					CameraComponentSource cameraComponentSource = new CameraComponentSource();
					cameraComponentSource.FOV = ((CameraComponent) component).GetFOV();
					cameraComponentSource.isActive = ((CameraComponent) component).isActive();
					componentSources.add(cameraComponentSource);
				} else if (component instanceof SpriteRenderComponent) {
					SpriteRenderComponentSource spriteRenderComponentSource = new SpriteRenderComponentSource();

					boolean isInternal = ((SpriteRenderComponent) component).getTexture().GetPath().equals("INTERNAL");
					if (!isInternal) {
						spriteRenderComponentSource.textureUuid = ((SpriteRenderComponent) component).getTexture().GetAssetHandel();
					}
					spriteRenderComponentSource.isInternal = isInternal;
					componentSources.add(spriteRenderComponentSource);
				} else if (component instanceof RidgedBodyComponent) {
					RidgetbodyComponentSource ridgetbodyComponentSource = new RidgetbodyComponentSource();

					ridgetbodyComponentSource.isRotationLocked = ((RidgedBodyComponent) component).IsRotationLocked();
					componentSources.add(ridgetbodyComponentSource);
				} else if (component instanceof PointLightComponent) {
					PointLightComponentSource pointLightComponentSource = new PointLightComponentSource();
					pointLightComponentSource.color = ((PointLightComponent) component).GetColor();
					pointLightComponentSource.intensity = ((PointLightComponent) component).GetIntensity();
					componentSources.add(pointLightComponentSource);
				} else if (component instanceof TilemapComponent) {
					TilemapComponentSource tilemapComponentSource = new TilemapComponentSource();
					tilemapComponentSource.tilemapID = ((TilemapComponent) component).getTilemap().GetAssetHandel();
					componentSources.add(tilemapComponentSource);
				} else if (component instanceof BoxColliderComponent) {
					BoxColliderComponentSource boxColliderComponentSource = new BoxColliderComponentSource();
					componentSources.add(boxColliderComponentSource);
				} else if (component instanceof BaseComponentWrapper) {
					BaseComponentWrapper baseComponentWrapper = (BaseComponentWrapper) component;
					BaseComponent baseComponent = baseComponentWrapper.GetComponent();
					componentSources.add(new BaseComponentSource(baseComponent));
				}
			}

			entitySource.components = componentSources.toArray(new ComponentSource[1]);

			entitySources[i] = entitySource;
		}

		sceneSource.entities = entitySources;

		return sceneSource;
	}

	@Override
	public Asset Deserialize(AssetSource source) {
		if (!(source instanceof SceneSource)) {
			Logger.c_error("The source file doesn't contain a scene");
			return null;
		}

		SceneSource sceneSource = (SceneSource) source;

		Scene scene = new Scene();
		scene.AddSystem(new BaseSystem(), new PhysicsSystem());

		for (EntitySource entitySource : sceneSource.entities) {

			Entity entity = scene.Create(entitySource.name);
			entity.transform.setPosition(entitySource.position);
			entity.transform.setScale(entitySource.scale);
			entity.transform.setRotation(entitySource.rotation);

			if (entitySource.components == null)
				continue;

			for (ComponentSource componentSource : entitySource.components) {
				switch (componentSource.GetComponentType()) {
					case CameraComponent:
						CameraComponentSource cameraComponentSource = (CameraComponentSource) componentSource;

						CameraComponent cameraComponent = new CameraComponent();
						cameraComponent.SetFOV(cameraComponentSource.FOV);
						cameraComponent.isActive = cameraComponentSource.isActive;
						entity.AddComponent(cameraComponent);
						break;
					case SpriteRenderComponent:
						SpriteRenderComponentSource spriteRenderComponentSource = (SpriteRenderComponentSource) componentSource;

						SpriteRenderComponent spriteRenderComponent = new SpriteRenderComponent();
						if (!spriteRenderComponentSource.isInternal) {
							spriteRenderComponent.SetTexture(
									(Texture2D) Project.GetActive().GetAssetManager().GetAsset(spriteRenderComponentSource.textureUuid));
						}
						entity.AddComponent(spriteRenderComponent);
						break;
					case RidgedBodyComponent:
						RidgetbodyComponentSource ridgetbodyComponentSource = (RidgetbodyComponentSource) componentSource;
						RidgedBodyComponent ridgedBodyComponent = new RidgedBodyComponent();
						ridgedBodyComponent.SetIsRotationLocked(ridgetbodyComponentSource.isRotationLocked);
						entity.AddComponent(ridgedBodyComponent);
						break;
					case BoxColliderComponent:
						entity.AddComponent(new BoxColliderComponent());
						break;
					case PointLightComponent:
						PointLightComponentSource pointLightComponentSource = (PointLightComponentSource) componentSource;

						PointLightComponent pointLightComponent = new PointLightComponent();
						pointLightComponent.SetColor(pointLightComponentSource.color);
						pointLightComponent.SetIntensity(pointLightComponentSource.intensity);
						entity.AddComponent(pointLightComponent);
						break;
					case TilemapComponent:
						TilemapComponentSource tilemapComponentSource = (TilemapComponentSource) componentSource;

						TilemapComponent t = new TilemapComponent();
						t.setTilemap(Project.GetActive().GetAssetManager().GetAsset(tilemapComponentSource.tilemapID));

						entity.AddComponent(t);
						break;
					case BaseComponent:
						BaseComponentSource baseComponentSource = (BaseComponentSource) componentSource;
						entity.AddComponent(new BaseComponentWrapper(baseComponentSource.getComponent()));
						break;
				}
			}
		}

		return scene;
	}

}
