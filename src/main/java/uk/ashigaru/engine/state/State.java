package uk.ashigaru.engine.state;

public interface State {

	public void init();
	public void deinit();
	
	public void update();
	public void draw();

	public void pause();
	public void resume();
}
