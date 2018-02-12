package uk.ashigaru.engine.glfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.CharBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import uk.ashigaru.engine.Engine;

public class Display {
	
	private GLFWKeyCallback glfwKeyCallback;
	private GLFWMouseButtonCallback glfwMouseButtonCallback;
	private GLFWCursorPosCallback glfwMouseMoveCallback;
	private GLFWScrollCallback glfwMouseScrollCallback;
	private GLFWWindowSizeCallback glfwWindowSizeCallback;
	private GLFWErrorCallback glfwErrorCallback;

	public long windowID;

	public boolean isDisplayActive() {
		return !(glfwWindowShouldClose(windowID));
	}
	
	public Display() {
		glfwSetErrorCallback(glfwErrorCallback = GLFWErrorCallback.createPrint(System.err));

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialise GLFW");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_SAMPLES, 4);
	}
	
	public void kill() {
		glfwWindowSizeCallback.free();
		glfwMouseButtonCallback.free();
		glfwMouseScrollCallback.free();
		glfwMouseMoveCallback.free();
		glfwDestroyWindow(windowID);
		glfwKeyCallback.free();
		glfwTerminate();
		glfwErrorCallback.free();
	}

	public void create(int width, int height, String title, boolean vsync, boolean fullscreen) {
		windowID = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
		if (windowID == NULL) {
			throw new RuntimeException("Failed to create GLFW window");
		}
		glfwSetKeyCallback(windowID, glfwKeyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(window != windowID) return;
				Input.eventKeyUsed.execute(key, scancode, action, mods);
			}			
		});
		glfwSetWindowSizeCallback(windowID, glfwWindowSizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int width, int height) {
				if(window != windowID) return;
				GL11.glViewport(0, 0, width, height);
				Engine.eventDisplayResize.execute(width, height);
			}
		});
		glfwSetMouseButtonCallback(windowID, glfwMouseButtonCallback = new GLFWMouseButtonCallback() {
			public void invoke(long window, int button, int action, int mods) {
				if(window != windowID) return;
				Input.eventMouseClick.execute(button, action, mods);
			}
		});
		glfwSetScrollCallback(windowID, glfwMouseScrollCallback = new GLFWScrollCallback() {
			public void invoke(long window, double xoffset, double yoffset) {
				if(window != windowID) return;
				Input.eventMouseScroll.execute(xoffset, yoffset);
			}
		});
		glfwSetCursorPosCallback(windowID, glfwMouseMoveCallback = new GLFWCursorPosCallback() {
			public void invoke(long window, double xpos, double ypos) {
				if(window != windowID) return;
				Input.eventMouseMove.execute(xpos, ypos);
			}
		});
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowMonitor(windowID, fullscreen ? glfwGetPrimaryMonitor() : NULL, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2, width, height, vidmode.refreshRate());
		
		glfwMakeContextCurrent(windowID);
		if(vsync) glfwSwapInterval(1);
		GL.createCapabilities();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public int getWidth() {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(windowID, width, null);
		return width.get();
	}
	
	public int getHeight() {
		IntBuffer height = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(windowID, null, height);
		return height.get();
	}
	
	public float getAspectRatio() {
		return (float) getWidth()/ (float) getHeight();
	}

	public void setResizeable(boolean bool) {
		glfwWindowHint(GLFW_RESIZABLE, bool ? GLFW_TRUE : GLFW_FALSE);
	}
	
	public void setTitle(String title) {
		CharBuffer buffer = (CharBuffer) BufferUtils.createCharBuffer(title.length()).put(title).flip();
		glfwSetWindowTitle(windowID, buffer);
	}
	
	public void update() {
		glfwSwapBuffers(windowID);
		glfwPollEvents();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public long getWindowID() {
		return windowID;
	}
}