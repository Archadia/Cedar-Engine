package uk.ashigaru.engine;

import org.lwjgl.opengl.GL11;

import uk.ashigaru.engine.glfw.Display;
import uk.ashigaru.engine.glfw.Input;
import uk.ashigaru.engine.observer.EventSubject;

public class Engine {

	public static Display display;
	
	private static final int TICKS_PER_SECOND = 25;
	private static final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private static final int MAX_FRAME_SKIP = 5;
	
	private static long nextGameTick = System.nanoTime();
	private static long loops;
	private static float interpolation;
	
	public static EventSubject eventDisplayResize = new EventSubject();

	public static void clearGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void start(Frame frame, int width, int height, String title, boolean vsync, boolean fullscreen) {
		display = new Display();
		frame.setupWindow(display);
		display.create(width, height, title, vsync, fullscreen);
		frame.init();		
		while(display.isDisplayActive()) {
			loops = 0;
			while(System.nanoTime() > nextGameTick && loops < MAX_FRAME_SKIP) {
				frame.update();
				Input.poll();
				nextGameTick += SKIP_TICKS;
				loops = loops + 1;
			}
			
			interpolation = ((float)(System.nanoTime() + SKIP_TICKS - nextGameTick)) / ((float)(SKIP_TICKS));
			frame.draw(interpolation);
			display.update();
		}
		frame.deinit();
		display.kill();
	}
	
	public static float getInterpolation() {
		return interpolation;
	}
	
	public static int getWidth() {
		return display.getWidth();
	}
	
	public static int getHeight() {
		return display.getHeight();
	}
	
	public static int gwp(float percent) {
		return (int) (getWidth() * percent);
	}
	
	public static int ghp(float percent) {
		return (int) (getHeight() * percent);
	}
	
	public static float getAR() {
		return (float) display.getWidth() / (float) display.getHeight();
	}
}