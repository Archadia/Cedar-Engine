package uk.ashigaru.engine.graphics;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import uk.ashigaru.engine.misc.Resource;

public class Shader {

	public final int programID;
	private Map<Integer, Integer> shaders = new HashMap<Integer, Integer>();
	public Map<String, Integer> locations = new HashMap<String, Integer>();

	public Shader(Resource vertex, Resource fragment) {
		programID = GL20.glCreateProgram();

		attachShader(GL20.GL_VERTEX_SHADER, vertex.readString());
		attachShader(GL20.GL_FRAGMENT_SHADER, fragment.readString());
		
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(this.programID);

		if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
			throw new RuntimeException("Unable to link shader program:");
		

	}

	public void attachShader(int type, String source) {
		int shader = GL20.glCreateShader(type);
		shaders.put(type, shader);

		GL20.glShaderSource(shader, source);
		GL20.glCompileShader(shader);

		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
			throw new RuntimeException("Error creating " + (type == GL20.GL_VERTEX_SHADER ? "vertex" : "fragment") + " shader\n" + GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH)));

		GL20.glAttachShader(programID, shader);
	}

	public void dispose() {
		unbind();

		for (int type : shaders.keySet()) {
			GL20.glDetachShader(programID, type);
			GL20.glDeleteShader(shaders.get(type));
		}

		GL20.glDeleteProgram(programID);
	}

	public void bind() {
		GL20.glUseProgram(programID);
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public Shader load(String key, Object object) {
		int location = -1;
		if(locations.containsKey(key)) {
			location = locations.get(key);
		} else {
			location = GL20.glGetUniformLocation(this.programID, key);
		}
		if (object instanceof Integer) {
			GL20.glUniform1i(location, (int) object);
		} else if (object instanceof Float) {
			GL20.glUniform1f(location, (float) object);
		} else if (object instanceof Vector4f) {
			Vector4f vec = (Vector4f) object;
			GL20.glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
		} else if (object instanceof Vector3f) {
			Vector3f vec = (Vector3f) object;
			GL20.glUniform3f(location, vec.x, vec.y, vec.z);
		} else if (object instanceof Vector2f) {
			Vector2f vec = (Vector2f) object;
			GL20.glUniform2f(location, vec.x, vec.y);
		} else if (object instanceof Boolean) {
			GL20.glUniform1f(location, (boolean) object ? 1 : 0);
		} else if (object instanceof Matrix4f) {
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			Matrix4f mat = (Matrix4f) object;
			mat.get(buffer);
			GL20.glUniformMatrix4fv(location, false, buffer);
		} else if (object instanceof Color) {
			Color vec = (Color) object;
			GL20.glUniform3f(location, vec.getRed() / 255f, vec.getGreen() / 255f, vec.getBlue() / 255f);
		}
		return this;
	}

//	public Shader createLocation(String string) {
//		this.locations.put(string, GL20.glGetUniformLocation(this.programID, string));
//		return this;
//	}
}
