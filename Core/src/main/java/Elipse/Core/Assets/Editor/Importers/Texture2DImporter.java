package Elipse.Core.Assets.Editor.Importers;

import java.util.UUID;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Editor.AssetMetaData;
import Elipse.Core.Assets.Editor.IAssetImporter;
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
