package uk.ashigaru.engine.render.model.higher.mesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ashigaru.engine.render.model.higher.Material;

public class MeshIndexed {

	private Map<String, List<Float>> map = new HashMap<String, List<Float>>();
	private Map<String, List<Integer>> triangleMap = new HashMap<String, List<Integer>>();
 	
	private Material material;
	
	public MeshIndexed clear() {
		map.clear();
		triangleMap.clear();
		material = null;
		return this;
	}
	
	public MeshIndexed setMaterial(Material material) {
		this.material = material;
		return this;
	}
		
	public Material getMaterial() {
		return material;
	}
	
	public MeshIndexed pushTriangle(String name, int... array) {
		if (!triangleMap.containsKey(name)) {
			List<Integer> list = new ArrayList<Integer>();
			for(int i : array) list.add(i);
			triangleMap.put(name, list);
		} else {
			for(int i : array) triangleMap.get(name).add(i);
		}
		return this;
	}
	
	public MeshIndexed push(String name, float... array) {
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
		List<Float> list = map.get(name);
		float[] array = new float[list.size()];
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public int[] getTriangleArray(String name) {
		List<Integer> list = triangleMap.get(name);
		int[] array = new int[list.size()];
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public List<Float> get(String name) {
		return map.get(name);
	}
	
	public List<Integer> getTriangle(String name) {
		return triangleMap.get(name);
	}
}
