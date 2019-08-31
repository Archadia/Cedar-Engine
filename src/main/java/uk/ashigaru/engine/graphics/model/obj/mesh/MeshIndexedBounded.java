package uk.ashigaru.engine.graphics.model.obj.mesh;

import java.util.HashMap;
import java.util.Map;

import uk.ashigaru.engine.graphics.model.obj.Material;

public class MeshIndexedBounded {

	private Map<String, float[]> map = new HashMap<String, float[]>();
	private Map<String, int[]> triangleMap = new HashMap<String, int[]>();
	
	private Material material;
	private int meshWidth = 2;
	private int meshHeight = 2;
	
	public MeshIndexedBounded(int meshWidth, int meshHeight) {
		this.meshWidth = Math.max(meshWidth, 2);
		this.meshHeight = Math.max(meshHeight, 2);
	}

	public void clear() {
		map.clear();
		triangleMap.clear();
		material = null;
	}
	
	public MeshIndexedBounded setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public Material getMaterial() {
		return material;
	}
	
	public void setAll(String name, float[] array) {
		if(array.length == meshWidth * meshHeight) {
			map.put(name, array);
		}
	}
	
	public void setAllTriangles(String name, int[] array) {
		if(array.length == (meshWidth-1)*(meshHeight-1)*6) {
			triangleMap.put(name, array);
		}
	}
		
	public void set(String name, int index, float value) {
		if(!map.containsKey(name)) {
			float[] array = new float[meshWidth * meshHeight];
			array[index] = value;
			map.put(name, array);
		} else {
			map.get(name)[index] = value;
		}
	}
	
	public void setTriangle(String name, int index, int value) {
		if(!triangleMap.containsKey(name)) {
			int[] array = new int[(meshWidth-1)*(meshHeight-1)*6];
			array[index] = value;
			triangleMap.put(name, array);
		} else {
			triangleMap.get(name)[index] = value;
		}
	}

	public float[] get(String name) {
		return map.get(name);
	}
	
	public int[] getTriangles(String name) {
		return triangleMap.get(name);
	}
}
