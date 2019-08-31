package uk.ashigaru.engine.loop;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameLoopRequester {

	private Map<Integer, IDraw> drawLoops = new HashMap<Integer, IDraw>();
	private Map<Integer, IUpdate> updateLoops = new HashMap<Integer, IUpdate>();
	private Map<Integer, IExit> exit = new HashMap<Integer, IExit>();
	
	private AtomicInteger drawCounter = new AtomicInteger();
	private AtomicInteger updateCounter = new AtomicInteger();
	private AtomicInteger exitCounter = new AtomicInteger();

	public synchronized int requestDraw(IDraw gl) {
		int id = drawCounter.getAndIncrement();
		drawLoops.put(id, gl);
		return id;
	}
	
	public void deleteDraw(int id) {
		drawLoops.remove(id);
	}
	
	public synchronized int requestUpdate(IUpdate gl) {
		int id = updateCounter.getAndIncrement();
		updateLoops.put(id, gl);
		return id;
	}
	
	public void deleteUpdate(int id) {
		updateLoops.remove(id);
	}
	
	public synchronized int requestExit(IExit gl) {
		int id = exitCounter.getAndIncrement();
		exit.put(id, gl);
		return id;
	}
	
	public void deleteExit(int id) {
		exit.remove(id);
	}
	
	public synchronized void requestGameLoop(IGameLoop gameLoop) {
		requestDraw(gameLoop);
		requestUpdate(gameLoop);
		requestExit(gameLoop);
	}

	public void update(double t, double dt) {
		updateLoops.forEach((x, y) -> {
			y.update(t, dt);
		});
	}

	public void draw() {
		drawLoops.forEach((x, y) -> {
			y.draw();
		});
	}
	
	public void exit() {
		exit.forEach((x, y) -> {
			y.exit();
		});
	}
}
