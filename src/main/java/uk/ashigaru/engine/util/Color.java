package uk.ashigaru.engine.util;

import org.joml.Vector3f;

public class Color {

	public static Vector3f hex(String hex) {
		if (hex.startsWith("#"))
			hex = hex.substring(1);
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		return integer(r, g, b);
	}
	
	public static Vector3f integer(int r, int g, int b) {
		return new Vector3f((float)r / 255f, (float)g / 255f, (float)b / 255f);
	}
}
