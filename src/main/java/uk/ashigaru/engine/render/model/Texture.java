package uk.ashigaru.engine.render.model;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL42;

import uk.ashigaru.engine.util.Resource;

public class Texture {

	private int width, height;
	private int textureID;
			
	public Texture() {}
	
	public Texture(Resource texture) {
		this.textureID = this.createTexture(this.scanResource(texture), this.width, this.height);
	}
	
	public Texture(RawTexture texture) {
		this.textureID = this.createTexture(texture.getPixels(), texture.getWidth(), texture.getHeight());
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public static void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	private int[] scanResource(Resource texture) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(texture.getPath()));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}		
		return data;
	}
	
	private int createTexture(int[] data, int width, int height) {
		int result = GL11.glGenTextures(); 
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);	
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, 1, GL11.GL_RGBA8, width, height);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);	
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return result;
	}
	
	public void delete() {
		GL11.glDeleteTextures(textureID);
	}
		
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getTextureID() { return textureID; }
}