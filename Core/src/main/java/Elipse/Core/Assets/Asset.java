package Elipse.Core.Assets;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import Elipse.Core.ECS.Scene;
import Elipse.Core.Scripting.ScriptableObject;
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
		SCRIPTABLEOBJ(3);

		private int id;

		AssetType(int id) {
			this.id = id;
		}

		public int GetId() {
			return id;
		}
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
			default:
				return AssetType.NONE;
		}
	}

	protected transient UUID id;

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
		} else {
			return AssetType.NONE;
		}
	}
}
