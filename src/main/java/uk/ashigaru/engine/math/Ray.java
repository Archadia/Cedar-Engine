package uk.ashigaru.engine.math;

import org.joml.Vector3f;

public class Ray {

	public Vector3f start;
	public Vector3f direction;
	
	public Ray(Vector3f start, Vector3f direction) {
		this.start = start;
		this.direction = direction;
	}
	
	public Ray(Ray ray) {
		this.start = new Vector3f(ray.start);
		this.direction = new Vector3f(ray.direction);
	}
	
	public Vector3f pointAt(float t) {
		return new Vector3f(start).add(new Vector3f(direction).mul(t));
	}
	
	public float magnitudeAt(float t) {
		return pointAt(t).length();
	}
}
