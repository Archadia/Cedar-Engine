package uk.ashigaru.engine.render.model.higher;

import java.util.Arrays;

public class ModelGroupIndex {

	public float[] vertices;
	public float[] normals;
	public float[] textureCoords;
	public int[] indices;
	
	private Material material;
	
	public ModelGroupIndex(float[] vertices, float[] normals, float[] textureCoords, int[] indices) {
		this.vertices = vertices;
		this.normals = normals;
		this.textureCoords = textureCoords;
		this.indices = indices;
	}
	
	public ModelGroupIndex setMaterial(Material material) {
		this.material = material;
		return this;
	}
		
	public Material getMaterial() {
		return material;
	}
	
	public void pushVertices(float... verts) {
		float[] newVerts = Arrays.copyOf(vertices, vertices.length + verts.length);
		for(int i = vertices.length; i < newVerts.length; i++) {
			newVerts[i] = verts[i - vertices.length];
		}
		this.vertices = newVerts;
	}
	
	public void pushNormals(float... norms) {
		float[] newNorms = Arrays.copyOf(normals, normals.length + norms.length);
		for(int i = normals.length; i < newNorms.length; i++) {
			newNorms[i] = norms[i - normals.length];
		}
		this.normals = newNorms;
	}

	public void pushTextureCoords(float... txc) {
		float[] newTexCoords = Arrays.copyOf(textureCoords, textureCoords.length + txc.length);
		for(int i = textureCoords.length; i < newTexCoords.length; i++) {
			newTexCoords[i] = txc[i - textureCoords.length];
		}
		this.textureCoords = newTexCoords;
	}
	
	public void pushIndices(int... indc) {
		int[] newIndices = Arrays.copyOf(indices, indices.length + indc.length);
		for(int i = indices.length; i < newIndices.length; i++) {
			newIndices[i] = indc[i - normals.length];
		}
		this.indices = newIndices;
	}
}
