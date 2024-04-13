package Elipse.Renderer.Opengl;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import Elipse.Core.ECS.Transform;
import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;
import Elipse.Renderer.Opengl.Buffers.BufferElement;
import Elipse.Renderer.Opengl.Buffers.BufferElement.DataType;
import Elipse.Renderer.Opengl.Buffers.BufferLayout;
import Elipse.Renderer.Opengl.Buffers.IndexBuffer;
import Elipse.Renderer.Opengl.Buffers.VertexArray;
import Elipse.Renderer.Opengl.Buffers.VertexBuffer;
import Elipse.Renderer.Opengl.Texture.Texture2D;

class SceneData {
	public Matrix4f viewMatrix;
	public Matrix4f projectionMatrix;
}

public class Renderer2D {

	private static SceneData sceneData;

	private static VertexArray quad;
	private static Shader Shader_Quad;

	public static void Init() {
		RendererApi.Init();

		sceneData = new SceneData();

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

	public static void DrawQuad() {
		Shader_Quad.bind();
		RendererApi.DrawIndexed(quad);
	}

	public static void EndScene() {

	}

	public static void DrawSprite(Texture2D texture, Transform transform) {
		Shader_Quad.bind();
		Shader_Quad.loadMatrix4("viewMat", sceneData.viewMatrix);
		Shader_Quad.loadMatrix4("projectionMat", sceneData.projectionMatrix);
		Shader_Quad.loadMatrix4("transformMat",
				new Matrix4f().identity().translate(transform.position.x,
						transform.position.y, 10).scale(transform.scale.x, transform.scale.y, 1)
						.rotateZ((float) Math.toRadians(transform.rotation)));

		texture.Bind(0);
		Shader_Quad.loadInt("tex", 0);

		quad.bind();
		GL30.glDrawElements(GL30.GL_TRIANGLES, quad.getIndexBuffer().GetCount(), GL30.GL_UNSIGNED_INT, 0);
		quad.unbind();
	}

	public static void BeginScene(CameraComponent camera) {
		sceneData.projectionMatrix = camera.GetProjection();
		sceneData.viewMatrix = camera.GetView();

		RendererApi.setClearColor(0.5f, 0.5f, 1);
		RendererApi.clear();
	}
}
