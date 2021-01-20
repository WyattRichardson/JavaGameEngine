package core.rendering;

import core.gameobjects.*;
import core.rendering.shaders.ModelShader;
import core.utils.KeyInput;
import core.utils.Math;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL30.*;
public class EntityRenderer{

		public static HashMap<Model, ArrayList<Entity>> entities;
		
		private ModelShader modelShader;
		
		public EntityRenderer() {
			entities = new HashMap<Model, ArrayList<Entity>>();
			modelShader = new ModelShader("src/main/resources/assets/shaders/modelVertShader.txt", "src/main/resources/assets/shaders/modelFragShader.txt");
		}
		
		public void render(float dt) {
			
			glUseProgram(modelShader.getID());
			
			for(Model model: entities.keySet()) {
				
				int vaoID = model.getVAO();
				
				glBindVertexArray(vaoID);
				
				glEnableVertexAttribArray(0);				
				glEnableVertexAttribArray(1);
				glEnableVertexAttribArray(2);
				
				for(Entity e: entities.get(model)) {
					
					updateEntity(e);
					
					float[] modelTransformMat = new float[16];
					Math.createTransformationMatrix(e.getPosition(), e.getRotation(), e.getScale()).get(modelTransformMat);
					glUniformMatrix4fv(modelShader.uniformLocations.get("transformationMatrix"), false, modelTransformMat);
					
					glDrawElements(model.getFaceType(), model.getIndicyCount(), GL_UNSIGNED_INT, 0);
				
				
				}
				glDisableVertexAttribArray(0);
				glDisableVertexAttribArray(1);
				glDisableVertexAttribArray(2);
				
				glBindVertexArray(0);
			
			}
			
			glUseProgram(0);
		
		}
		
		public void updateEntity(Entity e) {
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
				e.setRotation(e.getRotation().x + (6f), e.getRotation().y, e.getRotation().z);
			}
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
				e.setPosition(e.getPosition().x - (0.1f), e.getPosition().y, e.getPosition().z);
			}
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_UP)) {
				e.setPosition(e.getPosition().x, e.getPosition().y + (0.1f), e.getPosition().z);
			}
			if(KeyInput.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
				e.setPosition(e.getPosition().x, e.getPosition().y - (0.1f), e.getPosition().z);
			}
		}
		
		public void addEntity(Entity entity) {
			
			Model entityModel = entity.getModel();
			
			if(!entities.containsKey(entityModel)) {
				entities.put(entityModel, new ArrayList<Entity>());
			}
			
			entities.get(entityModel).add(entity);

		}
		
		public void cleanUp() {
			glDeleteProgram(modelShader.getID());
			//TODO: cycle through all models and call a cleanup function defined within Model class
		}

}