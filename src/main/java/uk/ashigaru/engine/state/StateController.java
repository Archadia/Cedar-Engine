package uk.ashigaru.engine.state;

public class StateController {

	public State currentState;
	
	public StateController() {}
	
	public StateController(State firstState) {
		switchState(firstState);
	}
	
	public void switchState(State state) {
		if(this.currentState != null) this.currentState.deinit();
		this.currentState = state;
		this.currentState.init();
	}

	public void pause() {
		if(currentState != null) currentState.pause();
	}
	
	public void resume() {
		if(currentState != null) currentState.resume();
	}
	
	public void draw() {
		if(currentState != null) currentState.draw();
	}
	
	public void update() {
		if(currentState != null) currentState.update();
	}
	
	public void deinit() {
		if(this.currentState != null) this.currentState.deinit();
	}
}
