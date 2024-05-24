package Elipse.Core.ECS.BuiltIn.RenderSystem;

import java.util.List;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Transform;
import Elipse.Renderer.OrthograhicCamera;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.Renderer2D;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Utils.Pair;

public class RenderSystem extends ECSSystem {

	private Framebuffer fbo;

	private OrthograhicCamera camera;

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

		for (Pair<Entity, Component> pair : CameraComponents) {
			CameraComponent camera = (CameraComponent) pair.getValue();
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

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);
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

		Renderer2D.BeginScene(camera);

		List<Pair<Entity, Component>> components = scene.GetComponents(SpriteRenderComponent.class);

		if (components != null) {
			for (Pair<Entity, Component> component : components) {
				SpriteRenderComponent SpriteRenderComp = (SpriteRenderComponent) component.getValue();
				Transform transform = component.getKey().transform;

				Renderer2D.DrawSprite(SpriteRenderComp.getTexture(), transform);
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
		this.camera = camera;
	}

}
