package Elipse.Core.Assets;

import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public abstract class Asset {
	public enum AssetType {
		@SerializedName("NONE")
		NONE(0),

		@SerializedName("SCENE")
		SCENE(1),

		@SerializedName("TEXTURE2D")
		TEXTURE2D(2);

		private int id;

		AssetType(int id) {
			this.id = id;
		}

		public int GetId() {
			return id;
		}
	}

	public static AssetType GetAssetTypeFromString(String type) {
		switch (type) {
			case "SCENE":
				return AssetType.SCENE;
			case "TEXTURE2D":
				return AssetType.TEXTURE2D;
			default:
				return AssetType.NONE;
		}
	}

	protected UUID id;

	public UUID GetAssetHandel() {
		return id;
	}

	public abstract AssetType GetAssetType();
}
