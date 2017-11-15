package uk.ashigaru.engine.glfw;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import uk.ashigaru.engine.Launcher;
import uk.ashigaru.engine.observer.EventSubject;

public class Input {

	public static EventSubject eventMouseClick = new EventSubject();
	public static EventSubject eventMouseScroll = new EventSubject();
	public static EventSubject eventMouseMove = new EventSubject();
	
	public static EventSubject eventKeyUsed = new EventSubject();
	
	public static void setCursorState(int cursor) {
		GLFW.glfwSetInputMode(Launcher.display.windowID, GLFW.GLFW_CURSOR, cursor);
	}
	
	public static int getCursorState() {
		return GLFW.glfwGetInputMode(Launcher.display.windowID, GLFW.GLFW_CURSOR);
	}
	
	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Launcher.display.windowID, key) == GLFW.GLFW_PRESS;
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
		GLFW.glfwGetCursorPos(Launcher.display.windowID, xArray, yArray);
		return new Vector2f((float) xArray[0], (float) yArray[0]);
	}
	
	public static int getMouseX() {
		return (int) getMousePos().x;
	}
	
	public static int getMouseY() {
		return (int) getMousePos().y;
	}

}
