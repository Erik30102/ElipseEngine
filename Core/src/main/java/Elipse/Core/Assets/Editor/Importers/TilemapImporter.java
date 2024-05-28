package Elipse.Core.Assets.Editor.Importers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Editor.AssetMetaData;
import Elipse.Core.Assets.Editor.IAssetImporter;
import Elipse.Core.Tilemap.Tilemap;
import Elipse.Utils.Serializer.LocalTilemapSerializer;

public class TilemapImporter implements IAssetImporter {

	@Override
	public Asset GetAsset(UUID id, AssetMetaData metaData) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Tilemap.class, new LocalTilemapSerializer()).create();

		try {
			return gson.fromJson(Files.readString(Path.of(metaData.getPath())), Tilemap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void SerializeAsset(String path, Asset asset) {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Tilemap.class, new LocalTilemapSerializer())
				.create();

		String json = gson.toJson(asset);
		try {
			Files.writeString(Path.of(path), json);
		} catch (IOException e) {
			Logger.c_error("Could not write asset to path: " + path);
			e.printStackTrace();
		}
	}

}
