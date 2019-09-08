package uk.ashigaru.engine.font;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line implements Iterable<String> {

	private List<String> parts;
	
	public Line(String line, String delimiter) {
		String[] p = line.split(delimiter);
		parts = new ArrayList<String>();
		for(String part : p) {
			if(!part.replace(" ", "").trim().isEmpty()) parts.add(part);
		}
	}
	
	@Override
	public Iterator<String> iterator() {
		return parts.iterator();
	}
	
	public String get(int index) {
		return parts.get(index);
	}
	
	public boolean startsWith(String string) {
		return !parts.isEmpty() && parts.get(0).startsWith(string);
	}
}
