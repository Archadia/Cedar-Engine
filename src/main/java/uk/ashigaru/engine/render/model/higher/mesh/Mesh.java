package uk.ashigaru.engine.render.model.higher.mesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ashigaru.engine.render.model.higher.Material;

public class Mesh {

	private Map<String, List<Float>> map = new HashMap<String, List<Float>>();
	
	private Material material;
	
	public void clear() {
		map.clear();
		material = null;
	}
	
	public Mesh setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public Mesh push(String name, float... array) {
		if (!map.containsKey(name)) {
			List<Float> list = new ArrayList<Float>();
			for(float f : array) list.add(f);
			map.put(name, list);
		} else {
			for(float f : array) map.get(name).add(f);
		}
		return this;
	}
	
	public float[] getArray(String name) {
		if(!map.containsKey(name)) return new float[0];
		List<Float> list = map.get(name);
		float[] array = new float[list.size()];
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public List<Float> get(String name) {
		return map.get(name);
	}
}
