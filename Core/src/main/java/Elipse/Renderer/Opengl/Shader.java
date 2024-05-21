package Elipse.Renderer.Opengl;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryStack;

import Elipse.Core.Logger;
import Elipse.Core.Maths.Vector;
import Elipse.Core.Maths.Vector3;
import Elipse.Utils.Pair;

public class Shader {

	public enum ShaderType {
		VERTEX,
		FRAGMENT
	}

	private int InternalShaderTypeToOpenglType(ShaderType type) {
		switch (type) {
			case VERTEX:
				return GL46.GL_VERTEX_SHADER;
			case FRAGMENT:
				return GL46.GL_FRAGMENT_SHADER;
		}
		Logger.c_error("unknown ShaderType: " + type.toString());

		return 0;
	}

	private ShaderType StringToInternalShaderType(String type) {
		switch (type) {
			case "vertex":
				return ShaderType.VERTEX;
			case "fragment":
				return ShaderType.FRAGMENT;
		}
		Logger.c_error("unknown String ShaderType: " + type);
		return null;
	}

	private int ShaderId;
	private HashMap<String, Integer> uniforms = new HashMap<String, Integer>();

	/**
	 * Create a shader from vertex and fragment shader source
	 * 
	 * @param vertexShader
	 * @param fragmentShader
	 */
	public Shader(String vertexShader, String fragmentShader) {
		this.LoadShader(vertexShader, fragmentShader);
	}

	public Shader(String filepath) {
		try {
			String contents = Files.readString(Path.of(filepath));
			Pair<String, String> src = PreProcessShaderSrc(contents);

			this.LoadShader(src.getKey(), src.getValue());
		} catch (IOException e) {
			Logger.c_error("Coulnd not load shader: " + filepath);
		}
	}

	// TODO: find a better way of doing this type of shit
	private Pair<String, String> PreProcessShaderSrc(String src) {

		String vertexSrc = "";
		String fragmentSrc = "";

		int index = src.indexOf("#type ", 0);
		while (index >= 0) {
			int eol = src.indexOf("\n", index);
			String type = src.substring(index + 6, eol).trim();

			ShaderType shaderType = StringToInternalShaderType(type);
			index = src.indexOf("#type ", eol);

			if (shaderType == ShaderType.FRAGMENT) {
				fragmentSrc = src.substring(eol, index == -1 ? src.length() : index);
			} else {
				vertexSrc = src.substring(eol, index == -1 ? src.length() : index);
			}
		}

		return new Pair<String, String>(vertexSrc, fragmentSrc);
	}

	private void LoadShader(String vertexShader, String fragmentShader) {
		this.ShaderId = GL46.glCreateProgram();

		int vertexShaderId = this.CompileShader(ShaderType.VERTEX, vertexShader);
		int fragmentShaderId = this.CompileShader(ShaderType.FRAGMENT, fragmentShader);

		GL46.glAttachShader(this.ShaderId, fragmentShaderId);
		GL46.glAttachShader(this.ShaderId, vertexShaderId);
		GL46.glLinkProgram(this.ShaderId);

		int status = GL46.glGetProgrami(this.ShaderId, GL46.GL_LINK_STATUS);
		if (status != GL46.GL_TRUE) {
			Logger.c_warn(GL46.glGetProgramInfoLog(this.ShaderId));
			return;
		}

		GL46.glValidateProgram(this.ShaderId);
	}

	private int CompileShader(ShaderType type, String source) {
		int shaderId = GL46.glCreateShader(InternalShaderTypeToOpenglType(type));

		GL46.glShaderSource(shaderId, source);
		GL46.glCompileShader(shaderId);

		return shaderId;
	}

	private void initUniform(String uniformName) {
		uniforms.put(uniformName, GL46.glGetUniformLocation(this.ShaderId, uniformName));
	}

	private void chckUniform(String uniformName) {
		if (!uniforms.containsKey(uniformName)) {
			initUniform(uniformName);
		}
	}

	/**
	 * A method to load a float value to a specific location.
	 *
	 * @param location the location to load the float value
	 * @param val      the float value to be loaded
	 */
	public void loadFloat(String location, float val) {
		chckUniform(location);
		GL46.glUniform1f(uniforms.get(location), val);
	}

	/**
	 * Method to load a Vector2f value to a specified location.
	 *
	 * @param location the location to load the value to
	 * @param val      the Vector2f value to load
	 */
	public void loadVector2(String location, Vector val) {
		chckUniform(location);
		GL46.glUniform2f(uniforms.get(location), val.getX(), val.getY());
	}

	/**
	 * Method to load a Vector3f value to a specified location.
	 *
	 * @param location the location to load the value to
	 * @param val      the Vector3f value to load
	 */
	public void loadVector3(String location, Vector3 val) {
		chckUniform(location);
		GL46.glUniform3f(uniforms.get(location), val.getX(), val.getY(), val.getZ());
	}

	/**
	 * Loads an integer value into the specified location in the shader program.
	 *
	 * @param location the name of the uniform variable in the shader program
	 * @param val      the integer value to be loaded
	 */
	public void loadInt(String location, int val) {
		chckUniform(location);
		GL46.glUniform1i(uniforms.get(location), val);
	}

	/**
	 * Loads a boolean value into the specified uniform location represented with a
	 * 1 for true and a 0 for false.
	 *
	 * @param location the name of the uniform location
	 * @param v        the boolean value to be loaded
	 */
	public void loadBool(String location, boolean v) {
		chckUniform(location);
		GL46.glUniform1i(uniforms.get(location), v ? 1 : 0);
	}

	/**
	 * Loads a 4x4 matrix into a uniform variable in the shader program.
	 *
	 * @param location the name of the uniform variable
	 * @param val      the matrix to load
	 */
	public void loadMatrix4(String location, Matrix4f val) {
		chckUniform(location);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = val.get(stack.mallocFloat(16));
			GL46.glUniformMatrix4fv(uniforms.get(location), false, fb);
		}
	}

	/**
	 * Used to activate the shader for the next draw calls till the shader is
	 * unbinded.
	 */
	public void bind() {
		GL46.glUseProgram(this.ShaderId);
	}

	/**
	 * disables the Shader
	 */
	public void unbind() {
		GL46.glUseProgram(0);
	}
}
