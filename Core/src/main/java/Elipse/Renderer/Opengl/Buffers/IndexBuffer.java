package Elipse.Renderer.Opengl.Buffers;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class IndexBuffer {
	private int bufferId;
	private int size;

	private static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public IndexBuffer(int[] indicies) {
		size = indicies.length;

		bufferId = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, bufferId);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indicies), GL30.GL_STATIC_DRAW);
	}

	/**
	 * internal code for binding the indexbuffer
	 */
	public void bind() {
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, bufferId);
	}

	/**
	 * internal code for retriving the indecies count for sending the cout of
	 * vertecies to render
	 */
	public int GetCount() {
		return size;
	}

	/**
	 * internal code for unbinding any index buffer
	 */
	public void unbind() {
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	/**
	 * delete all the data from the gpu assosicated with the index buffer
	 */
	public void dispose() {
		GL30.glDeleteBuffers(bufferId);
	}

}
