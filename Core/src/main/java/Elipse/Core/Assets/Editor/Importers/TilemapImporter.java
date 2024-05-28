package Elipse.Core.Assets.Editor.Importers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Editor.AssetMetaData;
import Elipse.Core.Assets.Editor.IAssetImporter;
import Elipse.Core.Tilemap.Tilemap;

public class TilemapImporter implements IAssetImporter {

	@Override
	public Asset GetAsset(UUID id, AssetMetaData metaData) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Tilemap.class, new TilemapImporter()).create();

		try {
			return gson.fromJson(Files.readString(Path.of(metaData.getPath())), Tilemap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void SerializeAsset(String path, Asset asset) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'SerializeAsset'");
	}

}
