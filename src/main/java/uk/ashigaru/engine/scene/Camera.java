package uk.ashigaru.engine.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	public Vector3f position = new Vector3f(0,0,0);
	public Vector3f rotation = new Vector3f();
	
	public Camera(Vector3f position, Vector3f orientation) {
		this.position = position;
		this.rotation = orientation;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f in = new Matrix4f().identity();
		in.rotate((float) Math.toRadians(this.rotation.x), 1, 0, 0);
		in.rotate((float) Math.toRadians(this.rotation.y), 0, 1, 0);
		in.rotate((float) Math.toRadians(this.rotation.z), 0, 0, 1);
		in.translate(new Vector3f(position).negate());
		return in;
	}
}
