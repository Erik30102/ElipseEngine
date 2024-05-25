package Elipse.Renderer.Opengl;

import org.lwjgl.opengl.GL46;
import org.lwjgl.opengl.GLDebugMessageCallback;

import Elipse.Core.Logger;
import Elipse.Renderer.Opengl.Buffers.VertexArray;

public class RendererApi {

	public static void Init() {
		GL46.glEnable(GL46.GL_DEBUG_OUTPUT);
		GL46.glEnable(GL46.GL_DEBUG_OUTPUT_SYNCHRONOUS);
		GL46.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
			String msg = GLDebugMessageCallback.getMessage(length, message);

			switch (type) {
				case GL46.GL_DEBUG_TYPE_ERROR:
					msg = "[ERROR] " + msg;
					break;
				case GL46.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
					msg = "[DEPRECATED BEHAVIOR] " + msg;
					break;
				case GL46.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
					msg = "[UNDEFINED BEHAVIOR] " + msg;
					break;
				case GL46.GL_DEBUG_TYPE_PORTABILITY:
					msg = "[PORTABILITY] " + msg;
					break;
				case GL46.GL_DEBUG_TYPE_PERFORMANCE:
					msg = "[PERFORMANCE] " + msg;
					break;
				case GL46.GL_DEBUG_TYPE_OTHER:
					msg = "[OTHER] " + msg;
					break;
			}

			switch (severity) {
				case GL46.GL_DEBUG_SEVERITY_HIGH:
					Logger.c_error(msg);
					break;
				case GL46.GL_DEBUG_SEVERITY_MEDIUM:
					Logger.c_warn(msg);
					break;
				case GL46.GL_DEBUG_SEVERITY_LOW:
					Logger.c_warn(msg);
					break;
				case GL46.GL_DEBUG_SEVERITY_NOTIFICATION:
					// Logger.c_info(msg);
					break;
				default:
					break;
			}
		}, 0);

		GL46.glEnable(GL46.GL_DEPTH_TEST);
		GL46.glEnable(GL46.GL_LINE_SMOOTH);
		GL46.glEnable(GL46.GL_BLEND);
		GL46.glBlendFunc(GL46.GL_SRC_ALPHA, GL46.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void setClearColor(float r, float g, float b) {
		GL46.glClearColor(r, g, b, 1);
	}

	public static void clear() {
		GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
	}

	public static void DrawIndexed(VertexArray vertexArray) {
		DrawIndexed(vertexArray, vertexArray.getIndexBuffer().GetCount());
	}

	public static void DrawIndexed(VertexArray vertexArray, int count) {
		GL46.glDrawElements(GL46.GL_TRIANGLES, count, GL46.GL_UNSIGNED_INT, 0);
	}

	public static void SetViewport(int getWidth, int getHeight) {
		GL46.glViewport(0, 0, getWidth, getHeight);
	}
}
