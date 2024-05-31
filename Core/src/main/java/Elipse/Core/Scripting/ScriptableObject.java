package Elipse.Core.Scripting;

import Elipse.Core.Assets.Asset;

public abstract class ScriptableObject extends Asset {

	@Override
	public AssetType GetAssetType() {
		return AssetType.SCRIPTABLEOBJ;
	}

}
