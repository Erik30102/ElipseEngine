package Elipse.Core.Assets.AssetPacks.Importers;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.IRuntimeAssetImporter;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.TilemapSource;
import Elipse.Core.Project.Project;
import Elipse.Core.Tilemap.Tilemap;

public class RuntimeTilemapImporter implements IRuntimeAssetImporter {

	@Override
	public AssetSource Serialize(Asset asset) {
		if (asset.GetAssetType() != AssetType.TILEMAP) {
			Logger.c_error("Tryed serialzing a non Tilemap asset with the Tilemap serializer");
			return null;
		}

		TilemapSource tilemapSource = new TilemapSource();

		Tilemap tilemap = (Tilemap) asset;

		tilemapSource.width = tilemap.GetWidth();
		tilemapSource.height = tilemap.GetHeight();
		tilemapSource.texturID = tilemap.GetSpritesheet().GetAssetHandel();
		tilemapSource.tiles = tilemap.GetTiles();
		return tilemapSource;
	}

	@Override
	public Asset Deserialize(AssetSource source) {
		if (!(source instanceof TilemapSource)) {
			Logger.c_error("The source file doesn't contain a Tilemap");
			return null;
		}

		TilemapSource tilemapSource = (TilemapSource) source;

		return new Tilemap(Project.GetActive().GetAssetManager().GetAsset(tilemapSource.texturID),
				tilemapSource.width, tilemapSource.height, tilemapSource.tiles);
	}

}
