package uk.ashigaru.engine.render.model.lower;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import uk.ashigaru.engine.util.Logger;

public class ArtificialTexture {

	public final int width, height;
	private int textureID;
	
	public ArtificialTexture(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void createBlankTexture() {
		int[] data = new int[width * height];
		int result = GL11.glGenTextures();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);	
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		this.textureID = result;
	}
	
	public void stitchTexture(int x, int y, RawTexture texture) {
		if(texture.getWidth() > this.width || texture.getHeight() > this.height) {
			Logger.err("Cannot stitch texture. Exceeding bounds!");
			return;
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);	
		IntBuffer buffer = BufferUtils.createIntBuffer(texture.getPixels().length);
		buffer.put(texture.getPixels()).flip();
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, texture.getWidth(), texture.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
