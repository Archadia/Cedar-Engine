package uk.ashigaru.engine.window;

import org.lwjgl.opengl.GL11;

public class Viewport {

	public int x, y, w, h;
	
	public Viewport(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void bind() {
		GL11.glViewport(x, y, w, h);
	}
	
	public float getAR() {
		return (float) w / (float) h;
	}
}
