package uk.ashigaru.engine;

import uk.ashigaru.engine.state.StateMachine;
import uk.ashigaru.engine.window.Display;

public abstract class Frame {

	private boolean paused;
	private StateMachine stateMachine;
	
	public Frame() {
		stateMachine = new StateMachine(this);
	}
	
	public void setPaused(boolean bool) {
		this.paused = bool;
	}
	
	public boolean isPaused() {
		return this.paused;
	}
	
	public StateMachine getStateMachine() {
		return stateMachine;
	}
	
	public abstract void init();
	public abstract void setupWindow(Display display);
	public abstract void earlyDraw();
	public abstract void earlyUpdate(double t, double dt);
	public abstract void deinit();
}
