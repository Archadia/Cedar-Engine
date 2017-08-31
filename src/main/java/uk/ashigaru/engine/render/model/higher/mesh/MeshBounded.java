package uk.ashigaru.engine.render.model.higher.mesh;

import java.util.HashMap;
import java.util.Map;

import uk.ashigaru.engine.render.model.higher.Material;

public class MeshBounded {

	private Map<String, float[]> map = new HashMap<String, float[]>();
	
	private Material material;
	private int meshWidth = 2;
	private int meshHeight = 2;
	
	public MeshBounded(int meshWidth, int meshHeight) {
		this.meshWidth = Math.max(meshWidth, 2);
		this.meshHeight = Math.max(meshHeight, 2);
	}

	public void clear() {
		map.clear();
		material = null;
	}
	
	public MeshBounded setMaterial(Material material) {
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
		
	public void set(String name, int index, float value) {
		if(!map.containsKey(name)) {
			float[] array = new float[meshWidth * meshHeight];
			array[index] = value;
			map.put(name, array);
		} else {
			map.get(name)[index] = value;
		}
	}

	public float[] get(String name) {
		return map.get(name);
	}
}
