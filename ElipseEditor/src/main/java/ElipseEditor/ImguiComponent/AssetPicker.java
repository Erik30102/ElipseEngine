package ElipseEditor.ImguiComponent;

import java.util.List;
import java.util.ArrayList;

import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.AssetMetaData;
import Elipse.Core.Assets.EditorAssetManager;
import Elipse.Core.Project.Project;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class AssetPicker {

	private static AssetPicker INSTANCE;

	private String key = "";
	private boolean showDialog = false;
	private AssetType type;

	private Asset selected;

	private List<AssetMetaData> ChashedAssets = new ArrayList<>();

	public AssetPicker() {
		INSTANCE = this;
	}

	public static void Init() {
		new AssetPicker();
	}

	public static boolean Display(String key) {
		if (INSTANCE.key.equals(key) && INSTANCE.showDialog) {
			ImGui.openPopup(key);

			if (ImGui.beginPopupModal(key, new ImBoolean(true), ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoTitleBar)) {

				int columCount = (int) (ImGui.getContentRegionAvailX() / (80));
				ImGui.columns(columCount == 0 ? 1 : columCount, "##assetPicker", false);

				for (AssetMetaData metaData : INSTANCE.ChashedAssets) {
					if (ImGui.button(metaData.getPath(), 64, 64)) {
						INSTANCE.selected = Project.GetActive().GetAssetManager()
								.GetAsset(((EditorAssetManager) Project.GetActive().GetAssetManager()).GetUUIDFromMetadata(metaData));

						INSTANCE.showDialog = false;
						ImGui.endPopup();

						return true;
					}
				}

				ImGui.endPopup();
			}

		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Asset> T GetSelected(Class<T> clazz) {
		if (INSTANCE.selected.getClass() != clazz) {
			Logger.c_warn("Selected asset is not of type " + clazz.getSimpleName() + " Edior bug please report!");
			return null;
		}

		return (T) INSTANCE.selected;
	}

	public static void Open(String key, AssetType type) {
		INSTANCE.key = key;
		INSTANCE.showDialog = true;
		INSTANCE.type = type;
		INSTANCE.selected = null;

		INSTANCE.ChashedAssets = ((EditorAssetManager) Project.GetActive().GetAssetManager())
				.GetAssetsFromType(INSTANCE.type);
	}
}
