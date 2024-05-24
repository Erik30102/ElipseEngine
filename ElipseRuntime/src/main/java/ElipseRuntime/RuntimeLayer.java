package ElipseRuntime;

import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.RenderSystem.RenderSystem;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Project.Project;
import Elipse.Core.Scripting.ScriptEngine;

public class RuntimeLayer extends Layer {

	private Scene scene;
	private ScriptEngine scriptEngine;

	@Override
	public void OnAttach() {
		this.scriptEngine = new ScriptEngine();

		Project proj = Project
				.LoadProject("C:\\Users\\Administrator\\Documents\\InfoEngine\\ExampleProject\\Project.elprj");

		this.scriptEngine.LoadJar(proj.GetScriptProjectPath());

		RenderSystem renderSystem = new RenderSystem(null);

		this.scene = (Scene) proj.GetAssetManager().GetAsset(proj.GetStartScene());
		this.scene.AddSystem(renderSystem);
	}

	@Override
	public void OnEvent(Event event) {
	}

	@Override
	public void OnUpdate(double dt) {
		this.scene.OnRuntimeStep((float) dt);
	}

}
