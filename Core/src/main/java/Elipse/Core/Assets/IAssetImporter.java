package Elipse.Core.Assets;

import java.util.UUID;

public interface IAssetImporter {
	public Asset GetAsset(UUID id, AssetMetaData metaData);
}
