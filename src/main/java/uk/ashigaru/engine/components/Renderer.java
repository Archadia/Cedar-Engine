package uk.ashigaru.engine.components;

import java.util.ArrayList;
import java.util.List;

import uk.ashigaru.engine.Engine;
import uk.ashigaru.engine.loop.IGameLoop;
import uk.ashigaru.engine.scene.Camera;
import uk.ashigaru.engine.scene.GameObject;

public abstract class Renderer implements IGameComponent {

	public abstract void init();
	public abstract void draw(Camera camera);
	
	protected Camera camera;
	
	protected List<GameObject> subjects;
	
	public Renderer() {
		this.subjects = new ArrayList<GameObject>();
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void addSubject(GameObject go) {
		this.subjects.add(go);
	}
	
	@Override
	public void onAssign(GameObject gameObject) {
		addSubject(gameObject);
	}
}
