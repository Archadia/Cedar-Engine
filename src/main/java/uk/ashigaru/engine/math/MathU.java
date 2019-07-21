package uk.ashigaru.engine.math;

public class MathU {
	public static int fl(double f) {
		return (int) Math.floor(f);
	}
	
	public static int ce(double f) {
		return (int) Math.ceil(f);
	}
	
	public static float lerp(float origin, float destination, float t) {
		return (1f - t) * destination + t * origin;
	}
}
