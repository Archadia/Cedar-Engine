package uk.ashigaru.engine.gfx.model.obj.mesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import uk.ashigaru.engine.gfx.model.obj.Material;

public class Mesh {

	private Map<Integer, List<Float>> map = new HashMap<Integer, List<Float>>();
	
	private Material material;
	
	public void clear() {
		map.clear();
		material = null;
	}
	
	public void reverse() {
		for(int key : map.keySet()) {
			List<Float> list = map.get(key);
			List<Float> reversed = Lists.reverse(list);
			map.put(key, reversed);
		}
	}
	
	public Mesh setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public Mesh push(int index, float... array) {
		if (!map.containsKey(index)) {
			List<Float> list = new ArrayList<Float>();
			for(float f : array) list.add(f);
			map.put(index, list);
		} else {
			for(float f : array) map.get(index).add(f);
		}
		return this;
	}
	
	public float[] getArray(int index) {
		if(!map.containsKey(index)) return new float[0];
		List<Float> list = map.get(index);
		float[] array = new float[list.size()];
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public List<Float> get(int index) {
		return map.get(index);
	}
}
