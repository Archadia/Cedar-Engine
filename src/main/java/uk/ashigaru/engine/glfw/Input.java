package uk.ashigaru.engine.glfw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import uk.ashigaru.engine.Engine;
import uk.ashigaru.engine.observer.EventSubject;

public class Input {

	public static EventSubject eventMouseClick = new EventSubject();
	public static EventSubject eventMouseScroll = new EventSubject();
	public static EventSubject eventMouseMove = new EventSubject();
	
	public static EventSubject eventKeyUsed = new EventSubject();
	
	public static void setCursorState(int cursor) {
		GLFW.glfwSetInputMode(Engine.display.windowID, GLFW.GLFW_CURSOR, cursor);
	}
	
	public static int getCursorState() {
		return GLFW.glfwGetInputMode(Engine.display.windowID, GLFW.GLFW_CURSOR);
	}
	
	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Engine.display.windowID, key) == GLFW.GLFW_PRESS;
	}
	
	private static int currMouseX, currMouseY;
	private static int lastMouseX, lastMouseY;

	public static void poll() {
		lastMouseX = currMouseX;
		lastMouseY = currMouseY;
		
		currMouseX = getMouseX();
		currMouseY = getMouseY();
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
		GLFW.glfwGetCursorPos(Engine.display.windowID, xArray, yArray);
		return new Vector2f((float) xArray[0], (float) yArray[0]);
	}
	
	public static int getMouseX() {
		return (int) getMousePos().x;
	}
	
	public static int getMouseY() {
		return (int) getMousePos().y;
	}
	
	private static Map<Integer, List<Runnable>> keyPressObservers = new HashMap<Integer, List<Runnable>>();
	private static List<Runnable> leftClickObservers = new ArrayList<Runnable>();
	private static List<Runnable> rightClickObservers = new ArrayList<Runnable>();

	static {
		eventKeyUsed.attach((obj) -> {
			if ((int) obj[2] == GLFW.GLFW_RELEASE) {
				if(keyPressObservers.containsKey(obj[0])) {
					for(Runnable runnable : keyPressObservers.get(obj[0])) {
						runnable.run();
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
	}
	
	public static void onLeftClick(Runnable runnable) {
		leftClickObservers.add(runnable);
	}
	
	public static void onRightClick(Runnable runnable) {
		rightClickObservers.add(runnable);
	}

	public static void onKeyRelease(int key, Runnable runnable) {
		if(keyPressObservers.containsKey(key)) {
			keyPressObservers.get(key).add(runnable);
		} else {
			List<Runnable> list = new ArrayList<Runnable>();
			list.add(runnable);
			keyPressObservers.put(key, list);
		}
	}
}
