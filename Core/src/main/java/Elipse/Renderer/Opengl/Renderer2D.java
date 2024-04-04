package Elipse.Renderer.Opengl;

import Elipse.Core.ECS.Transform;
import Elipse.Renderer.Opengl.Buffers.BufferElement;
import Elipse.Renderer.Opengl.Buffers.BufferElement.DataType;
import Elipse.Renderer.Opengl.Buffers.BufferLayout;
import Elipse.Renderer.Opengl.Buffers.IndexBuffer;
import Elipse.Renderer.Opengl.Buffers.VertexArray;
import Elipse.Renderer.Opengl.Buffers.VertexBuffer;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class Renderer2D {

	private static VertexArray quad;
	private static Shader Shader_Quad;

	public static void Init() {
		RendererApi.Init();

		quad = new VertexArray();
		quad.bind();

		BufferLayout bufferLayout = new BufferLayout(new BufferElement[] {
				new BufferElement("texCoords", DataType.VEC2),
		});

		VertexBuffer vbo = new VertexBuffer(new float[] {
				0.5f, -0.5f, 0.0f,
				-0.5f, 0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f });

		float[][] vertexInfo = new float[][] {
				{ 1, 1,
						0, 0,
						1, 0,
						0, 1 }
		};

		vbo.SetLayout(bufferLayout);
		quad.addVertexBuffer(vbo, vertexInfo);

		IndexBuffer idx = new IndexBuffer(new int[] { 2, 1, 0,
				0, 1, 3 });

		quad.addIndexBuffer(idx);

		quad.unbind();

		Shader_Quad = new Shader("Shaders/Renderer_sprite.glsl");
	}

	public static void BeginScene() {
		RendererApi.setClearColor(0.5f, 0.5f, 1);
		RendererApi.clear();
	}

	public static void DrawQuad() {
		Shader_Quad.bind();
		RendererApi.DrawIndexed(quad);
	}

	public static void EndScene() {

	}

	public static void DrawSprite(Texture2D texture, Transform transform) {

	}
}
