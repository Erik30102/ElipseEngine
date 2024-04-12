package Elipse.Core.Assets;

import java.util.UUID;

import Elipse.Core.Assets.Asset.AssetType;

public interface AssetManager {
	public <T extends Asset> T GetAsset(UUID id);

	public boolean IsAssetLoaded(UUID id);

	public AssetType GetAssetType(UUID id);
}
