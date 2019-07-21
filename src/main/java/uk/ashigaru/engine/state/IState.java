package uk.ashigaru.engine.state;

public interface IState {

	/**
	 * Sets a public flag inside the state to either true/false.
	 */
	public void setFlag(String key, boolean bool);
	
	/**
	 * Retrieves a public flag from the state.
	 */
	public boolean getFlag(String key);
	
	/**
	 * To be called when the state is destroyed and removed from the stack's map.
	 */
	public void onStateEnd();	
	
	/**
	 * Called when the state is no longer the head of the stack.
	 */
	public void onStateExit();
	
	/**
	 * Called when the state becomes the head of the stack.
	 */
	public void onStateEnter();
	
	/**
	 * Called when the state is the head of the stack and is being updated.
	 */
	public void update(double t, double dt);
	
	/**
	 * Called when the state is the head of the stack and is being drawn.+
	 */
	public void draw();

}
