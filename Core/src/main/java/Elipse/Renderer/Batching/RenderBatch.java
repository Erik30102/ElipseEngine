package Elipse.Renderer.Batching;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import Elipse.Core.ECS.Transform;
import Elipse.Core.Maths.Vector;
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

	/**
	 * Creates new Render batch with support of x many sprites
	 * 
	 * @param Size the max number of sprites in the batch
	 */
	public RenderBatch(int Size) {
		this.maxBatchSize = Size;

		shader = new Shader("Shaders/Batch_sprite.glsl");

		Start();
	}

	private void Start() {
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

	/**
	 * clear the batch data from the last frame
	 */
	public void Begin() {
		textureCount = 0;
		numOfSprites = 0;
		hasRoom = true;
		hasRoomTextures = true;

		textures.clear();
	}

	/**
	 * Adds sprite to the batch
	 */
	public void AddSprite(Texture2D texture, Vector position) {
		AddSprite(texture, position, new Vector(1, 1), 0);
	}

	/**
	 * Adds sprite to the batch
	 */
	public void AddSprite(Texture2D texture, Transform transform) {
		AddSprite(texture, transform.position, transform.scale, transform.rotation);
	}

	/**
	 * Adds sprite to the batch
	 */
	public void AddSprite(Texture2D texture, Vector position, Vector scale, float rotation) {
		int texIndex = 0;

		if (!textures.contains(texture)) {
			textures.add(texture);
			textureCount++;
		}

		Matrix4f transformMatrix = new Matrix4f().identity().translate(position.getX(),
				position.getY(), 10).scale(scale.getX(), scale.getY(), 1)
				.rotateZ((float) Math.toRadians(rotation));

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

	public void AddSprite(Sprite sprite, Vector position) {
		AddSprite(sprite, position, new Vector(1, 1), 0);
	}

	public void AddSprite(Sprite sprite, Vector position, Vector scale, float rotation) {
		int texIndex = 0;

		if (!textures.contains(sprite.getTexture())) {
			textures.add(sprite.getTexture());
			textureCount++;
		}

		Matrix4f transformMatrix = new Matrix4f().identity().translate(position.getX(),
				position.getY(), 10).scale(scale.getX(), scale.getY(), 1)
				.rotateZ((float) Math.toRadians(rotation));

		texIndex = textures.indexOf(sprite.getTexture());

		Vector4f[] vec = new Vector4f[] {
				new Vector4f(0.5f, -0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(-0.5f, 0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(0.5f, 0.5f, 0f, 1f).mul(transformMatrix),
				new Vector4f(-0.5f, -0.5f, 0f, 1f).mul(transformMatrix),
		};

		float[] ux = new float[] {
				sprite.getUv()[0].getX(),
				sprite.getUv()[1].getX(),
				sprite.getUv()[2].getX(),
				sprite.getUv()[3].getX()
		};

		float[] uy = new float[] {
				sprite.getUv()[0].getY(),
				sprite.getUv()[1].getY(),
				sprite.getUv()[2].getY(),
				sprite.getUv()[3].getY()
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

	/**
	 * reloades the changes of the vertecies in the vbo and uploades the changes to
	 * the gpu
	 */
	public void reloadData() {
		this.vbo.bind();
		this.vbo.SetData(this.vertecies);
		this.vbo.unbind();
	}

	// TODO: have singular scene data and use that

	/**
	 * Draws the batch in a single draw call
	 * 
	 * @param viewMatrix       the view matrix of the camera
	 * @param projectionMatrix the projection matrix of the camera
	 */
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

	/**
	 * Checks if the batch has room for more draw calls
	 * 
	 * @return true or false depending if the batch has room
	 */
	public boolean hasRoom() {
		return hasRoom;
	}

	/**
	 * Checks if the batch has room for more textures
	 * 
	 * @return true or false depending if the batch has room for textures
	 */
	public boolean hasRoomTextures() {
		return hasRoomTextures;
	}

	/**
	 * Checks if a texture is already apart of the batch
	 * 
	 * @param texture the texture to check
	 * @return true or false depending if the texture is already in the batch
	 */
	public boolean isTextureLoaded(Texture2D texture) {
		return textures.contains(texture);
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
