package Elipse.Core.Assets;

import Elipse.Core.Assets.Asset.AssetType;

public class AssetMetaData {
	private AssetType type;
	private String path;

	public AssetMetaData(AssetType type, String path) {
		this.type = type;
		this.path = path;
	}

	public AssetType getType() {
		return this.type;
	}

	public String getPath() {
		return this.path;
	}
}
