package ElipseEditor.ImguiComponent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Elipse.Core.Assets.Asset;
import Elipse.Core.ECS.Scene;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.Editor.EditorAssetManager;
import Elipse.Core.Project.Project;
import Elipse.Core.Scripting.Script;
import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Core.Scripting.Script.ScriptType;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Utils.Serializer.LocalSceneSerializer;
import ElipseEditor.Utils.SerializingHelper;
import imgui.ImGui;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class ContentBrowser {
	public String Path;

	private Map<String, File[]> content = new HashMap<>();

	private String currentPath;
	private String AssetDir;

	public ContentBrowser() {
		currentPath = new File(Project.GetActive().GetAssetDir()).getPath();
		AssetDir = new File(Project.GetActive().GetAssetDir()).getPath();

		Refresh();
	}

	private static int size = 64;
	// Create modal
	private ImString filename = new ImString();

	public void OnImgui() {
		if (ImGui.beginPopupModal("##assetmodal", new ImBoolean(true),
				ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoTitleBar)) {
			ImGui.inputText("Filename", filename);

			if (ImGui.button("Create")) {
				// TODO: dynamic
				SerializingHelper.SaveToPath(currentPath + "/" + filename.get() + ".el",
						SerializingHelper.Serialize(Scene.class, new LocalSceneSerializer(), new Scene()));
			}
			ImGui.endPopup();
		}

		ImGui.begin("Content Browser");

		if (ImGui.beginPopup("CreateNewAsset")) {
			if (ImGui.selectable("New Scene")) {
				ImGui.openPopup("##assetmodal");
			}

			ImGui.separator();
			ImGui.text("Scritable Objects");
			ImGui.separator();
			for (Script script : ScriptEngine.GetInstance().GetScripts()) {
				if (script.GetScriptType() == ScriptType.SCRIPTABLEOBJ) {
					if (ImGui.selectable(script.GetBaseClazz().getSimpleName())) {

					}
				}
			}

			ImGui.endPopup();
		}

		if (ImGui.button("Refresh")) {
			Refresh();
		}

		if (!currentPath.equals(AssetDir)) {
			if (ImGui.button("Back")) {
				currentPath = new File(currentPath).getParent();
			}
		}

		int columCount = (int) (ImGui.getContentRegionAvailX() / (size + 16));
		ImGui.columns(columCount == 0 ? 1 : columCount, "##contentBrowser", false);

		for (File file : content.get(currentPath)) {
			if (file.isDirectory()) {
				ImGui.pushID(file.getPath());
				if (ImGui.button("Folder", size, size)) {
					currentPath = file.getPath();
				}
				ImGui.popID();
				ImGui.text(file.getName());
			} else {
				HandelFile(file);
			}
			ImGui.nextColumn();
		}
		ImGui.columns(1);

		if (ImGui.isMouseClicked(ImGuiMouseButton.Right) && ImGui.isWindowHovered()) {
			ImGui.openPopup("CreateNewAsset");
		}

		ImGui.end();
	}

	private void HandelFile(File file) {
		AssetType type = Asset.GetTypeFromPath(file.getPath());
		EditorAssetManager assetManager = (EditorAssetManager) Project.GetActive().GetAssetManager();

		ImGui.pushID(file.getPath());
		if (type != AssetType.TEXTURE2D && !assetManager.IsAssetImported(file.getPath())) {
			if (ImGui.button(type != AssetType.NONE ? type.name() : "NF", size, size)) {
				if (type != AssetType.NONE) {

					if (!assetManager.IsAssetImported(file.getPath())) {
						assetManager.ImportAsset(file.getPath());
					}
				}
			}
		} else { // TODO: rework
			if (ImGui.imageButton(((Texture2D) assetManager.GetAssetFromPath(file.getPath())).GetTextureId(), size, size)) {

			}
		}
		ImGui.popID();

		ImGui.text(file.getName());
	}

	private void Refresh() {
		AddToContent(Project.GetActive().GetAssetDir());
	}

	private void AddToContent(String path) {
		File folder = new File(path);

		content.put(new File(path).getPath(), folder.listFiles());

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				AddToContent(fileEntry.getPath());
			}
		}
	}
}
