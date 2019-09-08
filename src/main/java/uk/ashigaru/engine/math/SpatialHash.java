package uk.ashigaru.engine.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;

public class SpatialHash<T extends SpatialElement> {

	private Map<String, List<T>> hashm;
	private int cellDimensions;
	
	public SpatialHash(int cellDimensions) {
		this.cellDimensions = cellDimensions;
		
		this.hashm = new HashMap<String, List<T>>();
	}
	
	public void rem(T element) {
		
	}
	
	public void add(T element) {
		List<String> keys = hash(element);
		for(String key : keys) {
			if(!hashm.containsKey(key)) {
				List<T> l = new ArrayList<T>();
				l.add(element);
				hashm.put(key, l);
			} else {
				hashm.get(key).add(element);
			}
		}
	}
	
	private List<String> hash(T element) {
		List<String> keys = new ArrayList<String>();
		
		AABB elementBounds = new AABB(element.getX(), element.getY(), element.getRadius());
		
		addHash(elementBounds.topLeft(), keys);
		addHash(elementBounds.topRight(), keys);
		addHash(elementBounds.bottomLeft(), keys);
		addHash(elementBounds.bottomRight(), keys);
		return keys;
	}
	
	private List<String> hash(AABB boundary) {
		List<String> keys = new ArrayList<String>();

		AABB elementBounds = new AABB(boundary.x, boundary.y, boundary.halfD);
		
		addHash(elementBounds.topLeft(), keys);
		addHash(elementBounds.topRight(), keys);
		addHash(elementBounds.bottomLeft(), keys);
		addHash(elementBounds.bottomRight(), keys);
		return keys;
	}
	
	public List<T> get(T element) {
		List<String> keys = hash(element);
		List<T> elements = new ArrayList<T>();
		for(String key : keys) {
			List<T> e = hashm.get(key);
			if(e != null) {
				for(T elementInMap : e) {
					if(!elements.contains(elementInMap)) {
						elements.add(elementInMap);
					}
				}
			}
		}
		return elements;
	}
	
	public List<T> get(AABB boundary) {
		List<String> keys = hash(boundary);
		List<T> elements = new ArrayList<T>();
		for(String key : keys) {
			List<T> e = hashm.get(key);
			if(e != null) {
				for(T elementInMap : e) {
					if(!elements.contains(elementInMap)) {
						elements.add(elementInMap);
					}
				}
			}
		}
		return elements;
	}
	
	private void addHash(Vector2f vec, List<String> keys) {
		int x = (int) Math.floor(vec.x / (float) cellDimensions) * cellDimensions;
		int y = (int) Math.floor(vec.y / (float) cellDimensions) * cellDimensions;
		String key = x + ":" + y;
		if(!keys.contains(key)) {
			keys.add(key);
		}
	}

	public void clear() {
		hashm.clear();
	}
}
