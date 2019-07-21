package uk.ashigaru.engine.misc;

import java.awt.Color;

import org.joml.Vector3f;

public class Col {

	public static Color or(int r, int g, int b, int a) {
		return new Color(r, g, b, a);
	}
	
	public static Color or(float r, float g, float b, float a) {
		return new Color(r, g, b, a);
	}
	
	public static Color or(int hex) {
		return new Color((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, (hex & 0xFF), hex >> 24);
	}
	
	public static Vector3f asVec(Color c) {
		return new Vector3f(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f); 
	}
}
