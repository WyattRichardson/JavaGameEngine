package main;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector3f;

import core.application.Sandbox;
import core.gameobjects.entity.Entity;
import core.gameobjects.entity.EntityComponent;
import core.gameobjects.lighting.Light;
import core.gameobjects.model.Model;

public class Main {
	
	public static final int WINDOW_WIDTH = 1080;
	public static final int WINDOW_HEIGHT = 720;
	public static final String WINDOW_TITLE = "Java Game Engine";
	public static final float[] CLEAR_COLOR = {0.3f,0.3f,0.3f,1};
	
	
	
	
	public static void main(String[] args) {
		
		Sandbox sandbox = new Sandbox(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, CLEAR_COLOR);
		Model squareModel = new Model("Square.obj", GL_TRIANGLES);
		Model mando = new Model("Mandalorian.obj", GL_TRIANGLES);
		Light sunLight = new Light(new Vector3f(0,200,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
		
		Player player_1 = new Player("player_1");
		player_1.addComponent(mando);
		player_1.setPosition(0, 0, -100);
		player_1.hasController = true;
		sandbox.addEntity(player_1);
		
		
		MovingLight sun = new MovingLight("Sun_Light");
		sun.addComponent(sunLight);
		sun.hasController = true;
		sandbox.addLightEntity(sun);
		
		sandbox.init(); 
		
	}
}
