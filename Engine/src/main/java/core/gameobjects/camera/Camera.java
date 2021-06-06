package core.gameobjects.camera;

import org.joml.Vector3f;

import core.gameobjects.entity.EntityComponent;
import core.gameobjects.entity.Transform;

public class Camera extends EntityComponent{
	
	private Transform transform;
	
	public Camera() {
		// TODO Auto-generated constructor stub
	}
	
	public Vector3f getPosition() {
		return transform.position;
	}
	
	public Vector3f getRotation() {
		return transform.rotation;
	}

}
