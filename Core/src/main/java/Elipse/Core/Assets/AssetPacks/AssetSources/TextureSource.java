package Elipse.Core.Assets.AssetPacks.AssetSources;

import java.io.Serializable;

import Elipse.Renderer.Opengl.Texture.Texture.TextureFiltering;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFormat;
import Elipse.Renderer.Opengl.Texture.Texture.TextureWrapMode;

public class TextureSource implements AssetSource, Serializable {
	public byte[] texture;
	public TextureFormat format;
	public int width;
	public int height;
	public TextureFiltering filtering;
	public TextureWrapMode wrapMode;
}
