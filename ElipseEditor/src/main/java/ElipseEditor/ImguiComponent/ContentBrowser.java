package ElipseEditor.ImguiComponent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
				if (ImGui.button(file.getName(), size, size)) {
					currentPath = file.getPath();
				}
			} else {
				ImGui.button(file.getName(), size, size);
			}
			ImGui.nextColumn();
		}
		ImGui.columns(1);

		ImGui.end();
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
