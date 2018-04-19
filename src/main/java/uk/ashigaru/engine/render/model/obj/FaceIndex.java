package uk.ashigaru.engine.render.model.obj;

import org.joml.Vector3f;

public class FaceIndex {

	public Vector3f vertexIndex;
	public Vector3f normalIndex;
	public Vector3f texCoordIndex;
	
	public FaceIndex(Vector3f vertexIndex, Vector3f normalIndex, Vector3f texCoordIndex) {
		this.vertexIndex = vertexIndex;
		this.normalIndex = normalIndex;
		this.texCoordIndex = texCoordIndex;
	}
}
