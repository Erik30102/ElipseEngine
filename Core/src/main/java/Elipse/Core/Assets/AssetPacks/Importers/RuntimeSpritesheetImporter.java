package Elipse.Core.Assets.AssetPacks.Importers;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.IRuntimeAssetImporter;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.SpritesheetSource;
import Elipse.Core.Project.Project;
import Elipse.Renderer.Batching.Spritesheet;

public class RuntimeSpritesheetImporter implements IRuntimeAssetImporter {

	@Override
	public AssetSource Serialize(Asset asset) {
		if (asset.GetAssetType() != AssetType.SPRITESHEET) {
			Logger.c_error("Tryed serialzing a non spritesheet asset with the spritesheet serializer");
			return null;
		}

		SpritesheetSource spritesheetSource = new SpritesheetSource();

		Spritesheet spritesheet = (Spritesheet) asset;

		spritesheetSource.textureID = spritesheet.getTexture().GetAssetHandel();

		spritesheetSource.width = spritesheet.getSpriteWidth();
		spritesheetSource.height = spritesheet.getSpriteHeight();

		return spritesheetSource;
	}

	@Override
	public Asset Deserialize(AssetSource source) {
		if (!(source instanceof SpritesheetSource)) {
			Logger.c_error("The source file doesn't contain a spritesheet");
			return null;
		}

		SpritesheetSource spritesheetSource = (SpritesheetSource) source;

		return new Spritesheet(Project.GetActive().GetAssetManager().GetAsset(spritesheetSource.textureID),
				spritesheetSource.width, spritesheetSource.height);
	}

}
