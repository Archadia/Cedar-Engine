package uk.ashigaru.prefab.renderer;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import uk.ashigaru.engine.RenderLayer;
import uk.ashigaru.engine.graphics.Shader;
import uk.ashigaru.engine.graphics.model.Texture;
import uk.ashigaru.engine.graphics.model.VAO;
import uk.ashigaru.engine.misc.Resource;
import uk.ashigaru.engine.window.Viewport;

public class InterfaceRenderer {

	private Shader shader;
	private Shader textureShader;

	private VAO vao;
	private VAO vaoLine;

	public InterfaceRenderer(Viewport viewport) {
		this.shader = new Shader(new Resource("Interface.vert"), new Resource("Interface.frag"));
		shader.bind();
		shader.load("projection", new Matrix4f().ortho(0, viewport.w, viewport.h, 0, 0f, 10f));
		shader.unbind();
		
		this.textureShader = new Shader(new Resource("Interface.vert"), new Resource("InterfaceTextured.frag"));
		textureShader.bind();
		textureShader.load("projection", new Matrix4f().ortho(0, viewport.w, viewport.h, 0, 0, 10));
		textureShader.unbind();

		this.vao = new VAO();
		vao.bind();
		vao.loadf(0, 2, new float[] {0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1});
		vao.loadf(1, 2, new float[] {0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1});
		vao.unbind();
		
		this.vaoLine = new VAO();
		vaoLine.bind();
		vaoLine.loadf(0, 2, new float[] {0, 0, 1, 0, 1, 1, 0, 1});
		vaoLine.loadf(1, 2, new float[] {0, 0, 1, 0, 1, 1, 0, 1});
		vaoLine.unbind();
	}
	
	public void fillRect(int x, int y, int width, int height, Color color) {
		shader.bind();
		shader.load("stepSize", new Vector2f(1f/(float)width, 1f/(float)height));
		shader.load("transformation", new Matrix4f().translate(x, y, RenderLayer.GUI_FOREGROUND.z).scale(width, height, 0));
		shader.load("borderColor", color);
		shader.load("color", color);
		vao.bind();
		vao.drawArrays(GL11.GL_TRIANGLES, 2);
		vao.unbind();
		shader.unbind();
	}
	
	public void drawRect(int x, int y, int width, int height, Color color) {
		shader.bind();
		shader.load("stepSize", new Vector2f(1f/(float)width, 1f/(float)height));
		shader.load("transformation", new Matrix4f().translate(x, y, RenderLayer.GUI_FOREGROUND.z).scale(width, height, 0));
		shader.load("borderColor", color);
		shader.load("color", color);
		vaoLine.bind();
		vaoLine.drawArrays(GL11.GL_LINE_LOOP, 2);
		vaoLine.unbind();
		shader.unbind();
	}
	
	public void fillBorderedRect(int x, int y, int width, int height, Color color, float borderSize, Color borderColor) {
		shader.bind();
		shader.load("stepSize", new Vector2f(borderSize/(float)width, borderSize/(float)height));
		shader.load("transformation", new Matrix4f().translate(x, y, RenderLayer.GUI_FOREGROUND.z).scale(width, height, 0));
		shader.load("borderColor", borderColor);
		shader.load("color", color);
		vao.bind();
		vao.drawArrays(GL11.GL_TRIANGLES, 2);
		vao.unbind();
		shader.unbind();
	}
	
	public void drawTexturedRect(int x, int y, int width, int height, Texture texture) {
		textureShader.bind();
		texture.bind();
		textureShader.load("stepSize", new Vector2f(0f/(float)width, 0f/(float)height));
		textureShader.load("transformation", new Matrix4f().translate(x, y, RenderLayer.GUI_FOREGROUND.z).scale(width, height, 0));
		textureShader.load("borderColor", new Vector3f(0,0,0));
		vao.bind();
		vao.drawArrays(GL11.GL_TRIANGLES, 2);
		vao.unbind();
		GL15.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textureShader.unbind();
	}
	
	public void drawTexturedBorderedRect(int x, int y, int width, int height, Texture texture, float borderSize, Color borderColor) {
		textureShader.bind();
		texture.bind();
		textureShader.load("stepSize", new Vector2f(borderSize/(float)width, borderSize/(float)height));
		textureShader.load("transformation", new Matrix4f().translate(x, y, RenderLayer.GUI_FOREGROUND.z).scale(width, height, 0));
		textureShader.load("borderColor", borderColor);
		vao.bind();
		vao.drawArrays(GL11.GL_TRIANGLES, 2);
		vao.unbind();
		GL15.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textureShader.unbind();
	}
	
	public void exit() {
		this.vao.dispose();
		this.vaoLine.dispose();
		this.shader.dispose();
		this.textureShader.dispose();
	}
}
