package uk.ashigaru.engine.render.model.lower;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import uk.ashigaru.engine.util.Resource;

public class RawTexture {

	private int width, height;
	private int[] data;
			
	public RawTexture() {}
	
	public RawTexture(Resource texture) {
		this.load(texture);
	}
	private void load(Resource texture) {
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
		
		data = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
	}
		
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int[] getPixels() { return data; }
}