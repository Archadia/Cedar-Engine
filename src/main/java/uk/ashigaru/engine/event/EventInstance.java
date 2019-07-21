package uk.ashigaru.engine.event;

@FunctionalInterface
public interface EventInstance {
	
	public abstract void run(Object... obj);
}