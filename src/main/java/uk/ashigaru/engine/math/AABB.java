package uk.ashigaru.engine.math;

import org.joml.Vector2f;

/**
 * AABB represented through a center point and a half dimension.
 */
public class AABB {
	
	public float x, y;
	public float halfD;
	
	public AABB(float x, float y, float halfD) {
		this.x = x;
		this.y = y;
		this.halfD = halfD;
	}
	
	public boolean contains(float x, float y) {
		return (x >= this.x - halfD && x <= this.x + halfD) && (y >= this.y - halfD && y <= this.y + halfD);
	}
	
	public boolean intersects(AABB aabb) {
		if(this.x + halfD >= aabb.x - aabb.halfD && this.x - halfD <= aabb.x + aabb.halfD) {
			if(this.y + halfD >= aabb.y - aabb.halfD && this.y - halfD <= aabb.y + aabb.halfD) {
				return true;
			}
		}
		return false;
	}
	
	public Vector2f topLeft() {
		return new Vector2f(x - halfD, y - halfD);
	}
	
	public Vector2f topRight() {
		return new Vector2f(x + halfD, y - halfD);
	}
	
	public Vector2f bottomLeft() {
		return new Vector2f(x - halfD, y + halfD);
	}
	
	public Vector2f bottomRight() {
		return new Vector2f(x + halfD, y + halfD);
	}
	
	public AABB clone() {
		return new AABB(x, y, halfD);
	}
}
