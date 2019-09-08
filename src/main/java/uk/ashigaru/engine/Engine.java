package uk.ashigaru.engine;

import org.lwjgl.opengl.GL11;

import uk.ashigaru.engine.loop.GameLoopRequester;
import uk.ashigaru.engine.window.Display;
import uk.ashigaru.engine.window.Viewport;

public class Engine {

	private static Display display;
	private static GameLoopRequester glRequester;
	
	private static Object child;
	
	public static void setChild(Object c) {
		child = c;
	}
	
	public static Object getChild() {
		return child;
	}

	public static void clearGLBuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public static Display getDisplay() {
		return display;
	}
	
	public static long getDisplayID() {
		return display.getWindowID();
	}
	
	public static GameLoopRequester getGameLoopRequester() {
		return glRequester;
	}

	public static void initialiseGLFW() {
		display = new Display();
		glRequester = new GameLoopRequester();
	}
	
	public static void createWindow(int width, int height, String title, boolean vsync, boolean fullscreen) {
		display.create(width, height, title, vsync, fullscreen);
	}
	
	private static double fps;
	private static double ups;
	
	public static void createGameLoop() {
		double t = 0.0;
		final double dt = 0.01;
			
		double currentTime = System.nanoTime() / 1000000000.0;
		double accumulator = 0.0;
		
		double ct = System.nanoTime() / 1000000000.0;
		double ctups = System.nanoTime() / 1000000000.0;
		
		int count = 0;
		int countUPS = 0;
		
		while(display.isDisplayActive()) {
			double newTime = System.nanoTime() / 1000000000.0;
			double frameTime = newTime - currentTime;
			currentTime = newTime;
				
			accumulator += frameTime;
			
			while(accumulator >= dt) {
				glRequester.update(t, dt);
				//input poll
				accumulator -= dt;
				countUPS += 1;
				double ntups = System.nanoTime() / 1000000000.0;
				if(ntups - ctups >= 1.0) {
					ups = countUPS;
					countUPS = 0;
					ctups = ntups;
				}
				t += 1;
			}
			glRequester.draw();
			display.update();
			
			count += 1;
			double nt = System.nanoTime() / 1000000000.0;
			if(nt - ct >= 1.0) {
				fps = count;
				count = 0;
				ct = nt;
			}
		}
		glRequester.exit();
		display.kill();
		System.exit(0);
	}
	
	public static double getFPS() {
		return fps;
	}
	
	public static double getUPS() {
		return ups;
	}
	
	public static boolean isDev() {
		String e = System.getProperty("eclipse");
		return e == null ? false : e.equalsIgnoreCase("true");
	}

	public static int getWidth() {
		return display.getWidth();
	}
	
	public static int getHeight() {
		return display.getHeight();
	}
	
	public static float getAR() {
		return (float) display.getWidth() / (float) display.getHeight();
	}
}
