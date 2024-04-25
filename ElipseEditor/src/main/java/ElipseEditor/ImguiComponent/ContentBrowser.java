package ElipseEditor.ImguiComponent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.EditorAssetManager;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Project.Project;
import imgui.ImGui;

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

	public void OnImgui() {
		ImGui.begin("Content Browser");

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
				if (ImGui.button("Folder", size, size)) {
					currentPath = file.getPath();
				}
				ImGui.text(file.getName());
			} else {
				HandelFile(file);
			}
			ImGui.nextColumn();
		}
		ImGui.columns(1);

		ImGui.end();
	}

	private void HandelFile(File file) {
		AssetType type = Asset.GetTypeFromPath(file.getPath());

		ImGui.pushID(file.getPath());
		if (ImGui.button(type != AssetType.NONE ? type.name() : "NF", size, size)) {
			if (type != AssetType.NONE) {
				EditorAssetManager assetManager = (EditorAssetManager) Project.GetActive().GetAssetManager();

				if (!assetManager.IsAssetImported(file.getPath())) {
					assetManager.ImportAsset(file.getPath());
				}
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
