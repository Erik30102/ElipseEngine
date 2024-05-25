package Elipse.Renderer.Batching;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import Elipse.Core.ECS.Transform;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Renderer.Opengl.Shader;
import Elipse.Renderer.Opengl.Buffers.BufferElement;
import Elipse.Renderer.Opengl.Buffers.BufferElement.DataType;
import Elipse.Renderer.Opengl.Buffers.BufferLayout;
import Elipse.Renderer.Opengl.Buffers.IndexBuffer;
import Elipse.Renderer.Opengl.Buffers.VertexArray;
import Elipse.Renderer.Opengl.Buffers.VertexBuffer;
import Elipse.Renderer.Opengl.Buffers.VertexBuffer.BUFFER_USAGE;
import Elipse.Renderer.Opengl.Texture.Texture2D;

public class RenderBatch {
	private Shader shader;

	private int maxBatchSize;

	private VertexArray vao;
	private VertexBuffer vbo;

	private boolean hasRoom = true;
	private boolean hasRoomTextures = true;

	private int textureCount = 0;

	private float[] vertecies;
	private List<Texture2D> textures = new ArrayList<Texture2D>();

	private int numOfSprites;
	private final int OFFSET_VERTECIE = 6;

	private int[] texSlots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };

	public RenderBatch(int Size) {
		this.maxBatchSize = Size;

		shader = new Shader("Shaders/Batch_sprite.glsl");

		Start();
	}

	public void Start() {
		BufferLayout bufferLayout = new BufferLayout(new BufferElement[] {
				new BufferElement("position", DataType.VEC3),
				new BufferElement("texCoords", DataType.VEC2),
				new BufferElement("textureIndex", DataType.FLOAT),
		});

		this.vertecies = new float[this.maxBatchSize * OFFSET_VERTECIE];

		vao = new VertexArray();
		vao.bind();
		vbo = new VertexBuffer(this.vertecies, BUFFER_USAGE.DYNAMIC_DRAW);
		vbo.SetLayout(bufferLayout);

		IndexBuffer ibo = new IndexBuffer(generateIndecies());

		vao.addIndexBuffer(ibo);
		vao.addVertexBuffer(vbo);
	}

	public void Begin() {
		textureCount = 0;
		numOfSprites = 0;
		hasRoom = true;
		hasRoomTextures = true;

		textures.clear();
	}

	public void AddSprite(Texture2D texture, Transform transform) {
		int texIndex = 0;

		if (!textures.contains(texture)) {
			textures.add(texture);
			textureCount++;
		}

		Matrix4f transformMatrix = new Matrix4f().identity().translate(transform.position.getX(),
				transform.position.getY(), 10).scale(transform.scale.getX(), transform.scale.getY(), 1)
				.rotateZ((float) Math.toRadians(transform.rotation));

		texIndex = textures.indexOf(texture);

		Vector4f[] vec = new Vector4f[] {
				new Vector4f(0.5f, -0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(-0.5f, 0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(0.5f, 0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(-0.5f, -0.5f, 0f, 1f).mul(transformMatrix),
		};

		float[] ux = new float[] {
				1,
				0,
				1,
				0
		};

		float[] uy = new float[] {
				1,
				0,
				0,
				1
		};

		for (int i = 0; i < 4; i++) {
			int start = numOfSprites * OFFSET_VERTECIE * 4;

			vertecies[start + i * 6 + 0] = vec[i].x;
			vertecies[start + i * 6 + 1] = vec[i].y;
			vertecies[start + i * 6 + 2] = vec[i].z;
			vertecies[start + i * 6 + 3] = ux[i];
			vertecies[start + i * 6 + 4] = uy[i];
			vertecies[start + i * 6 + 5] = texIndex;
		}

		this.numOfSprites++;

		if (numOfSprites >= maxBatchSize) {
			hasRoom = false;
		}
	}

	public void reloadData() {
		this.vbo.bind();
		this.vbo.SetData(this.vertecies);
		this.vbo.unbind();
	}

	public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
		shader.bind();
		shader.loadMatrix4("viewMat", viewMatrix);
		shader.loadMatrix4("projectionMat", projectionMatrix);

		for (int i = 0; i < textureCount; i++) {
			textures.get(i).Bind(i);
		}
		shader.loadIntArray("textures", texSlots);

		vao.bind();
		RendererApi.DrawIndexed(vao, numOfSprites * 6);

		textures.get(0).Unbind();
	}

	public boolean hasRoom() {
		return hasRoom;
	}

	public boolean hasRoomTextures() {
		return hasRoomTextures;
	}

	private int[] generateIndecies() {
		int[] indecies = new int[maxBatchSize * 6];

		for (int i = 0; i < maxBatchSize; i++) {
			indecies[i * 6] = i * 4 + 2;
			indecies[i * 6 + 1] = i * 4 + 1;
			indecies[i * 6 + 2] = i * 4 + 0;
			indecies[i * 6 + 3] = i * 4 + 0;
			indecies[i * 6 + 4] = i * 4 + 1;
			indecies[i * 6 + 5] = i * 4 + 3;
		}
		return indecies;
	}
}
