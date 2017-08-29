package uk.ashigaru.engine.render.model.lower;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

public class VAO {

	private int vao;
	private Map<String, Integer> count = new HashMap<String, Integer>();
	private int[] vbos = new int[16];
	
	public VAO() {
		this.vao = GL30.glGenVertexArrays();
	}
		
	public VAO bind() {
		GL30.glBindVertexArray(this.vao);
		return this;
	}
	
	public VAO unbind() {
		GL30.glBindVertexArray(0);
		return this;
	}
	
	public int getVBOSize(String tag) {
		return this.count.get(tag) == null ? 0 : this.count.get(tag);
	}
	
	public int getID() {
		return this.vao;
	}
	
	public int getVBO(int i) {
		if(i < vbos.length && i > -1) {
			return this.vbos[i];
		} else {
			return -1;
		}
	}
	
	public void registerVBOSize(String key, int amount) {
		this.count.put(key, amount);
	}
	
	public VAO drawArrays(int vertexAmount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexAmount);
		return this;
	}
	
	public VAO drawArraysInstanced(int vertexAmount, int instanceAmount) {
		this.bind();
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, vertexAmount, instanceAmount);
		return this;
	}
	
	public VAO drawElements(int vertexAmount) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexAmount, GL11.GL_UNSIGNED_BYTE, 0);
		return this;
	}
	
	public VAO buffer(int index, int size, float[] data) {		
		this.vbos[index] = GL15.glGenBuffers();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[index]);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		return this;
	}
	
	public void rebuffer(int index, int size, float[] data) {
		if(vbos[index] == 0) {
			buffer(index, size, data);
			return;
		}
				
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[index]);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		//GL20.glEnableVertexAttribArray(index);
	}
	
	public void dispose() {
		for(int id : vbos) {
			GL15.glDeleteBuffers(id);
		}
		GL30.glDeleteVertexArrays(vao);
	}
}
