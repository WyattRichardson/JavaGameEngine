package core.gameobjects.entity;

import java.util.ArrayList;

import org.joml.Vector3f;

import core.gameobjects.model.Model;
import core.gameobjects.textures.ModelTexture;
import core.gameobjects.textures.Texture;

public class Entity {
	public static final int TEXTURE_INDEX = 0;
	
	
	public Transform transform;
	public Model model;
	public ArrayList<EntityComponent> components;
	public String id;
	public boolean hasController = false;
	
	public Entity(String id) {
		this.id = id;
		transform = new Transform(0,0,-1,0,0,0,1);
		components = new ArrayList<EntityComponent>();
	}
	
	public void tick(float dt) {}
	
	//TODO: setter methods for all components, maybe getters
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model m) {
		this.model = m;
	}
	
	public void setPosition(float x, float y, float z) {
		transform.position.x = x;
		transform.position.y = y;
		transform.position.z = z;
	}
	public void setRotation(float rx, float ry, float rz) {
		transform.rotation.x = rx;
		transform.rotation.y = ry;
		transform.rotation.z = rz;
	}
	public void setScale(float scale) {
		transform.scale = scale;
	}
	public Vector3f getPosition() {
		return this.transform.position;
	}
	
	public Vector3f getRotation() {
		return this.transform.rotation;
	}
	public float getScale() {
		return this.transform.scale;
	}
	
}
