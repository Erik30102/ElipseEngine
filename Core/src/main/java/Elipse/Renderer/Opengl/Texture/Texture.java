package Elipse.Renderer.Opengl.Texture;

import org.lwjgl.opengl.GL46;

import Elipse.Core.Assets.Asset;

import java.nio.IntBuffer;

public abstract class Texture extends Asset {

	public enum TextureType {
		TEXTURE2D,
	};

	public enum TextureWrapMode {
		REPEAT,
		CLAMP_TO_EDGE,
		CLAMP_TO_BORDER
	};

	public enum TextureFiltering {
		NEAREST,
		BILINEAR,
		TRILINEAR,
	};

	public enum TextureFormat {
		RGB8,
		RGBA8,
	}

	protected int textureId;
	protected String path = "INTERNAL";

	protected int width, height;

	protected TextureFormat format;
	protected int internalFormat;
	protected int internalDataFormat;

	public String GetPath() {
		return path;
	}

	public int GetWidth() {
		return width;
	}

	public int GetHeight() {
		return height;
	}

	public int GetTextureId() {
		return textureId;
	}

	public abstract void Bind(int slot);

	public abstract void Unbind();

	public abstract TextureType GetType();

	public abstract void SetData(IntBuffer buffer);

	public abstract void Dispose();

	protected int InternalFormatToGLInternalFormat(TextureFormat format) {
		switch (format) {
			case RGB8:
				return GL46.GL_RGB8;
			case RGBA8:
				return GL46.GL_RGBA8;
			default:
				return GL46.GL_RGBA8;
		}
	}

	/**
	 * Converts the Internal Fomrat to the Opengl Data Format
	 * 
	 * @param format The Internal Format to return
	 * @return a value which can be passed to the gpu which will be interpreted as
	 *         the data format
	 */
	protected int InternalFormatToGLDataFormat(TextureFormat format) {
		switch (format) {
			case RGB8:
				return GL46.GL_RGB;
			case RGBA8:
				return GL46.GL_RGBA;
			default:
				return GL46.GL_RGBA;
		}
	}

	protected int InternalWrapModeToGLWrapMode(TextureWrapMode wrapMode) {
		switch (wrapMode) {
			case REPEAT:
				return GL46.GL_REPEAT;
			case CLAMP_TO_EDGE:
				return GL46.GL_CLAMP_TO_EDGE;
			case CLAMP_TO_BORDER:
				return GL46.GL_CLAMP_TO_BORDER;
			default:
				return GL46.GL_REPEAT;
		}
	}

	protected int InternalFilteringToGLFiltering(TextureFiltering filtering) {
		switch (filtering) {
			case NEAREST:
				return GL46.GL_NEAREST;
			case BILINEAR:
				return GL46.GL_LINEAR;
			case TRILINEAR:
				return GL46.GL_LINEAR_MIPMAP_LINEAR;
			default:
				return GL46.GL_NEAREST;
		}
	}
}
