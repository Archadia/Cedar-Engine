package uk.ashigaru.engine.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import uk.ashigaru.engine.Engine;
import uk.ashigaru.engine.event.EventSubject;
import uk.ashigaru.engine.misc.Logger;
import uk.ashigaru.engine.misc.Pair;

public class Input {

	public static EventSubject eventMouseClick = new EventSubject();
	public static EventSubject eventMouseScroll = new EventSubject();
	public static EventSubject eventMouseMove = new EventSubject();
	
	public static EventSubject eventKeyUsed = new EventSubject();
	public static EventSubject eventCharUsed = new EventSubject();
	
	public static void setCursorState(int cursor) {
		GLFW.glfwSetInputMode(Engine.getDisplay().windowID, GLFW.GLFW_CURSOR, cursor);
	}
	
	public static int getCursorState() {
		return GLFW.glfwGetInputMode(Engine.getDisplay().windowID, GLFW.GLFW_CURSOR);
	}
	
	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Engine.getDisplay().windowID, key) == GLFW.GLFW_PRESS;
	}
	
	public static boolean isKeyDown(String name) {
		if(Keybinds.hasBinding(name)) {
			return Input.isKeyDown(Keybinds.get(name));			
		} else {
			Logger.err("[Input] Keybind %s does not exist.", name);
			return false;
		}
	}
	
	public static boolean isMouseDown(int key) {
		return GLFW.glfwGetMouseButton(Engine.getDisplay().windowID, key) == GLFW.GLFW_PRESS;
	}
	
	private static int currMouseX, currMouseY;
	private static int lastMouseX, lastMouseY;

	public static void poll(double dt) {
		lastMouseX = currMouseX;
		lastMouseY = currMouseY;
		
		currMouseX = getMouseX();
		currMouseY = getMouseY();
		
		List<Integer> toRemoveRelease = new ArrayList<Integer>();
		for(int key : keyReleaseTimers.keySet()) {
			double v = keyReleaseTimers.get(key);
			if(v > 0) {
				keyReleaseTimers.put(key, Math.max(0, v - dt));
			}
			if(v <= 0) {
				toRemoveRelease.add(key);
			}
		}
		for(int key : toRemoveRelease) {
			keyReleaseTimers.remove(key);
		}
		
		List<Integer> toRemovePress = new ArrayList<Integer>();
		for(int key : keyPressTimers.keySet()) {
			double v = keyPressTimers.get(key);
			if(v > 0) {
				keyPressTimers.put(key, Math.max(0, v - dt));
			}
			if(v <= 0) {
				toRemovePress.add(key);
			}
		}
		for(int key : toRemovePress) {
			keyPressTimers.remove(key);
		}
	}
	
	public static void lock(int key, double timeLength) {
		keyReleaseTimers.put(key, timeLength);
		keyPressTimers.put(key, timeLength);
	}
	
	public static void lock(String key, double timeLength) {
		keyReleaseTimers.put(Keybinds.get(key), timeLength);
		keyPressTimers.put(Keybinds.get(key), timeLength);
	}
	
	public static int getMouseDX() {
		return getMouseX() - lastMouseX;
	}
	
	public static int getMouseDY() {
		return getMouseY() - lastMouseY;
	}
	
	public static Vector2f getMousePos() {
		double[] xArray = new double[1];
		double[] yArray = new double[1];
		GLFW.glfwGetCursorPos(Engine.getDisplay().windowID, xArray, yArray);
		return new Vector2f((float) xArray[0], (float) yArray[0]);
	}
	
	public static int getMouseX() {
		return (int) getMousePos().x;
	}
	
	public static int getMouseY() {
		return (int) getMousePos().y;
	}
	
	private static Map<Integer, List<Runnable>> keyReleaseObservers = new HashMap<Integer, List<Runnable>>();
	private static Map<Integer, Double> keyReleaseTimers = new HashMap<Integer, Double>();
	private static Map<Integer, Double> keyPressTimers = new HashMap<Integer, Double>();
	private static Map<Integer, List<Runnable>> keyPressObservers = new HashMap<Integer, List<Runnable>>();
	private static List<Runnable> leftClickObservers = new ArrayList<Runnable>();
	private static List<Runnable> rightClickObservers = new ArrayList<Runnable>();
	private static List<Runnable> scrollUpObservers = new ArrayList<Runnable>();
	private static List<Runnable> scrollDownObservers = new ArrayList<Runnable>();
	private static List<CharUsedEvent> charUsedObservers = new ArrayList<CharUsedEvent>();

	static {
		eventKeyUsed.attach((obj) -> {
			if ((int) obj[2] == GLFW.GLFW_RELEASE) {
				if(keyReleaseObservers.containsKey(obj[0])) {
						for(Runnable runnable : keyReleaseObservers.get(obj[0])) {
							if(!keyReleaseTimers.containsKey(obj[0])) {
								runnable.run();
							}
						}
					
				}
			} else if((int) obj[2] == GLFW.GLFW_PRESS) {
				if(keyPressObservers.containsKey(obj[0])) {
					for(Runnable runnable : keyPressObservers.get(obj[0])) {
						if(!keyPressTimers.containsKey(obj[0])) {
							runnable.run();
						}
					}
				}
			}
		});
		eventMouseClick.attach((obj) -> {
			if((int) obj[1] == GLFW.GLFW_RELEASE) {
				if((int) obj[0] == GLFW.GLFW_MOUSE_BUTTON_1) {
					for(Runnable runnable : leftClickObservers) {
						runnable.run();
					}
				} else if((int) obj[0] == GLFW.GLFW_MOUSE_BUTTON_2) {
					for(Runnable runnable : rightClickObservers) {
						runnable.run();
					}
				}
			} 
		});
		eventMouseScroll.attach((obj) -> {
			if((double) obj[1] > 0) {
				for(Runnable runnable : scrollUpObservers) {
					runnable.run();
				}
			} else if((double) obj[1] < 0) {
				for(Runnable runnable : scrollDownObservers) {
					runnable.run();
				}
			}
		});
		eventCharUsed.attach((obj) -> {
			charUsedObservers.forEach((x) -> {
				x.execute((int)obj[0]);
			});
		});
	}
	
	public static void onLeftClick(Runnable runnable) {
		leftClickObservers.add(runnable);
	}
	
	public static void onRightClick(Runnable runnable) {
		rightClickObservers.add(runnable);
	}
	
	public static void onScrollUp(Runnable runnable) {
		scrollUpObservers.add(runnable);
	}

	public static void onScrollDown(Runnable runnable) {
		scrollDownObservers.add(runnable);
	}
	
	public static void onCharUsed(CharUsedEvent event) {
		charUsedObservers.add(event);
	}
	
	public static void onKeyRelease(int key, Runnable runnable) {
		if(keyReleaseObservers.containsKey(key)) {
			keyReleaseObservers.get(key).add(runnable);
		} else {
			List<Runnable> list = new ArrayList<Runnable>();
			list.add(runnable);
			keyReleaseObservers.put(key, list);
		}
	}
	
	public static void onKeyRelease(String binding, Runnable runnable) {
		int key = Keybinds.get(binding);
		if(keyReleaseObservers.containsKey(key)) {
			keyReleaseObservers.get(key).add(runnable);
		} else {
			List<Runnable> list = new ArrayList<Runnable>();
			list.add(runnable);
			keyReleaseObservers.put(key, list);
		}
	}
	
	public static void onKeyPress(int key, Runnable runnable) {
		if(keyPressObservers.containsKey(key)) {
			keyPressObservers.get(key).add(runnable);
		} else {
			List<Runnable> list = new ArrayList<Runnable>();
			list.add(runnable);
			keyPressObservers.put(key, list);
		}
	}
	
	public static void onKeyPress(String binding, Runnable runnable) {
		int key = Keybinds.get(binding);
		if(keyPressObservers.containsKey(key)) {
			keyPressObservers.get(key).add(runnable);
		} else {
			List<Runnable> list = new ArrayList<Runnable>();
			list.add(runnable);
			keyPressObservers.put(key, list);
		}
	}
	
	@FunctionalInterface
	public static interface CharUsedEvent {
		public void execute(int c);
	}
}
