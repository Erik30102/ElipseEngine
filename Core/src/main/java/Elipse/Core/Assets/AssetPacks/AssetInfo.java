package Elipse.Core.Assets.AssetPacks;

import java.io.Serializable;

import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;

public class AssetInfo implements Serializable {
	private AssetSource content;
	private AssetType assetType;

	public AssetInfo(AssetSource content, AssetType assetType) {
		this.content = content;
		this.assetType = assetType;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public AssetSource getContent() {
		return content;
	}
}