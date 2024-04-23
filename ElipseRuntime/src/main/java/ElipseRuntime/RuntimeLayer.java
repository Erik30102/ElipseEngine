package ElipseRuntime;

import Elipse.Core.ECS.Scene;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Project.Project;

public class RuntimeLayer extends Layer {

	private Scene scene;

	@Override
	public void OnAttach() {
		Project proj = Project
				.LoadProject("C:\\Users\\Administrator\\Documents\\InfoEngine\\ExampleProject\\Project.elprj");

		this.scene = (Scene) proj.GetAssetManager().GetAsset(proj.GetStartScene());
	}

	@Override
	public void OnEvent(Event event) {

	}

	@Override
	public void OnUpdate(double dt) {
		this.scene.OnRuntimeStep((float) dt);
	}

}
