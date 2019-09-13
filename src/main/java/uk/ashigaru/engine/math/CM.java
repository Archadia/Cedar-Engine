package uk.ashigaru.engine.math;

import java.util.Random;

public class CM {

	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	public static int randomRange(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
	
	public static float lerp(float v0, float v1, float t) {
		return (1 - t) * v0 + t * v1;
	}
	
	public static float d2r(float d) {
		return (float) Math.toRadians(d);
	}
	
	public static float r2d(float r) {
		return (float) Math.toDegrees(r);
	}

	public static float inv(float f) {
		return 1f/f;
	}
	
	public static float sind(float d) {
		return (float) Math.sin(d2r(d));
	}
	
	public static float cosd(float d) {
		return (float) Math.cos(d2r(d));
	}
}
