package uk.ashigaru.engine.util;

import java.awt.Color;

public class Col {

	public static Color or(int r, int g, int b) {
		return new Color(r, g, b);
	}
	
	public static Color or(int hex) {
		return new Color((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, (hex & 0xFF));
	}
}
