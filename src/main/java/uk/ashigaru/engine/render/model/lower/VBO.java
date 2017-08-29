package uk.ashigaru.engine.render.model.lower;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class VBO {

	public final int id;
	public final int type;
	
	public VBO(int size, int location, float[] array) {		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array).flip();
		
		this.type = GL11.GL_FLOAT;
		this.id = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, array, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(location, size, GL11.GL_FLOAT, false, 0, 0);	}
	
	public VBO(int[] array) {		
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array).flip();
		
		this.type = GL11.GL_INT;
		this.id = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.id);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, array, GL15.GL_STATIC_DRAW);
	}
	
	public void destroy() {
		GL15.glBindBuffer(type == GL11.GL_FLOAT ? GL15.GL_ARRAY_BUFFER : (type == GL11.GL_INT ? GL15.GL_ELEMENT_ARRAY_BUFFER : GL15.GL_ARRAY_BUFFER), 0);
		GL15.glDeleteBuffers(this.id);
	}
}
