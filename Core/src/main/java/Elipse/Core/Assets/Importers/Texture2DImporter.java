package Elipse.Core.Assets.Importers;

import java.util.UUID;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.AssetMetaData;
import Elipse.Core.Assets.IAssetImporter;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFiltering;
import Elipse.Renderer.Opengl.Texture.Texture.TextureWrapMode;

public class Texture2DImporter implements IAssetImporter {

	@Override
	public Asset GetAsset(UUID id, AssetMetaData metaData) {
		Texture2D texture = new Texture2D(metaData.getPath(), TextureFiltering.NEAREST, TextureWrapMode.CLAMP_TO_BORDER);

		return texture;
	}

}
