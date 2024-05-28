package Elipse.Core.Assets.Editor;

import java.util.UUID;

import Elipse.Core.Assets.Asset;

public interface IAssetImporter {
	public Asset GetAsset(UUID id, AssetMetaData metaData);

	public void SerializeAsset(String path, Asset asset);
}
