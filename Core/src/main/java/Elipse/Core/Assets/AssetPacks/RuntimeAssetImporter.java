package Elipse.Core.Assets.AssetPacks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;
import Elipse.Core.Assets.AssetPacks.Importers.RuntimeSceneImporter;
import Elipse.Core.Assets.AssetPacks.Importers.RuntimeTextureImporter;

public class RuntimeAssetImporter {
	private static Map<AssetType, IRuntimeAssetImporter> importers = new HashMap<AssetType, IRuntimeAssetImporter>();

	public static void Init() {
		importers.put(AssetType.SCENE, new RuntimeSceneImporter());
		importers.put(AssetType.TEXTURE2D, new RuntimeTextureImporter());
	}

	public static Asset ImportAsset(UUID id, AssetInfo assetInfo) {
		if (!importers.containsKey(assetInfo.getAssetType())) {
			Logger.c_warn("Failed to find importer for asset type " + assetInfo.getAssetType());

			return null;
		}

		return importers.get(assetInfo.getAssetType()).Deserialize(assetInfo.getContent());
	}

	public static AssetSource GetAssetInfo(Asset asset) {
		if (!importers.containsKey(asset.GetAssetType())) {
			Logger.c_warn("Failed to find importer for asset type " + asset.GetAssetType());

			return null;
		}

		return importers.get(asset.GetAssetType()).Serialize(asset);
	}
}
