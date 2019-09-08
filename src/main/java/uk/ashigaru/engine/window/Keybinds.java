package uk.ashigaru.engine.window;

import java.util.HashMap;
import java.util.Map;

public class Keybinds {

	private static Map<String, Integer> keybinds = new HashMap<String, Integer>();
	
	public static void register(String name, int key) {
		keybinds.put(name, key);
	}
	
	public static int get(String name) {
		return keybinds.get(name);
	}

	public static boolean hasBinding(String name) {
		return keybinds.containsKey(name);
	}
}
