package uk.ashigaru.engine.render.model.higher.mesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import uk.ashigaru.engine.render.model.higher.Material;

public class MeshIndexed {

	private Map<String, List<Vector3f>> map = new HashMap<String, List<Vector3f>>();
	private Map<String, List<Vector3i>> triangleMap = new HashMap<String, List<Vector3i>>();
 	
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
	
	public MeshIndexed pushTriangle(String name, Vector3i... array) {
		if (!triangleMap.containsKey(name)) {
			List<Vector3i> list = new ArrayList<Vector3i>();
			for(Vector3i i : array) list.add(i);
			triangleMap.put(name, list);
		} else {
			for(Vector3i i : array) triangleMap.get(name).add(i);
		}
		return this;
	}
	
	public MeshIndexed push(String name, Vector3f... array) {
		if (!map.containsKey(name)) {
			List<Vector3f> list = new ArrayList<Vector3f>();
			for(Vector3f v : array) list.add(v);
			map.put(name, list);
		} else {
			for(Vector3f v : array) map.get(name).add(v);
		}
		return this;
	}
	
	public List<Vector3f> get(String name) {
		return map.get(name);
	}
	
	public List<Vector3i> getTriangles(String name) {
		return triangleMap.get(name);
	}
}
