package testing;
import static org.lwjgl.opengl.GL30.*;

import core.gameobjects.Model;
import core.application.Sandbox;
import core.gameobjects.*;

public class Main {
	
	public static final int WINDOW_WIDTH = 1080;
	public static final int WINDOW_HEIGHT = 720;
	public static final String WINDOW_TITLE = "Java Game Engine";
	public static final float[] CLEAR_COLOR = {0.3f,0.3f,0.3f,1};
	
	
	
	
	public static void main(String[] args) {
		
		Sandbox sandbox = new Sandbox(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, CLEAR_COLOR);
		Model squareModel = new Model("Square.obj", GL_TRIANGLES);
		Entity square_1 = new Entity(squareModel);
		square_1.setPosition(0, 0, -10);
		sandbox.addEntity(square_1);
		sandbox.init(); 
		
	}
}
