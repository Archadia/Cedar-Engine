package uk.ashigaru.engine.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Batcher<Batchee> {

	private Map<Integer, List<Batchee>> batch = new HashMap<Integer, List<Batchee>>();
	
	public void add(int id, Batchee obj) {
		if(batch.containsKey(id)) {
			batch.get(id).add(obj);
		} else {
			List<Batchee> list = new ArrayList<Batchee>();
			list.add(obj);
			batch.put(id, list);
		}
	}
	
	public List<Batchee> get(int i) {
		return batch.get(i);
	}
	
	public Set<Integer> getKeySet() {
		return batch.keySet();
	}
	
	public boolean contains(int i) {
		return batch.containsKey(i);
	}	
}
