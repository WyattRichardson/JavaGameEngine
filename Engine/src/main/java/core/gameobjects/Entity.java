package core.gameobjects;

import org.joml.Vector3f;

import core.gameobjects.textures.ModelTexture;

public class Entity {
	private Transform transform;
	private Model model;
	private ModelTexture texture;
	
	public Entity() {
		transform = new Transform(0,0,-1,0,0,0,1);
	}
	
	public Entity(Model model) {
		transform = new Transform(0,0,-1,0,0,0,1);
		this.model = model;
		
	}

	public Entity(Model model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
		transform = new Transform(0,0,-1,0,0,0,1);
	}
	
	public Entity(Model model, ModelTexture texture, float x, float y, float z, float scale) {
		this.model = model;
		this.texture = texture;
		transform = new Transform(x,y,z,0,0,0,scale);
	}
	
	
	
	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}
	
	public void setModel(Model model) {
		this.model = model;
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
	
	public Model getModel() {
		return this.model;
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
