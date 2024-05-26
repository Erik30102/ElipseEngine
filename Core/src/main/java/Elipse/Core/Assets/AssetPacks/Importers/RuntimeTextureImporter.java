package Elipse.Core.Assets.AssetPacks.Importers;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.AssetPacks.IRuntimeAssetImporter;
import Elipse.Core.Assets.AssetPacks.AssetSources.AssetSource;
import Elipse.Core.Assets.AssetPacks.AssetSources.TextureSource;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class RuntimeTextureImporter implements IRuntimeAssetImporter {

	@Override
	public AssetSource Serialize(Asset asset) {
		if (asset.GetAssetType() != AssetType.TEXTURE2D) {
			Logger.c_error("Tryed serialzing a non texture asset with the texture serializer");
			return null;
		}
		TextureSource textureSource = new TextureSource();

		Texture2D texture = (Texture2D) asset;
		textureSource.height = texture.GetHeight();
		textureSource.width = texture.GetWidth();
		textureSource.format = texture.GetTextureFormat();
		textureSource.filtering = texture.GetTextureFiltering();
		textureSource.wrapMode = texture.GetTextureWrapMode();
		textureSource.texture = texture.GetRawData();

		return textureSource;
	}

	@Override
	public Asset Deserialize(AssetSource source) {
		if (!(source instanceof TextureSource)) {
			Logger.c_error("The source file doesn't contain a texture");
			return null;
		}

		TextureSource textureSource = (TextureSource) source;
		return new Texture2D(
				textureSource.texture, textureSource.width, textureSource.height, textureSource.format,
				textureSource.filtering, textureSource.wrapMode);
	}

}
