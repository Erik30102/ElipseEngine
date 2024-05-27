package Elipse.Core.Assets.AssetPacks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.Editor.AssetManager;

public class RuntimeAssetManager implements AssetManager {

	private Map<String, Asset> loadedAssets = new HashMap<>();
	private Map<String, AssetInfo> assetInfoMap = new HashMap<>();

	public RuntimeAssetManager(AssetPack assetPack) {
		RuntimeAssetImporter.Init();

		assetInfoMap = assetPack.GetAssetInfoMap();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Asset> T GetAsset(UUID id) {
		if (this.IsAssetLoaded(id)) {
			return (T) loadedAssets.get(id.toString());
		}

		if (this.IsUUIDValid(id)) {
			AssetInfo assetInfo = assetInfoMap.get(id.toString());
			Asset asset = RuntimeAssetImporter.ImportAsset(id, assetInfo);
			if (asset != null) {
				asset.SetId(id);
				loadedAssets.put(id.toString(), asset);
				return (T) asset;
			}
		}

		Logger.c_warn("Asset with ID " + id.toString() + " was not loaded or dosnt exist in assetpack");

		return null;
	}

	private boolean IsUUIDValid(UUID id) {
		return assetInfoMap.containsKey(id.toString());
	}

	@Override
	public boolean IsAssetLoaded(UUID id) {
		return loadedAssets.containsKey(id.toString());
	}

	@Override
	public AssetType GetAssetType(UUID id) {
		if (assetInfoMap.containsKey(id.toString())) {
			return assetInfoMap.get(id.toString()).getAssetType();
		}
		return AssetType.NONE;
	}

}
