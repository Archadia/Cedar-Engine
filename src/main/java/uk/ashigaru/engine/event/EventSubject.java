package uk.ashigaru.engine.event;

import java.util.ArrayList;
import java.util.List;

public class EventSubject {

	private List<EventInstance> observers = new ArrayList<EventInstance>();

	public void attach(EventInstance observer) {
		observers.add(observer);
	}

	public void execute(Object... obj) {
		for (EventInstance observer : this.observers) {
			observer.run(obj);
		}
	}
}