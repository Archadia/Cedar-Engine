package uk.ashigaru.engine;

import org.lwjgl.opengl.GL11;

import uk.ashigaru.engine.event.EventSubject;
import uk.ashigaru.engine.window.Display;
import uk.ashigaru.engine.window.Input;

public class Engine {

	private static Display display;
	private static Frame frame;
	public static EventSubject eventDisplayResize = new EventSubject();

	public static void clearGLBuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static Frame getFrame() {
		return frame;
	}
	
	public static Display getDisplay() {
		return display;
	}
	
	public static void start(Class<? extends Frame> frameClass, int width, int height, String title, boolean vsync, boolean fullscreen) {
		try {
			Engine.frame = frameClass.newInstance();
			display = new Display();
			frame.setupWindow(display);
			display.create(width, height, title, vsync, fullscreen);
			frame.init();		
			
			double t = 0.0;
			final double dt = 0.01;
			
			double currentTime = System.nanoTime() / 1000000000.0;
			double accumulator = 0.0;
				
			while(display.isDisplayActive()) {
				double newTime = System.nanoTime() / 1000000000.0;
				double frameTime = newTime - currentTime;
				currentTime = newTime;
				
				accumulator += frameTime;
				
				while(accumulator >= dt) {
					frame.earlyUpdate(t, dt);
					frame.getStateMachine().update(t, dt);
					Input.poll(dt);
					accumulator -= dt;
					t += 1;
				}
				frame.earlyDraw();
				frame.getStateMachine().draw();
				display.update();
			}
			frame.deinit();
			display.kill();
			System.exit(0);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
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
