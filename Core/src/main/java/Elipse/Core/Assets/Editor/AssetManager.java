package Elipse.Core.Assets.Editor;

import java.util.UUID;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;

public interface AssetManager {
	/**
	 * Loads the asset with the given UUID
	 * 
	 * @param <T> Type of the asset
	 * @param id  UUID of the object
	 * @return the asset already cast to the correct type
	 */
	public <T extends Asset> T GetAsset(UUID id);

	/**
	 * Check if asset is already loaded into memory
	 * 
	 * @param id UUID of the asset
	 * @return a boolean which tells you if it is loaded
	 */
	public boolean IsAssetLoaded(UUID id);

	/**
	 * Returns the type of the asset
	 * 
	 * @param id UUID of the asset
	 * @return the type of the asset
	 */
	public AssetType GetAssetType(UUID id);

}
