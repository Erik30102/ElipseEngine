package Elipse.Core.Assets.AssetPacks;

import java.util.UUID;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetManager;

public class RuntimeAssetManager implements AssetManager {

	@Override
	public <T extends Asset> T GetAsset(UUID id) {
		throw new UnsupportedOperationException("Unimplemented method 'GetAsset'");
	}

	@Override
	public boolean IsAssetLoaded(UUID id) {
		throw new UnsupportedOperationException("Unimplemented method 'IsAssetLoaded'");
	}

	@Override
	public AssetType GetAssetType(UUID id) {
		throw new UnsupportedOperationException("Unimplemented method 'GetAssetType'");
	}

}
