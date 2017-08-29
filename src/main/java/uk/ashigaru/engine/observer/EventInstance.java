package uk.ashigaru.engine.observer;

@FunctionalInterface
public interface EventInstance {
	
	public abstract void run(Object... obj);
}