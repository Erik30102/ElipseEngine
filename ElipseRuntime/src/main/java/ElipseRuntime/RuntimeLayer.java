package ElipseRuntime;

import java.util.List;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.RenderSystem;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.EventSystem.Events.WindowResizeEvent;
import Elipse.Core.EventSystem.Events.Event.EventType;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Project.Project;
import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Utils.Pair;

public class RuntimeLayer extends Layer {

	private Scene scene;
	private ScriptEngine scriptEngine;

	@Override
	public void OnAttach() {
		this.scriptEngine = new ScriptEngine();

		Project proj = Project.LoadFromAssetPack("assetpack.ep");

		this.scriptEngine.LoadJar(
				"C:\\Users\\Administrator\\Documents\\InfoEngine\\Sandbox\\build\\libs\\Sandbox-1.0-SNAPSHOT.jar");

		RenderSystem renderSystem = new RenderSystem(null);

		this.scene = (Scene) proj.GetAssetManager().GetAsset(proj.GetStartScene());
		this.scene.AddSystem(renderSystem);
	}

	@Override
	public void OnEvent(Event event) {
		if (event.GetEventType() == EventType.WindowResize) {
			WindowResizeEvent w = ((WindowResizeEvent) event);

			List<Pair<Entity, Component>> cameras = scene.GetComponents(CameraComponent.class);

			if (cameras != null)
				cameras.forEach(c -> ((CameraComponent) c.getValue()).Resize(w.GetWidth(), w.GetHeight()));

			RendererApi.SetViewport(w.GetWidth(), w.GetHeight());
		}
	}

	@Override
	public void OnUpdate(double dt) {
		this.scene.OnRuntimeStep((float) dt);
	}

}
