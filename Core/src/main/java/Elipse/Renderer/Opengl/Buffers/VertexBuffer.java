package Elipse.Renderer.Opengl.Buffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class VertexBuffer {

	private int bufferId;
	private BufferLayout Layout;

	/**
	 * internal code used for sending the data to the gpu
	 * 
	 * @param data the data to be sent
	 */
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * @param vertecies all the vertecies that should be stored on the gpu
	 */
	public VertexBuffer(float[] vertecies) {
		bufferId = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, bufferId);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, createFloatBuffer(vertecies), GL30.GL_STATIC_DRAW);
	}

	/**
	 * set the layout of the vertex array for later use by the shader the data
	 * assosicated with the buffer layout is later specified when adding the
	 * vertexbuffer to the vertex array
	 * 
	 * @param BufferLayout the layout of memory of the vertex buffer
	 */
	public void SetLayout(BufferLayout layout) {
		this.Layout = layout;
	}

	/**
	 * @return layout of the extra info
	 */
	public BufferLayout GetLayout() {
		return this.Layout;
	}

	/**
	 * internal code for binding the vertex buffer
	 */
	public void bind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, bufferId);
	}

	/**
	 * internal code for unbinding any vertex buffer
	 */
	public void unbind() {
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * to clean up all data form the gpu of the vertex buffer
	 */
	public void dispose() {
		GL30.glDeleteBuffers(bufferId);
	}
}
