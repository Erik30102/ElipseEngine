package ElipseEditor;

import Elipse.Core.Application;
import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseSystem;
import Elipse.Core.ECS.BuiltIn.RenderSystem.RenderSystem;
import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.EventSystem.Events.KeyPressedEvent;
import Elipse.Core.EventSystem.Events.Event.EventType;
import Elipse.Core.Input.KeyCode;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Project.Project;
import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.Shader;
import ElipseEditor.ImguiComponent.SceneHiarchy;
import ElipseEditor.test.TestComponent;
import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;

public class EditorLayer extends Layer {

	private Scene scene;

	private static EditorLayer INSTANCE;

	private SceneHiarchy sceneHiarchy;
	private ScriptEngine scriptEngine;

	private Framebuffer fbo;

	private Class<? extends Component>[] components;

	@SuppressWarnings("unchecked")
	@Override
	public void OnAttach() {
		components = new Class[] { SpriteRenderComponent.class, BaseComponentWrapper.class };

		fbo = new Framebuffer(300, 200);

		scriptEngine = new ScriptEngine();

		INSTANCE = this;

		// Project aproj = new
		// Project("C:\\Users\\Administrator\\Documents\\InfoEngine\\ExampleProject",
		// "test proj");

		Project proj = Project
				.LoadProject("C:\\Users\\Administrator\\Documents\\InfoEngine\\ExampleProject\\Project.elprj");

		this.scene = (Scene) proj.GetAssetManager().GetAsset(proj.GetStartScene());
		sceneHiarchy = new SceneHiarchy(scene);
	}

	@Override
	public void OnUpdate(double dt) {
		scene.step((float) dt);
	}

	@Override
	public void OnImguiRender() {
		sceneHiarchy.OnImgui();

		ImGui.beginMainMenuBar();

		if (ImGui.beginMenu("File")) {
			if (ImGui.menuItem("Load Jar")) {
				ImGuiFileDialog.openModal("browse-key", "Choose File", ".jar", ".", null, 1, 1, 42,
						ImGuiFileDialogFlags.None);
			}

			ImGui.endMenu();
		}

		ImGui.endMainMenuBar();

		if (ImGuiFileDialog.display("browse-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
			if (ImGuiFileDialog.isOk()) {
				Logger.c_info("trying to load project jar: " + ImGuiFileDialog.getSelection().values().toArray()[0]);

				this.scriptEngine.LoadJar((String) ImGuiFileDialog.getSelection().values().toArray()[0]);
			}
			ImGuiFileDialog.close();
		}

		ImGui.begin("Debug Menu");

		for (BaseComponent baseComponent : this.scriptEngine.GetComponents()) {
			ImGui.text(baseComponent.getClass().getName());
		}

		ImGui.dragInt("Texture id", texId, 1, 1, 20);
		ImGui.image(texId[0], 200, 200, 0, 1, 1, 0);

		ImGui.end();

		ImGui.begin("viewport");

		ImGui.image(fbo.GetTexture().GetTextureId(), 300, 200, 0, 1, 1, 0);

		ImGui.end();
	}

	private int[] texId = { 1 };

	@Override
	public void OnEvent(Event event) {
		if (event.GetEventType() == EventType.KeyPressed) {
			if (((KeyPressedEvent) event).GetKeyCode() == KeyCode.EL_KEY_F) {
				Application.getApplication().GetWindow().SetFullscreen();
			}
		}
	}

	public static EditorLayer GetEditor() {
		return INSTANCE;
	}

	public ScriptEngine GetScriptEngine() {
		return scriptEngine;
	}

	public Class<? extends Component>[] GetComponents() {
		return components;
	}
}
