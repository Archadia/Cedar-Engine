package uk.ashigaru.engine.state;

import java.util.HashMap;
import java.util.Map;

import uk.ashigaru.engine.Frame;
import uk.ashigaru.engine.misc.Logger;

public class StateMachine {

	private Map<String, IState> stateMap = new HashMap<String, IState>();
	private IState state;
	private Frame frame;
	
	public StateMachine(Frame frame) {
		this.frame = frame;
	}
	
	public void register(String name, IState state) {
		if(state == null) {
			Logger.err("Cannot register state as provided state is null.");
			return;
		}
		stateMap.put(name, state);
	}
	
	public IState get(String name) {
		return stateMap.get(name);
	}
	
	public boolean stateCheck(String name) {
		return stateMap.get(name) == this.state;
	}
	
	public void transition(String name) {
		if(state != null) {
			state.onStateExit();
		}
		if(!stateMap.containsKey(name)) {
			Logger.err("Cannot transition to " + name + " as it does not exist.");
			return;
		}
		this.state = stateMap.get(name);
		this.state.onStateEnter();
	}
	
	public void draw() {
		if(this.state == null) {
			return;
		}
		this.state.draw();
	}
	
	public void update(double t, double dt) {
		if(this.state == null) {
			return;
		}
		this.state.update(t, dt);
	}
	
	public void clear() {
		if(state != null) {
			state.onStateExit();
		}
		for(String key : stateMap.keySet()) {
			IState state = stateMap.get(key);
			if(state == null) {
				Logger.err("Cannot clear the state with key: " + key + " because it is null.");
			}
			state.onStateEnd();
		}
	}
}
