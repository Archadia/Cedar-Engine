package uk.ashigaru.engine.graphics.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;

import uk.ashigaru.engine.misc.Resource;

public class Texture {
	
	private int textureID;
	private int width, height;

	public Texture(int[] data, int width, int height, int min, int mag) {
		textureID = createTexture(data, width, height, min, mag);
		this.width = width;
		this.height = height;
	}
	
	public Texture(Resource resource, int width, int height, int min, int mag) {
		textureID = createTexture(read(resource), width, height, min, mag);
		this.width = width;
		this.height = height;
	}

	public Texture(Resource resource, int min, int mag) {
		textureID = createTexture(read(resource), width, height, min, mag);
	}
	
	private int[] read(Resource texture) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(texture.getPath().openStream());
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					pixels[x + width * y] = image.getRGB(x, y);
				}
			}
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

	private int createTexture(int[] data, int width, int height, int min, int mag) {
		int result = GL11.glGenTextures(); 
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, result);	
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, min);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mag);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return result;
	}
	
	public void delete() {
		GL11.glDeleteTextures(textureID);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTextureID() {
		return textureID;
	}

	public void bind() {
		GL15.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
}
