package Elipse.Renderer.Opengl;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import Elipse.Core.ECS.Transform;
import Elipse.Core.Maths.Vector;
import Elipse.Renderer.OrthograhicCamera;
import Elipse.Renderer.Batching.RenderBatch;
import Elipse.Renderer.Batching.Sprite;
import Elipse.Renderer.Lighting.LightingManager;
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

	private static int currentBatchIndex = 0;

	private static List<RenderBatch> renderStack = new ArrayList<>();
	private static final Vector[] standardUVS = new Vector[] {
			new Vector(1, 1),
			new Vector(0, 0),
			new Vector(1, 0),
			new Vector(0, 1),
	};

	public static void Init() {
		RendererApi.Init();

		LightingManager.Init();

		sceneData = new SceneData();
		renderStack.add(new RenderBatch(20000));
	}

	public static void BeginScene(OrthograhicCamera camera) {
		sceneData.projectionMatrix = camera.GetProjection();
		sceneData.viewMatrix = camera.GetView();

		RendererApi.setClearColor(0.2f, 0.2f, 0.2f);
		RendererApi.clear();

		currentBatchIndex = 0;

		renderStack.forEach(RenderBatch::Begin);
	}

	public static void EndScene() {
		renderStack.forEach(RenderBatch::reloadData);
		renderStack.forEach(r -> r.render(sceneData.viewMatrix, sceneData.projectionMatrix));
	}

	private static boolean CheckCurrentBatch(Texture2D tex) {
		return (!renderStack.get(currentBatchIndex).hasRoom() || (!renderStack.get(currentBatchIndex).hasRoomTextures()
				&& !renderStack.get(currentBatchIndex).IsTextureAttached(tex)));
	}

	private static void PrepearBatch(Texture2D tex) {
		if (CheckCurrentBatch(tex)) {
			currentBatchIndex++;
			if (renderStack.get(currentBatchIndex) == null) {
				renderStack.add(new RenderBatch(20000));
				renderStack.get(currentBatchIndex).Begin();
			}
		}
	}

	public static void DrawSprite(Texture2D texture, Transform transform) {
		PrepearBatch(texture);

		renderStack.get(currentBatchIndex).AddSprite(texture, standardUVS, transform.position, transform.scale,
				transform.rotation);
	}

	public static void AddSprite(Sprite sprite, Vector vector) {
		PrepearBatch(sprite.getTexture());

		renderStack.get(currentBatchIndex).AddSprite(sprite.getTexture(), sprite.getUv(), vector, new Vector(1, 1), 0);
	}
}