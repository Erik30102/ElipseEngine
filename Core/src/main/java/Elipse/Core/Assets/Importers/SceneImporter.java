package Elipse.Core.Assets.Importers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.AssetMetaData;
import Elipse.Core.Assets.IAssetImporter;
import Elipse.Core.ECS.Scene;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Utils.Serializer.AssetSerializer;
import Elipse.Utils.Serializer.LocalSceneSerializer;

public class SceneImporter implements IAssetImporter {

	@Override
	public Asset GetAsset(UUID id, AssetMetaData metaData) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Texture2D.class, new AssetSerializer())
				.registerTypeAdapter(Scene.class, new LocalSceneSerializer()).create();

		try {
			return gson.fromJson(Files.readString(Path.of(metaData.getPath())), Scene.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
