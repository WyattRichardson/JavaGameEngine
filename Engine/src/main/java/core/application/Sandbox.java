package core.application;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.*;

import core.gameobjects.*;
import core.rendering.*;
import core.utils.KeyInput;

public final class Sandbox { //TODO: Implement transformation matrix, delta time, and entity scripts.
	
	static { //Initialize GLFW statically to allow for multiple Sandbox applications
		
		if(!glfwInit()) {
			System.out.println("FAILED TO INITILIZE GLFW!");
		}
		
	
	}
	
	public long windowID;
	
	public EntityRenderer entityRenderer;
	
	public TerrainRenderer terrainRenderer;
		
	public static GLFWVidMode currentVidMode = null;
	
	public Sandbox(int width, int height, String title, float[] clearColor) { 
		windowID = glfwCreateWindow(width, height, title, 0, 0);
		GLFWErrorCallback.createPrint(System.err).set();
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);
		glfwSetKeyCallback(windowID, new KeyInput());
		currentVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		System.out.println();
		GL.createCapabilities();
		glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
		this.entityRenderer = new EntityRenderer();
	}
	
	public void init() {
		//Initialization logic
				
		run();
		glfwFreeCallbacks(windowID);
		glfwDestroyWindow(windowID);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public void run() {
		long lastTime = System.currentTimeMillis();
		float dt = 0;
		while(!glfwWindowShouldClose(windowID)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			dt = (float)(System.currentTimeMillis() - lastTime);//(delta time in seconds)
			glfwPollEvents();
			
			entityRenderer.render(dt);
			
			glfwSwapBuffers(windowID);
			lastTime = System.currentTimeMillis();
		}
	}
	
	
	public void addEntity(Entity entity) {
		entityRenderer.addEntity(entity);
	}
	
	public void cleanUp() {
		entityRenderer.cleanUp();
	}
	
	
}