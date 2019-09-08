package uk.ashigaru.engine.font;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.ashigaru.engine.graphics.model.Texture;
import uk.ashigaru.engine.misc.Resource;

public class CustomFont {
	
	public CustomFont(Resource fontFile, Texture atlas) {
		readFont(fontFile.readString(), atlas);
	}
	
	public CustomFont(String name, Resource fontFile, Texture atlas) {
		readFont(fontFile.readString(), atlas);
		this.name = name;
	}
	
	//info
	private String name;
	private int size;
	private boolean bold;
	private boolean italic;
	private float stretch;
	private boolean smoothing;
	private int paddingUp, paddingRight, paddingLeft, paddingDown;
	private int spacingH, spacingV;
	
	//common
	private int lineHeight;
	private int base;
	private int scaleW, scaleH;
	
	//page
	private int pageID;
	
	private int charCount;
	private Map<Integer, Char> charMap;
	
	private Texture atlas;
	
	private void readFont(String fontSource, Texture atlas) {
		this.atlas = atlas;
		for(String str : fontSource.split("\n")) {
			Line line = new Line(str, " ");
			if(line.startsWith("info")) {
				for(String param : line) {
					if(param.startsWith("face")) {
						this.name = param.split("=")[1].replace("\"", "");
					}
					if(param.startsWith("size")) {
						this.size = Integer.parseInt(param.split("=")[1]);
					}
					if(param.startsWith("bold")) {
						this.bold = Boolean.parseBoolean(param.split("=")[1]);
					}
					if(param.startsWith("italic")) {
						this.italic = Boolean.parseBoolean(param.split("=")[1]);
					}
					if(param.startsWith("stretchH")) {
						this.stretch = Integer.parseInt(param.split("=")[1]);
					}
					if(param.startsWith("smooth")) {
						this.smoothing = Boolean.parseBoolean(param.split("=")[1]);
					}
					if(param.startsWith("padding")) {
						String[] split = param.split("=")[1].split(",");
						this.paddingUp = Integer.parseInt(split[0]);
						this.paddingRight = Integer.parseInt(split[1]);
						this.paddingLeft = Integer.parseInt(split[2]);
						this.paddingDown = Integer.parseInt(split[3]);
					}
					if(param.startsWith("spacing")) {
						String[] split = param.split("=")[1].split(",");
						this.spacingH = Integer.parseInt(split[0]);
						this.spacingV = Integer.parseInt(split[1]);
					}
				}
			} else if(line.startsWith("common")) {
				for(String param : line) {
					if(param.startsWith("lineHeight")) {
						this.lineHeight = Integer.parseInt(param.split("=")[1]);
					}
					if(param.startsWith("base")) {
						this.base = Integer.parseInt(param.split("=")[1]);
					}
					if(param.startsWith("scaleW")) {
						this.scaleW = Integer.parseInt(param.split("=")[1]);
					}
					if(param.startsWith("scaleH")) {
						this.scaleH = Integer.parseInt(param.split("=")[1]);
					}
				}
			} else if(line.startsWith("page")) {
				for(String param : line) {
					if(param.startsWith("id")) {
						this.pageID = Integer.parseInt(param.split("=")[1]);
					}
				}
			} else if(line.startsWith("chars")) {
				for(String param : line) {
					if(param.startsWith("count")) {
						this.charCount = Integer.parseInt(param.split("=")[1]);
						this.charMap = new HashMap<Integer, Char>();
					}
				}
			} else if(line.startsWith("char")) {
				Char c = Char.read(this, line);
				charMap.put(c.getId(), c);
			}
		}
	}
	
	public Texture getFontTexture() {
		return atlas;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public float getStretch() {
		return stretch;
	}

	public boolean isSmoothing() {
		return smoothing;
	}

	public int getPaddingUp() {
		return paddingUp;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public int getPaddingDown() {
		return paddingDown;
	}

	public int getSpacingH() {
		return spacingH;
	}

	public int getSpacingV() {
		return spacingV;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public int getBase() {
		return base;
	}

	public int getScaleW() {
		return scaleW;
	}

	public int getScaleH() {
		return scaleH;
	}

	public int getPageID() {
		return pageID;
	}

	public int getCharCount() {
		return charCount;
	}

	public Map<Integer, Char> getChars() {
		return Collections.unmodifiableMap(charMap);
	}
}
