package Elipse.Core.Assets.AssetPacks;

import java.io.Serializable;

import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.AssetPack.HEADERS;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;

public class AssetInfo implements Serializable {
	private HEADERS header;
	private AssetSource content;
	private AssetType assetType;

	public AssetInfo(HEADERS header, AssetSource content, AssetType assetType) {
		this.header = header;
		this.content = content;
		this.assetType = assetType;
	}

	public HEADERS getHeader() {
		return header;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public AssetSource getContent() {
		return content;
	}
}