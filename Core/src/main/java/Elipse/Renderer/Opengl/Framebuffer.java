package Elipse.Renderer.Opengl;

import org.lwjgl.opengl.GL30;

import Elipse.Core.Logger;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFiltering;
import Elipse.Renderer.Opengl.Texture.Texture.TextureFormat;
import Elipse.Renderer.Opengl.Texture.Texture.TextureWrapMode;

public class Framebuffer {
	public enum FramebufferTextureFormat {
		None,
		RGBA8,
		DEPTH24STENCIL8
	}

	private int FramebufferID = -1;
	private int width, height;

	private Texture2D FboTexture;

	public Framebuffer(int width, int height) {
		Resize(width, height);
	}

	public void Invalidate() {
		if (FramebufferID != -1) {
			GL30.glDeleteFramebuffers(FramebufferID);
			FboTexture.Dispose();
		}

		FramebufferID = GL30.glGenFramebuffers();
		int rboId = GL30.glGenRenderbuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, FramebufferID);

		FboTexture = new Texture2D(width, height, TextureFormat.RGBA8, TextureFiltering.NEAREST,
				TextureWrapMode.CLAMP_TO_BORDER);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D,
				FboTexture.GetTextureId(), 0);

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, rboId);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH_COMPONENT32, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
				GL30.GL_RENDERBUFFER, rboId);

		if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
			Logger.c_error("Framebuffer not completed");
		}
		Unbind();
	}

	public void Resize(int width, int height) {
		this.width = width;
		this.height = height;

		Invalidate();
	}

	public Texture2D GetTexture() {
		return FboTexture;
	}

	public void Bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, FramebufferID);
	}

	public void Unbind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public int GetWidth() {
		return this.width;
	}

	public int GetHeight() {
		return this.height;
	}
}
