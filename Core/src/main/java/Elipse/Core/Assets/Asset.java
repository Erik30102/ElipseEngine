package Elipse.Core.Assets;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import Elipse.Core.ECS.Scene;
import Elipse.Core.Scripting.ScriptableObject;
import Elipse.Core.Tilemap.Tilemap;
import Elipse.Renderer.Batching.Spritesheet;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public abstract class Asset {
	public enum AssetType {
		@SerializedName("NONE")
		NONE(0),

		@SerializedName("SCENE")
		SCENE(1),

		@SerializedName("TEXTURE2D")
		TEXTURE2D(2),

		@SerializedName("SCRIPTABLEOBJ")
		SCRIPTABLEOBJ(3),

		@SerializedName("TILEMAP")
		TILEMAP(4),

		@SerializedName("SPRITESHEET")
		SPRITESHEET(5);

		private int id;

		AssetType(int id) {
			this.id = id;
		}

		public int GetId() {
			return id;
		}

		// TODO: add function to get asset fileending by asset type
	}

	// TODO: to asset helper class has no reason to be here

	private static Map<String, AssetType> extensionDir = null;

	public static AssetType GetAssetTypeFromString(String type) {
		switch (type) {
			case "SCENE":
				return AssetType.SCENE;
			case "TEXTURE2D":
				return AssetType.TEXTURE2D;
			case "SCRIPTABLEOBJ":
				return AssetType.SCRIPTABLEOBJ;
			case "TILEMAP":
				return AssetType.TILEMAP;
			case "SPRITESHEET":
				return AssetType.SPRITESHEET;
			default:
				return AssetType.NONE;
		}
	}

	private transient UUID id;

	public final void SetId(UUID id) {
		this.id = id;
	}

	public UUID GetAssetHandel() {
		return id;
	}

	public abstract AssetType GetAssetType();

	public static AssetType GetTypeFromPath(String path) {
		if (extensionDir == null) {
			extensionDir = new HashMap<>();
			extensionDir.put("png", AssetType.TEXTURE2D);
			extensionDir.put("el", AssetType.SCENE);
			extensionDir.put(".elobj", AssetType.SCRIPTABLEOBJ);
			extensionDir.put(".elsheet", AssetType.SPRITESHEET);
			extensionDir.put(".elmap", AssetType.TILEMAP);
		}

		String extension = path.substring(path.lastIndexOf('.') + 1);

		if (!extensionDir.containsKey(extension)) {
			return AssetType.NONE;
		}

		return extensionDir.get(extension);
	}

	public static AssetType GetAssetTypeFromClass(Class<? extends Asset> clazz) {
		if (clazz == Scene.class) {
			return AssetType.SCENE;
		} else if (clazz == Texture2D.class) {
			return AssetType.TEXTURE2D;
		} else if (ScriptableObject.class.isAssignableFrom(clazz)) {
			return AssetType.SCRIPTABLEOBJ;
		} else if (clazz == Tilemap.class) {
			return AssetType.TILEMAP;
		} else if (clazz == Spritesheet.class) {
			return AssetType.SPRITESHEET;
		} else {
			return AssetType.NONE;
		}
	}
}
