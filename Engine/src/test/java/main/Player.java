package main;

import static org.lwjgl.glfw.GLFW.*;

import core.gameobjects.entity.Entity;
import core.gameobjects.entity.EntityController;
import core.gameobjects.entity.Transform;
import core.utils.KeyInput;

public class Player extends EntityController{

	public float speed = 10;
	public Player() {
		
	}

	@Override
	public Entity run(Entity entity, float dt) {
		entity.transform = updateTransform(entity.transform, dt);
		return entity;
	}

	
	public Transform updateTransform(Transform transform, float dt) {
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			transform.position.y += (speed * dt);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			transform.position.y -= (speed * dt);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			transform.position.x -= (speed * dt);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_D)){
			transform.position.x += (speed * dt);
		}
		return transform;
	}


}
