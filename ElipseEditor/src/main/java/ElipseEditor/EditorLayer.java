package ElipseEditor;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Elipse.Core.Application;
import Elipse.Core.Logger;
import Elipse.Core.Assets.AssetPacks.AssetPack;
import Elipse.Core.Assets.AssetPacks.RuntimeAssetImporter;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.Physics.BoxColliderComponent;
import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.PointLightComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.RenderSystem;
import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.TilemapComponent;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.EventSystem.Events.KeyPressedEvent;
import Elipse.Core.EventSystem.Events.Event.EventType;
import Elipse.Core.Input.KeyCode;
import Elipse.Core.Layers.Layer;
import Elipse.Core.Project.Project;
import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Utils.Pair;
import ElipseEditor.ImguiComponent.AssetPicker;
import ElipseEditor.ImguiComponent.ContentBrowser;
import ElipseEditor.ImguiComponent.SceneHiarchy;
import ElipseEditor.ImguiComponent.SettingsInspector;
import ElipseEditor.ImguiComponent.TilemapEditor;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class EditorLayer extends Layer {

	private enum SceneState {
		PLAYING, EDITING
	}

	private SceneState sceneState = SceneState.EDITING;

	private SceneCamera sceneCamera;

	private Scene scene;

	private static EditorLayer INSTANCE;

	private SettingsInspector settingsInspector;
	private SceneHiarchy sceneHiarchy;
	private ScriptEngine scriptEngine;
	private ContentBrowser contentBrowser;

	private TilemapEditor tilemapEditor;

	private Framebuffer fbo;
	private int fboWidth = 200, fboHeight = 200;

	private Class<? extends Component>[] components;

	private RenderSystem renderSystem;

	private boolean TileEditor = false;

	@SuppressWarnings("unchecked")
	@Override
	public void OnAttach() {
		INSTANCE = this;

		components = new Class[] { SpriteRenderComponent.class, CameraComponent.class, RidgedBodyComponent.class,
				BoxColliderComponent.class, PointLightComponent.class, TilemapComponent.class };

		fbo = new Framebuffer(200, 200);

		scriptEngine = new ScriptEngine();

		Project proj = Project
				.LoadProject("C:\\Users\\Administrator\\Documents\\InfoEngine\\ExampleProject\\Project.elprj");

		this.scriptEngine.LoadJar(proj.GetScriptProjectPath());

		sceneCamera = new SceneCamera();

		renderSystem = new RenderSystem(fbo);
		renderSystem.SetCamera(this.sceneCamera.GetCamera());

		this.scene = (Scene) proj.GetAssetManager().GetAsset(proj.GetStartScene());
		this.scene.AddSystem(renderSystem);

		sceneHiarchy = new SceneHiarchy(scene);
		contentBrowser = new ContentBrowser();
		settingsInspector = new SettingsInspector(scene);

		tilemapEditor = new TilemapEditor();

		AssetPicker.Init();
	}

	@Override
	public void OnUpdate(double dt) {
		switch (this.sceneState) {
			case EDITING:
				sceneCamera.OnUpdate();
				scene.OnEditorStep((float) dt);
				break;

			case PLAYING:
				scene.OnRuntimeStep((float) dt);
				break;
		}
	}

	@Override
	public void OnImguiRender() {
		sceneHiarchy.OnImgui();
		if (TileEditor)
			tilemapEditor.OnImGui();
		contentBrowser.OnImgui();
		settingsInspector.OnImgui();

		ImGui.beginMainMenuBar();

		if (ImGui.beginMenu("File")) {
			if (ImGui.menuItem("Load Jar")) {
				ImGuiFileDialog.openModal("browse-key", "Choose File", ".jar", ".", null, 1, 1, 42,
						ImGuiFileDialogFlags.None);
			}

			if (ImGui.menuItem("Save project")) {
				Project.GetActive().Save();
			}

			if (ImGui.menuItem("Load project")) {
				// TODO:
			}

			ImGui.endMenu();
		}

		if (ImGui.beginMenu("Windows")) {
			if (ImGui.menuItem("Tilemap Editor", null, TileEditor)) {
				TileEditor = !TileEditor;
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

		ImGui.dragInt("Texture id", texId, 1, 1, 20);
		ImGui.image(texId[0], 200, 200, 0, 1, 1, 0);

		// DEBUG DEBUG DEBUG DEBUG DEBGU DEBUG DEBUG

		if (ImGui.button("test function")) {
			RuntimeAssetImporter.Init();
			AssetPack.SaveToDisk("assetpack.ep", AssetPack.BuildFromCurrentProject());
		}

		if (ImGui.button("testa")) {

		}

		ImGui.end();

		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
		ImGui.begin("##viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoCollapse);

		ImVec2 windowSize = new ImVec2();
		ImGui.getContentRegionAvail(windowSize);

		if (windowSize.x != fboWidth || windowSize.y != fboHeight) {
			fboWidth = (int) windowSize.x;
			fboHeight = (int) windowSize.y;

			this.sceneCamera.Resize(fboWidth, fboHeight);
			fbo.Resize(fboWidth, fboHeight);

			List<Pair<Entity, Component>> cameras = scene.GetComponents(CameraComponent.class);

			if (cameras != null)
				cameras.forEach(c -> ((CameraComponent) c.getValue()).Resize(fboWidth, fboHeight));
		}

		ImGui.image(fbo.GetTexture().GetTextureId(), windowSize.x, windowSize.y, 0, 1, 1, 0);

		// #region ImGuizmo

		Entity activeEntity = this.sceneHiarchy.GetInspector().GetEntity();
		if (activeEntity != null && this.sceneState == SceneState.EDITING) {

			ImGuizmo.setOrthographic(true);
			ImGuizmo.setDrawList();
			ImGuizmo.setRect(ImGui.getWindowPosX(), ImGui.getWindowPosY(), ImGui.getWindowSizeX(), ImGui.getWindowSizeY());

			Matrix4f cameraView = sceneCamera.GetCamera().GetView();
			Matrix4f cameraProj = sceneCamera.GetCamera().GetProjection();

			Matrix4f trans = new Matrix4f().identity()
					.translate(activeEntity.transform.position.getX(), activeEntity.transform.position.getY(), 0)
					.scale(activeEntity.transform.scale.getX(), activeEntity.transform.scale.getY(), 0)
					.rotateZ((float) Math.toRadians(activeEntity.transform.rotation));

			float[] _trans = new float[16];

			ImGuizmo.manipulate(cameraView.get(new float[16]), cameraProj.get(new float[16]), trans.get(_trans),
					1 << 0 | 1 << 1 | 1 << 3 | 1 << 4 | 1 << 7 | 1 << 8, 1);

			if (ImGuizmo.isUsing()) {
				trans = new Matrix4f().set(_trans);

				Vector3f pos = new Vector3f();
				Vector3f scale = new Vector3f();

				trans.getTranslation(pos);
				trans.getScale(scale);

				activeEntity.transform.setScale(scale.x, scale.y);
				activeEntity.transform.setPosition(pos.x, pos.y);
			}
		}
		// #endregion

		ImGui.end();
		ImGui.popStyleVar();

		ImGui.begin("##runornot",
				ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoScrollbar);

		if (ImGui.button(this.sceneState == SceneState.EDITING ? "play" : "stop")) {
			if (this.sceneState == SceneState.EDITING) {
				this.sceneState = SceneState.PLAYING;
			} else if (this.sceneState == SceneState.PLAYING) {
				this.scene.OnDisposeEditor();
				this.sceneState = SceneState.EDITING;
			}
		}

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
		if (event.GetEventType() == EventType.WindowClose) {
			Editor.SetShouldBeRunning(false);
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
