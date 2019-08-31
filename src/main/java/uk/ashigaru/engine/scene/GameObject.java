package uk.ashigaru.engine.scene;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import uk.ashigaru.engine.components.IGameComponent;

public class GameObject {
	
	private List<IGameComponent> components;
	
	public Vector3f position;
	public Vector3f scale;
	public Quaternionf quaternion;
	
	public GameObject(Vector3f pos, Vector3f scale) {
		this.position = new Vector3f(pos);
		this.scale = new Vector3f(scale);
		this.quaternion = new Quaternionf();		

		this.components = new ArrayList<IGameComponent>();
	}
	
	public void addComponent(IGameComponent component) {
		this.components.add(component);
		component.onAssign(this);
	}
	
	public List<IGameComponent> getComponents(Class<?> type) {
		List<IGameComponent> list = new ArrayList<IGameComponent>();
		for(IGameComponent c : components) {
			if(type.isInstance(c)) {
				list.add(c);
			}
		}
		return list;
	}
	
	public GameObject() {
		this(new Vector3f(0,0,0), new Vector3f(1,1,1));
	}

	public Vector3f getEulerAngles() {
		Vector3f vector = new Vector3f();
		quaternion.getEulerAnglesXYZ(vector);
		return vector;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f matTranslation = new Matrix4f();
		Matrix4f matRotation = new Matrix4f();
		Matrix4f matScale = new Matrix4f();
		
		matTranslation.translate(position.x, position.y, position.z);
		quaternion.get(matRotation);
		matScale.scale(scale.x, scale.y, scale.z);
		return matTranslation.mul(matRotation).mul(matScale);
	}
}
