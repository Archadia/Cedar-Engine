package uk.ashigaru.engine.font;

public class Char {

	private CustomFont font;
	
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private int xAdvance;
	private int page;
	private int channel;
	
	private Char() {}
	
	public static Char read(CustomFont font, Line line) {
		Char character = new Char();
		character.font = font;
		for(String part : line) {
			if(part.contains("=")) {
				String type = part.split("=")[0];
				int value = Integer.parseInt(part.split("=")[1]);
				if(type.equalsIgnoreCase("id")) {
					character.id = value;
				} else if(type.equalsIgnoreCase("x")) {
					character.x = value;
				} else if(type.equalsIgnoreCase("y")) {
					character.y = value;
				} else if(type.equalsIgnoreCase("width")) {
					character.width = value;
				} else if(type.equalsIgnoreCase("height")) {
					character.height = value;
				} else if(type.equalsIgnoreCase("xoffset")) {
					character.xOffset = value;
				} else if(type.equalsIgnoreCase("yoffset")) {
					character.yOffset = value;
				} else if(type.equalsIgnoreCase("xadvance")) {
					character.xAdvance = value;
				} else if(type.equalsIgnoreCase("page")) {
					character.page = value;
				} else if(type.equalsIgnoreCase("chnl")) {
					character.channel = value;
				}
			}
		}
		return character;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public float getCorrectedX() {
		return (float) x / (float) font.getScaleW();
	}
	
	public float getCorrectedY() {
		return (float) y / (float) font.getScaleH();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public float getCorrectedWidth() {
		return (float) width / (float) font.getScaleW();
	}
	
	public float getCorrectedHeight() {
		return (float) height / (float) font.getScaleH();
	}
	
	public int getOffsetX() {
		return xOffset;
	}

	public int getOffsetY() {
		return yOffset;
	}

	public int getAdvanceX() {
		return xAdvance;
	}

	public int getPage() {
		return page;
	}

	public int getChannel() {
		return channel;
	}
}
