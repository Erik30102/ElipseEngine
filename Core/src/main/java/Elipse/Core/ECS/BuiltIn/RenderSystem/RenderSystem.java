package Elipse.Core.ECS.BuiltIn.RenderSystem;

import java.util.List;
import java.util.ArrayList;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Transform;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.OrthograhicCamera;
import Elipse.Renderer.Batching.RenderBatch;
import Elipse.Renderer.Batching.Spritesheet;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.Renderer2D;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Utils.Pair;

public class RenderSystem extends ECSSystem {

	private Framebuffer fbo;

	private OrthograhicCamera scenecamera;

	public RenderSystem(Framebuffer fbo) {
		super();

		this.fbo = fbo;

		Renderer2D.Init();
	}

	@Override
	public void OnRuntimeStep(float dt) {
		List<Pair<Entity, Component>> PointLights = scene.GetComponents(PointLightComponent.class);

		if (PointLights != null) {
			for (Pair<Entity, Component> pair : PointLights) {
				PointLightComponent pointLight = (PointLightComponent) pair.getValue();

				pointLight.SetPosition(pair.getKey().transform.position);
			}
		}

		List<Pair<Entity, Component>> CameraComponents = scene.GetComponents(CameraComponent.class);

		if (CameraComponents == null)
			return;

		CameraComponent camera = null;

		for (Pair<Entity, Component> pair : CameraComponents) {
			camera = (CameraComponent) pair.getValue();
			if (camera.isActive()) {
				Transform transform = pair.getKey().transform;

				camera.adjustViewMatrix(transform.position);

				if (fbo != null) {
					fbo.Bind();
					RendererApi.SetViewport(fbo.GetWidth(), fbo.GetHeight());
				}

				Renderer2D.BeginScene(camera.GetCamera());

				break;
			}
		}

		List<Pair<Entity, Component>> components = scene.GetComponents(SpriteRenderComponent.class);

		// TODO: check if the spritebatch has the texture and if it has still use that

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);
			}
		}

		List<Pair<Entity, Component>> TilemapComponent = scene.GetComponents(TilemapComponent.class);
		if (TilemapComponent != null) {
			for (Pair<Entity, Component> pair : TilemapComponent) {
				TilemapComponent tilemap = (TilemapComponent) pair.getValue();
				Transform transform = pair.getKey().transform;

				if (tilemap.getTilemap() == null)
					continue;

				Spritesheet spritesheet = tilemap.getTilemap().GetSpritesheet();

				for (int x = 0; x < tilemap.getTilemap().GetWidth(); x++) {
					for (int y = 0; y < tilemap.getTilemap().GetHeight(); y++) {
						Renderer2D.AddSprite(spritesheet.getSprite(tilemap.getTilemap().GetTile(x, y)),
								new Vector(x, y).add(transform.position));
					}
				}
			}
		}

		Renderer2D.EndScene();

		if (fbo != null)
			fbo.Unbind();

	}

	@Override
	public void OnEditorStep(float dt) {
		// LIGHTING

		List<Pair<Entity, Component>> PointLights = scene.GetComponents(PointLightComponent.class);

		if (PointLights != null) {
			for (Pair<Entity, Component> pair : PointLights) {
				PointLightComponent pointLight = (PointLightComponent) pair.getValue();

				pointLight.SetPosition(pair.getKey().transform.position);
			}
		}

		fbo.Bind();
		RendererApi.SetViewport(fbo.GetWidth(), fbo.GetHeight());

		Renderer2D.BeginScene(scenecamera);

		List<Pair<Entity, Component>> components = scene.GetComponents(SpriteRenderComponent.class);

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);
			}
		}

		List<Pair<Entity, Component>> TilemapComponent = scene.GetComponents(TilemapComponent.class);
		if (TilemapComponent != null) {
			for (Pair<Entity, Component> pair : TilemapComponent) {
				TilemapComponent tilemap = (TilemapComponent) pair.getValue();
				Transform transform = pair.getKey().transform;

				if (tilemap.getTilemap() == null)
					continue;

				Spritesheet spritesheet = tilemap.getTilemap().GetSpritesheet();

				for (int x = 0; x < tilemap.getTilemap().GetWidth(); x++) {
					for (int y = 0; y < tilemap.getTilemap().GetHeight(); y++) {
						Renderer2D.AddSprite(spritesheet.getSprite(tilemap.getTilemap().GetTile(x, y)),
								new Vector(x, y).add(transform.position));
					}
				}
			}
		}

		Renderer2D.EndScene();

		fbo.Unbind();
	}

	/**
	 * Sets the camera for rendering in the editor
	 * 
	 * @param camera editor camera
	 */
	public void SetCamera(OrthograhicCamera camera) {
		this.scenecamera = camera;
	}

}
