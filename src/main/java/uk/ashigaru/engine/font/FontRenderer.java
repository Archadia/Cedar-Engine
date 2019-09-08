package uk.ashigaru.engine.font;

import java.awt.Color;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import uk.ashigaru.engine.graphics.Shader;
import uk.ashigaru.engine.graphics.model.VAO;
import uk.ashigaru.engine.misc.Resource;
import uk.ashigaru.engine.window.Viewport;

public class FontRenderer {

	private Shader shader;
	
	public FontRenderer(Viewport viewport) {
		shader = new Shader(new Resource("Font.vert"), new Resource("Font.frag"));
		updateProjection(viewport);
	}	
	
	public void updateProjection(Viewport viewport) {
		shader.bind();
		shader.load("projection", new Matrix4f().ortho(0, viewport.w, viewport.h, 0, 0f, 10f));
		shader.unbind();
	}
	
	public void drawFontMesh(FontModel model, Vector2f translation, Vector2f scale, float width, float edge, Color color) {
		shader.bind();
		VAO vao = model.getVAO();
		vao.bind();
		model.getTexture().bind();
		shader.load("color", color);
		shader.load("width", width);
		shader.load("edge", edge);
		if(model.isCentered()) {
			shader.load("transformation", new Matrix4f().translate(translation.x - ((model.getStringWidth() * scale.x)/2f), translation.y, 0).scale(scale.x, scale.y, 1));
		} else {
			shader.load("transformation", new Matrix4f().translate(translation.x, translation.y, 0f).scale(scale.x, scale.y, 1));
		}
		vao.drawArrays(GL11.GL_TRIANGLES, 2);
		GL15.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		vao.unbind();
		shader.unbind();
	}
	
	public void drawString(String text, CustomFont font, float size, float width, float edge, boolean centered, Vector2f translation, Color color) {
		FontModel model = new FontModel(font, text, size, centered);
		drawFontMesh(model, translation, new Vector2f(0.2f, 0.2f), width, edge, color);
	}
	
	public static float stringWidth(String tex, CustomFont font, float size) {
		FontModel model = new FontModel(font, tex, size, false);
		return model.getStringWidth() * 0.2f;
	}
}
