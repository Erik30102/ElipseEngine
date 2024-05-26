package Elipse.Core.Assets.AssetPacks;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;

public interface IRuntimeAssetImporter {
	public AssetSource Serialize(Asset asset);

	public Asset Deserialize(AssetSource source);
}
