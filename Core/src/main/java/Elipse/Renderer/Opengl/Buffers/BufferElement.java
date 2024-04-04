package Elipse.Renderer.Opengl.Buffers;

import org.lwjgl.opengl.GL30;

import Elipse.Core.Logger;

public class BufferElement {
	public enum DataType {
		FLOAT, VEC2, VEC3, VEC4, MAT3, MAT4, INT, INT2, INT3, INT4, BOOL
	};

	public String Name;
	public DataType DataType;

	public BufferElement(String Name, DataType DataType) {
		this.Name = Name;
		this.DataType = DataType;
	}

	/**
	 * @return GL30 type of the given Buffer Data type for use with the gpu
	 */
	public int GetOpenGLBaseType() {
		switch (this.DataType) {
			case FLOAT:
				return GL30.GL_FLOAT;
			case VEC2:
				return GL30.GL_FLOAT;
			case VEC3:
				return GL30.GL_FLOAT;
			case VEC4:
				return GL30.GL_FLOAT;
			case MAT3:
				return GL30.GL_FLOAT;
			case MAT4:
				return GL30.GL_FLOAT;
			case INT:
				return GL30.GL_INT;
			case INT2:
				return GL30.GL_INT;
			case INT3:
				return GL30.GL_INT;
			case INT4:
				return GL30.GL_INT;
			case BOOL:
				return GL30.GL_BOOL;
		}
		Logger.c_error("Unsupported DataType for BufferElement");
		return 0;
	}

	/**
	 * @return The count of the elements used by the Buffertype
	 */
	public int GetComponentCount() {
		switch (this.DataType) {
			case FLOAT:
				return 1;
			case VEC2:
				return 2;
			case VEC3:
				return 3;
			case VEC4:
				return 4;
			case MAT3:
				return 9;
			case MAT4:
				return 16;
			case INT:
				return 1;
			case INT2:
				return 2;
			case INT3:
				return 3;
			case INT4:
				return 4;
			case BOOL:
				return 1;
		}
		Logger.c_error("Unsupported DataType for BufferElement");
		return 0;
	}
}