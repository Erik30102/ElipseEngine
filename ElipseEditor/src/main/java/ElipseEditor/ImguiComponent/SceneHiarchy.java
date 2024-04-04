package ElipseEditor.ImguiComponent;

import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Scene;
import imgui.ImGui;

public class SceneHiarchy {

	private Scene scene;
	private InspectorView inspectorView;

	public SceneHiarchy(Scene scene) {
		this.scene = scene;

		inspectorView = new InspectorView(scene);
	}

	public void SetScene(Scene scene) {
		this.scene = scene;

		inspectorView.SetScene(scene);
	}

	public void OnImgui() {
		ImGui.begin("Scene Hiarchy");

		int index = 0;

		for (Entity entity : scene.GetEntities().keySet()) {
			ImGui.pushID(index);

			if (ImGui.selectable(entity.GetName())) {
				inspectorView.SetEntity(entity);
			}

			ImGui.popID();
			index++;
		}

		ImGui.end();

		inspectorView.OnImgui();
	}
}
