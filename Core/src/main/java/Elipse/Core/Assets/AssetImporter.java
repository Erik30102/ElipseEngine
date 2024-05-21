package Elipse.Core.Assets;

import java.util.HashMap;
import java.util.UUID;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.Importers.SceneImporter;
import Elipse.Core.Assets.Importers.Texture2DImporter;

public class AssetImporter {
	private static HashMap<AssetType, IAssetImporter> importers;

	// TODO: !!!!IMPORTANT!!!! UUID IS NOT SET INTERNALLY WHEN LOADING AN ASSET HAS
	// TO BE FIXED

	// INFO: i think this is already fixxed but still the whole asset system seems a
	// bit shakey at the moment has to be looked at

	public static void Init() {
		importers = new HashMap<>();
		importers.put(AssetType.SCENE, new SceneImporter());
		importers.put(AssetType.TEXTURE2D, new Texture2DImporter());
	}

	public static Asset ImportAsset(UUID id, AssetMetaData metaData) {
		if (!importers.containsKey(metaData.getType())) {
			Logger.c_warn("Failed to import Asset " + metaData.getPath());

			return null;
		}

		return importers.get(metaData.getType()).GetAsset(id, metaData);
	}
}
