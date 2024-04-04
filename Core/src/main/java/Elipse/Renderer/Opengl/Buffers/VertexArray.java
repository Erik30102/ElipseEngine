package Elipse.Renderer.Opengl.Buffers;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

public class VertexArray {

	private List<VertexBuffer> vertexBuffers = new ArrayList<VertexBuffer>();
	private IndexBuffer indexBuffer;

	private int vertexId;

	public VertexArray() {
		vertexId = GL30.glGenVertexArrays();
	}

	/**
	 * sets the vertex buffer and adds the extra info associated with the layout of
	 * the vertex buffer
	 * 
	 * @param vertexBuffer the vertex buffer to be added
	 * 
	 * @param extraInfo    the extra info associated with the vertex buffer layout
	 */
	public void addVertexBuffer(VertexBuffer vertexBuffer, float[][] extraInfo) {
		this.bind();
		vertexBuffer.bind();
		GL30.glEnableVertexAttribArray(0);
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

		int index = 0;
		if (vertexBuffer.GetLayout() != null) {
			for (BufferElement element : vertexBuffer.GetLayout().getElements()) {
				int vbo = GL30.glGenBuffers();
				FloatBuffer buffer = VertexBuffer.createFloatBuffer(extraInfo[index]);

				GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
				GL30.glBufferData(GL30.GL_ARRAY_BUFFER, buffer, GL30.GL_STATIC_DRAW);

				GL30.glEnableVertexAttribArray(index + 1);
				GL30.glVertexAttribPointer(index + 1, element.GetComponentCount(), element.GetOpenGLBaseType(), false, 0, 0);
				index++;
			}
		}

		vertexBuffers.add(vertexBuffer);
	}

	/**
	 * binds the Vertex buffer for rendering
	 */
	public void bind() {
		GL30.glBindVertexArray(vertexId);
	}

	/**
	 * unbindes the vertex buffer
	 */
	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Retrieves the list of vertex buffers.
	 *
	 * @return the list of vertex buffers
	 */
	public List<VertexBuffer> getVertexBuffers() {
		return this.vertexBuffers;
	}

	/**
	 * Retrieves the index buffer associated with this object.
	 *
	 * @return the index buffer
	 */
	public IndexBuffer getIndexBuffer() {
		return indexBuffer;
	}

	/**
	 * @param indexBuffer set the index buffer
	 */
	public void addIndexBuffer(IndexBuffer indexBuffer) {
		this.bind();
		indexBuffer.bind();

		this.indexBuffer = indexBuffer;
	}
}
