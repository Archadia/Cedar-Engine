package uk.ashigaru.engine;

public interface IGameLoop {
	public void update(double t, double dt);
	public void draw();
	public void end();
}
