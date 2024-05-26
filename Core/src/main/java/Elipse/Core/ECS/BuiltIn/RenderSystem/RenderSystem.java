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
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.Renderer2D;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Utils.Pair;

public class RenderSystem extends ECSSystem {

	private Framebuffer fbo;

	private OrthograhicCamera scenecamera;
	private List<RenderBatch> renderBatch = new ArrayList<>();

	public RenderSystem(Framebuffer fbo) {
		super();

		this.fbo = fbo;

		Renderer2D.Init();

		renderBatch.add(new RenderBatch(20000));
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

		renderBatch.get(0).Begin();

		List<Pair<Entity, Component>> components = scene.GetComponents(SpriteRenderComponent.class);

		// TODO: check if the spritebatch has the texture and if it has still use that

		int batchIndex = 0;

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				// Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);

				if (renderBatch.get(batchIndex).hasRoom() && renderBatch.get(batchIndex).hasRoomTextures()) {
					renderBatch.get(batchIndex).AddSprite(SpriteRenderComp.getTexture(), transform);
				} else {
					batchIndex++;
					if (renderBatch.get(batchIndex) == null) {
						renderBatch.add(new RenderBatch(20000));
					}
					renderBatch.get(batchIndex).Begin();
					renderBatch.get(batchIndex).AddSprite(SpriteRenderComp.getTexture(), transform);
				}
			}
		}

		List<Pair<Entity, Component>> TilemapComponent = scene.GetComponents(TilemapComponent.class);
		if (TilemapComponent != null) {
			for (Pair<Entity, Component> pair : TilemapComponent) {
				TilemapComponent tilemap = (TilemapComponent) pair.getValue();
				Transform transform = pair.getKey().transform;

				Texture2D[] grid = tilemap.GetGrid();

				if (grid == null)
					continue;

				for (int i = 0; i < grid.length; i++) {
					int x = i % tilemap.GetWidth();
					int y = (int) Math.floor(i / tilemap.GetWidth());

					if (renderBatch.get(batchIndex).hasRoom() && renderBatch.get(batchIndex).hasRoomTextures()) {
						renderBatch.get(batchIndex).AddSprite(grid[i], new Vector(x, y).add(transform.position));
					} else {
						batchIndex++;
						if (renderBatch.get(batchIndex) == null) {
							renderBatch.add(new RenderBatch(20000));
						}
						renderBatch.get(batchIndex).Begin();
						renderBatch.get(batchIndex).AddSprite(grid[i], new Vector(x, y).add(transform.position));
					}
				}
			}
		}

		for (RenderBatch batch : renderBatch) {
			batch.reloadData();
			batch.render(camera.GetView(), camera.GetProjection());
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

		renderBatch.get(0).Begin();
		int batchIndex = 0;

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				// Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);

				if (renderBatch.get(batchIndex).hasRoom() && renderBatch.get(batchIndex).hasRoomTextures()) {
					renderBatch.get(batchIndex).AddSprite(SpriteRenderComp.getTexture(), transform);
				} else {
					batchIndex++;
					if (renderBatch.get(batchIndex) == null) {
						renderBatch.add(new RenderBatch(20000));
					}
					renderBatch.get(batchIndex).Begin();
					renderBatch.get(batchIndex).AddSprite(SpriteRenderComp.getTexture(), transform);
				}
			}
		}

		List<Pair<Entity, Component>> TilemapComponent = scene.GetComponents(TilemapComponent.class);
		if (TilemapComponent != null) {
			for (Pair<Entity, Component> pair : TilemapComponent) {
				TilemapComponent tilemap = (TilemapComponent) pair.getValue();
				Transform transform = pair.getKey().transform;

				Texture2D[] grid = tilemap.GetGrid();

				if (grid == null)
					continue;

				for (int i = 0; i < grid.length; i++) {
					int x = i % tilemap.GetWidth();
					int y = (int) Math.floor(i / tilemap.GetWidth());

					if (renderBatch.get(batchIndex).hasRoom() && renderBatch.get(batchIndex).hasRoomTextures()) {
						renderBatch.get(batchIndex).AddSprite(grid[i], new Vector(x, y).add(transform.position));
					} else {
						batchIndex++;
						if (renderBatch.get(batchIndex) == null) {
							renderBatch.add(new RenderBatch(20000));
						}
						renderBatch.get(batchIndex).Begin();
						renderBatch.get(batchIndex).AddSprite(grid[i], new Vector(x, y).add(transform.position));
					}
				}
			}
		}

		for (RenderBatch batch : renderBatch) {
			batch.reloadData();
			batch.render(scenecamera.GetView(), scenecamera.GetProjection());
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
