package Elipse.Renderer.Opengl.Texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL46;
import org.lwjgl.stb.STBImage;

import Elipse.Core.Logger;

public class Texture2D extends Texture {

	public Texture2D(String path, TextureFiltering filtering,
			TextureWrapMode wrapMode) {
		this.path = path;

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = STBImage.stbi_load(
				path, width, height,
				channels, 0);

		this.width = width.get(0);
		this.height = height.get(0);

		if (image != null) {
			if (channels.get(0) == 4) {
				this.format = TextureFormat.RGBA8;
			} else if (channels.get(0) == 3) {
				this.format = TextureFormat.RGB8;
			} else {
				Logger.c_error("Image: " + path + " could not load");
				return;
			}

			this.internalDataFormat = this.InternalFormatTOGLDataFormat(format);
			this.internalFormat = this.InternalFormatToGLInternalFormat(format);

			GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_MIN_FILTER,
					this.InternalFilteringToGLFiltering(filtering));
			GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_MAG_FILTER,
					this.InternalFilteringToGLFiltering(filtering));

			GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_WRAP_S, this.InternalWrapModeToGLWrapMode(wrapMode));
			GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_WRAP_T, this.InternalWrapModeToGLWrapMode(wrapMode));

			GL46.glTextureSubImage2D(textureId, 0, 0, 0, this.width, this.height, this.internalDataFormat,
					GL46.GL_UNSIGNED_BYTE, image);

		} else {
			Logger.c_error("Texture: Could not load image '" + path + "'");
			return;
		}
	}

	/**
	 * Generate a empty 2d Texture on the gpu
	 * 
	 * @param width     the width of the texture
	 * @param height    the height of the texture
	 * @param format    the format of how to pixels should be stored
	 * @param filtering the filtering mode for upscaling
	 * @param wrapMode  the wrapping mode of the texture
	 */
	public Texture2D(
			int width,
			int height,
			TextureFormat format,
			TextureFiltering filtering,
			TextureWrapMode wrapMode) {

		this.internalDataFormat = this.InternalFormatTOGLDataFormat(format);
		this.internalFormat = this.InternalFormatToGLInternalFormat(format);
		this.width = width;
		this.height = height;
		this.format = format;

		// this.textureId = GL46.glGenTextures();

		this.textureId = GL46.glCreateTextures(GL46.GL_TEXTURE_2D);
		GL46.glTextureStorage2D(this.textureId, 1, this.internalFormat, this.width, this.height);

		GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_MIN_FILTER,
				this.InternalFilteringToGLFiltering(filtering));
		GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_MAG_FILTER,
				this.InternalFilteringToGLFiltering(filtering));

		GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_WRAP_S, this.InternalWrapModeToGLWrapMode(wrapMode));
		GL46.glTextureParameteri(this.textureId, GL46.GL_TEXTURE_WRAP_T, this.InternalWrapModeToGLWrapMode(wrapMode));

		GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, internalDataFormat,
				GL30.GL_UNSIGNED_BYTE, 0);
	}

	@Override
	public void Bind(int slot) {
		GL46.glBindTextureUnit(slot, this.textureId);
	}

	@Override
	public TextureType GetType() {
		return TextureType.TEXTURE2D;
	}

	@Override
	public void SetData(IntBuffer data) {
		GL46.glTextureSubImage2D(textureId, 0, 0, 0, width, height, this.internalDataFormat, GL46.GL_UNSIGNED_BYTE,
				data);
	}

	@Override
	public void Dispose() {
		GL46.glDeleteTextures(textureId);
	}

	@Override
	public AssetType GetAssetType() {
		return AssetType.TEXTURE2D;
	}

}
