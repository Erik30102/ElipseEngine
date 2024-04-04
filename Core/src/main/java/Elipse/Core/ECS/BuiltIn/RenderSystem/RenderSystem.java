package Elipse.Core.ECS.BuiltIn.RenderSystem;

import java.util.List;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Transform;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.Renderer2D;
import Elipse.Utils.Pair;

public class RenderSystem extends ECSSystem {

	private Framebuffer fbo;

	public RenderSystem(Framebuffer fbo) {
		super();

		this.fbo = fbo;

		Renderer2D.Init();
	}

	@Override
	public void step(float dt) {

		List<Pair<Entity, Component>> components = scene.GetComponents(SpriteRenderComponent.class);

		fbo.Bind();

		Renderer2D.BeginScene();
		Renderer2D.DrawQuad();

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

}
