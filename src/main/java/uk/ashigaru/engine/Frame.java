package uk.ashigaru.engine;

import uk.ashigaru.engine.glfw.Display;

public interface Frame {

	public void init();
	public void setupWindow(Display display);
	public void draw(float dt);
	public void update();
	public void deinit();
}
