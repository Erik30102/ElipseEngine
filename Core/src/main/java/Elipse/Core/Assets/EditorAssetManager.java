package Elipse.Core.Assets;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Project.Project;
import Elipse.Utils.Serializer.UUIDSerializer;

public class EditorAssetManager implements AssetManager {

	public HashMap<UUID, Asset> loadedAssets = new HashMap<>();
	public HashMap<UUID, AssetMetaData> assetMap = new HashMap<>();

	private HashMap<String, AssetType> extensionDir = new HashMap<>();

	public EditorAssetManager() {
		extensionDir.put(".png", AssetType.TEXTURE2D);
		extensionDir.put(".el", AssetType.SCENE);

		AssetImporter.Init();
	}

	@Override
	public Asset GetAsset(UUID id) {
		if (this.IsAssetLoaded(id)) {
			return loadedAssets.get(id);
		}

		Logger.c_warn("Asset with ID " + id.toString() + " was not loaded or dosnt exist");

		return null;
	}

	private AssetType GetTypeFromPath(String path) {
		String extension = path.substring(path.lastIndexOf('.') + 1);

		Logger.c_info("!!! NEED TO DELETE !!! AssetType: " + extension);

		if (!extensionDir.containsKey(extension)) {
			Logger.c_error("AssetType not found for URL: " + path);

			return AssetType.NONE;
		}

		return extensionDir.get(extension);
	}

	public void ImportAsset(String path) {
		AssetType type = GetTypeFromPath(path);
		AssetMetaData metaData = new AssetMetaData(type, path);

		UUID id = UUID.randomUUID();

		Asset asset = AssetImporter.ImportAsset(id, metaData);
		if (asset != null) {
			asset.id = id;
			loadedAssets.put(id, asset);
			assetMap.put(id, metaData);
			SerializeAssetBank();
		}
	}

	@Override
	public boolean IsAssetLoaded(UUID id) {
		return loadedAssets.containsKey(id);
	}

	@Override
	public AssetType GetAssetType(UUID id) {
		if (assetMap.containsKey(id)) {
			return assetMap.get(id).getType();
		}

		return AssetType.NONE;
	}

	public void SerializeAssetBank() {
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(UUID.class, new UUIDSerializer()).create();
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
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(UUID.class, new UUIDSerializer()).create();

		try {
			String json = Files.readString(Path.of(Project.GetActive().GetAssetMapPath()));
			this.assetMap = gson.fromJson(json, this.assetMap.getClass());
		} catch (IOException e) {
			Logger.c_error("Could not read asset bank");
			e.printStackTrace();
		}

	}

}
