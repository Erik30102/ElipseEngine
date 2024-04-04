package Elipse.Core.Assets;

import java.util.UUID;

import Elipse.Core.Assets.Asset.AssetType;

public interface AssetManager {
	public Asset GetAsset(UUID id);

	public boolean IsAssetLoaded(UUID id);

	public AssetType GetAssetType(UUID id);
}
