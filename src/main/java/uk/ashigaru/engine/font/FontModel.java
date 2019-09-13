package uk.ashigaru.engine.font;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryUtil;

import uk.ashigaru.engine.graphics.model.Texture;
import uk.ashigaru.engine.graphics.model.VAO;

public class FontModel {

	private static final float SPACE_WIDTH = 30f;

	private CustomFont font;
	private String string;
	private float fontSize;
	private boolean centered;
	
	private List<Float> vertices;
	private List<Float> texes;

	private VAO vao;
	
	public FontModel(CustomFont font, String string, float fontSize, boolean centered) {
		this.font = font;
		this.string = string;
		this.fontSize = fontSize;
		this.centered = centered;
		
		this.vertices = new ArrayList<Float>();
		this.texes = new ArrayList<Float>();
		
		mesh();
	}
	
	public float getFontSize() {
		return fontSize;
	}
	
	private float stringWidth;
	
	private void mesh() {
		float cursorX = 0;
		for(int i = 0; i < string.length(); i++) {
			char c = string.toCharArray()[i];
			int unicode = c;
			if(c == ' ') {
				cursorX += SPACE_WIDTH * fontSize;
			} else {
				Char character = font.getChars().get(unicode);
				if(character != null) {
					addCharacter(cursorX, 0, character);
					cursorX += character.getAdvanceX() * fontSize;
				}
			}
		}
		stringWidth = cursorX;
		FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.size());
		FloatBuffer texBuffer = MemoryUtil.memAllocFloat(texes.size());
		
		vertices.forEach(f -> {
			vertexBuffer.put(f);
		});
		vertexBuffer.flip();
		texes.forEach(f -> {
			texBuffer.put(f);
		});
		texBuffer.flip();
	
		model(vertexBuffer, texBuffer);
		MemoryUtil.memFree(vertexBuffer);
		MemoryUtil.memFree(texBuffer);
	}
	
	public float getStringWidth() {
		return stringWidth;
	}
	
	public boolean isCentered() {
		return centered;
	}
	
	public void model(FloatBuffer vertexBuffer, FloatBuffer texBuffer) {
		if(vao != null) vao.dispose();
		vao = new VAO();
		vao.bind();
		vao.bufferf(0, 2, vertexBuffer);
		vao.bufferf(1, 2, texBuffer);
		vao.unbind();
	}
	
	public VAO getVAO() {
		return vao;
	}
	
	public Texture getTexture() {
		return font.getFontTexture();
	}
	
	private void addCharacter(float cursorX, float cursorY, Char character) {
		float x = cursorX + (character.getOffsetX() * fontSize);
		float y = cursorY + (character.getOffsetY() * fontSize);
		float maxX = x + (character.getWidth() * fontSize);
		float maxY = y + (character.getHeight() * fontSize);
		addQuad(x, y, maxX, maxY);
		addTextureCoords(character);
	}
	
	private void addQuad(float x, float y, float maxX, float maxY) {
		vertices.add(x);
		vertices.add(y);
		vertices.add(maxX);
		vertices.add(y);
		vertices.add(maxX);
		vertices.add(maxY);
		vertices.add(x);
		vertices.add(y);
		vertices.add(maxX);
		vertices.add(maxY);
		vertices.add(x);
		vertices.add(maxY);
	}
	
	public void addTextureCoords(Char character) {
		texes.add(character.getCorrectedX());
		texes.add(character.getCorrectedY());
		texes.add(character.getCorrectedX() + character.getCorrectedWidth());
		texes.add(character.getCorrectedY());
		texes.add(character.getCorrectedX() + character.getCorrectedWidth());
		texes.add(character.getCorrectedY() + character.getCorrectedHeight());
		texes.add(character.getCorrectedX());
		texes.add(character.getCorrectedY());
		texes.add(character.getCorrectedX() + character.getCorrectedWidth());
		texes.add(character.getCorrectedY() + character.getCorrectedHeight());
		texes.add(character.getCorrectedX());
		texes.add(character.getCorrectedY() + character.getCorrectedHeight());
	}
	
	public void dispose() {
		vao.dispose();
		vertices.clear();
		texes.clear();
	}
}
