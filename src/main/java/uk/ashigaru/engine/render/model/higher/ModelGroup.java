package uk.ashigaru.engine.render.model.higher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelGroup {

	private Map<String, List<Float>> map = new HashMap<String, List<Float>>();
	
	private Material material;
	
	public ModelGroup(float[] vertices, float[] normals, float[] textureCoords) {
		this.push("vertices", vertices);
		this.push("normals", normals);
		this.push("textureCoords", textureCoords);
	}

	public void clear() {
		map.clear();
		material = null;
	}
	
	public ModelGroup setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public void push(String name, float... array) {
		if (!map.containsKey(name)) {
			List<Float> list = new ArrayList<Float>();
			for(float f : array) list.add(f);
			map.put(name, list);
		} else {
			for(float f : array) map.get(name).add(f);
		}
	}
	
	public float[] getArray(String name) {
		List<Float> list = map.get(name);
		float[] array = new float[list.size()];
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public List<Float> get(String name) {
		return map.get(name);
	}
}
