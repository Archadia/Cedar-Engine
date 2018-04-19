package uk.ashigaru.engine.render.model;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class FBO {

	private int fbo;
	public Map<Integer, Integer> colorTextureMap = new HashMap<Integer, Integer>();
	public int depthBuf;
	
	public FBO(int width, int height) {
		fbo = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL20.glDrawBuffers(new int[] {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_COLOR_ATTACHMENT2});
		depthBuf =  createDepthBufferAttachment(width, height);
		unbind();
	}
	
	public void createColorTexture(int type, int width, int height) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, type, texture, 0);
        colorTextureMap.put(type, texture);
	}
	
    private int createDepthBufferAttachment(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width,
                height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                GL30.GL_RENDERBUFFER, depthBuffer);
        return depthBuffer;
    }
    
	public FBO bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		return this;
	}
	
	public FBO unbind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		return this;
	}

	public int getID() {
		return fbo;
	}
	
	public void dispose() {
		for(int key : colorTextureMap.keySet()) {
			GL11.glDeleteTextures(colorTextureMap.get(key));
		}
		GL30.glDeleteRenderbuffers(depthBuf);
		GL30.glDeleteFramebuffers(fbo);
	}
}
