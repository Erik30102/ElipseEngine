package Elipse.Renderer.Opengl.Buffers;

/**
 * the layout of the vertex array for later use with shaders
 */
public class BufferLayout {
	private BufferElement[] elements;

	public BufferLayout(BufferElement[] elements) {
		this.elements = elements;
	}

	public BufferElement[] getElements() {
		return this.elements;
	}
}
