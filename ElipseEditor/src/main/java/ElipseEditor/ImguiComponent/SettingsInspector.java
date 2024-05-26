package ElipseEditor.ImguiComponent;

import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Scene;
import Elipse.Core.Maths.Color;
import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Core.Scripting.Script.ScriptType;
import Elipse.Core.Scripting.Scripts.SystemScript;
import Elipse.Renderer.Lighting.LightingManager;
import imgui.ImGui;

public class SettingsInspector {

	private Scene scene;

	public SettingsInspector(Scene scene) {
		this.scene = scene;
	}

	public void SetScene(Scene scene) {
		this.scene = scene;
	}

	public void OnImgui() {
		ImGui.begin("Settings");

		ImGui.text("Renderer Settings");
		ImGui.columns(2);
		ImGui.text("Ambient Strenthg: ");
		ImGui.nextColumn();
		float[] r = { LightingManager.GetInstance().getAmbientStrength() };
		if (ImGui.dragFloat("##AS", r, 0.1f)) {
			LightingManager.GetInstance().setAmbientStrength(r[0]);
		}
		ImGui.nextColumn();
		ImGui.text("Ambient Color: ");
		ImGui.nextColumn();
		float[] c = { LightingManager.GetInstance().getAmbientColor().getRed()
				/ 255f,
				LightingManager.GetInstance().getAmbientColor().getGreen()
						/ 255f,
				LightingManager.GetInstance().getAmbientColor().getBlue() / 255f };
		if (ImGui.colorEdit3("##AC", c)) {
			LightingManager.GetInstance().setAmbientColor(new Color((int) (255 * c[0]), (int) (255 * c[1]),
					(int) (255 * c[2])));
		}
		ImGui.nextColumn();

		ImGui.columns(1);

		ImGui.separator();
		ImGui.text("Scene Settings");
		ImGui.text("Custom Systems");
		ImGui.separator();

		for (ECSSystem system : this.scene.GetSystems()) {
			ImGui.text(system.getClass().getSimpleName());
		}

		if (ImGui.button("Add System")) {
			ImGui.openPopup("AddSystem");
		}

		if (ImGui.beginPopup("AddSystem")) {
			for (SystemScript script : ScriptEngine.GetInstance().GetScripts(ScriptType.SYSTEM, SystemScript.class)) {
				boolean isAdded = false;

				for (ECSSystem f : this.scene.GetSystems()) {
					if (f.getClass() == script.GetBaseClazz()) {
						isAdded = true;
					}
				}

				if (!isAdded) {
					if (ImGui.selectable(script.GetBaseClazz().getSimpleName())) {
						if (!isAdded) {
							this.scene.AddSystem(script.GetScript(ECSSystem.class));
						}
					}
				}
			}
			ImGui.endPopup();
		}

		ImGui.end();
	}

}
