package uk.ashigaru.engine.scene;

import java.util.ArrayList;
import java.util.List;

public class Scene {

	private List<GameObject> gameObjects;
	
	public Scene() {
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public void removeGameObject(int i) {
		gameObjects.remove(i);
	}
}
