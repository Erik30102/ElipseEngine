package Elipse.Core.Assets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Project.Project;
import Elipse.Utils.Serializer.AssetBankSerializer;
import Elipse.Utils.Serializer.UUIDSerializer;

public class EditorAssetManager implements AssetManager {

	public Map<String, Asset> loadedAssets = new HashMap<>();
	public Map<String, AssetMetaData> assetMap = new HashMap<>();

	public EditorAssetManager() {
		AssetImporter.Init();
	}

	/**
	 * Returns the asset with the given UUID and loades it if it isn't loaded
	 * already
	 * 
	 * @param <T> Type of the asset
	 * @param id  UUID of the object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Asset> T GetAsset(UUID id) {
		if (this.IsAssetLoaded(id)) {
			return (T) loadedAssets.get(id.toString());
		}

		if (this.IsUUIDValid(id)) {
			AssetMetaData metaData = assetMap.get(id.toString());
			Asset asset = AssetImporter.ImportAsset(id, metaData);
			if (asset != null) {
				asset.id = id;
				loadedAssets.put(id.toString(), asset);
				return (T) asset;
			}
		}

		Logger.c_warn("Asset with ID " + id.toString() + " was not loaded or dosnt exist");

		return null;
	}

	private boolean IsUUIDValid(UUID id) {
		return assetMap.containsKey(id.toString());
	}

	public UUID ImportAsset(String path) {
		AssetType type = Asset.GetTypeFromPath(path);
		AssetMetaData metaData = new AssetMetaData(type, new File(path).getPath());

		UUID id = UUID.randomUUID();

		Asset asset = AssetImporter.ImportAsset(id, metaData);
		if (asset != null) {
			asset.id = id;
			loadedAssets.put(id.toString(), asset);
			assetMap.put(id.toString(), metaData);
			SerializeAssetBank();

			return id;
		}
		return null;
	}

	public boolean IsAssetImported(String path) {
		for (AssetMetaData metaData : assetMap.values()) {
			if (metaData.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}

	public List<AssetMetaData> GetAssetsFromType(AssetType assetType) {
		List<AssetMetaData> assets = new ArrayList<AssetMetaData>();

		for (Map.Entry<String, AssetMetaData> entry : assetMap.entrySet()) {
			if (entry.getValue().getType() == assetType) {
				assets.add(entry.getValue());
			}
		}

		return assets;
	}

	@Override
	public boolean IsAssetLoaded(UUID id) {
		return loadedAssets.containsKey(id.toString());
	}

	@Override
	public AssetType GetAssetType(UUID id) {
		if (assetMap.containsKey(id.toString())) {
			return assetMap.get(id.toString()).getType();
		}

		return AssetType.NONE;
	}

	public void SerializeAssetBank() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting()
				.registerTypeAdapter(UUID.class, new UUIDSerializer()).create();
		String json = gson.toJson(this.assetMap);
		Logger.c_info(json);

		try {
			Files.writeString(Path.of(Project.GetActive().GetAssetMapPath()), json);
		} catch (IOException e) {
			Logger.c_error("Could not write asset bank");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void DeserializeAssetBank() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting()
				.registerTypeAdapter(assetMap.getClass(), new AssetBankSerializer()).create();

		try {
			String json = Files.readString(Path.of(Project.GetActive().GetAssetMapPath()));
			this.assetMap = gson.fromJson(json, this.assetMap.getClass());
		} catch (IOException e) {
			Logger.c_error("Could not read asset bank");
			e.printStackTrace();
		}

	}

	public UUID GetUUIDFromMetadata(AssetMetaData metaData) {
		for (Map.Entry<String, AssetMetaData> entry : assetMap.entrySet()) {
			if (entry.getValue().getPath().equals(metaData.getPath())) {
				return UUID.fromString(entry.getKey());
			}
		}
		return null;
	}

}
