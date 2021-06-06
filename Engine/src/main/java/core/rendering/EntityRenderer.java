package core.rendering;

import core.gameobjects.entity.Entity;
import core.gameobjects.model.Model;
import core.rendering.shaders.ModelShader;
import core.utils.KeyInput;
import core.utils.Math;
import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL30.*;
public class EntityRenderer{

		public HashMap<Model, ArrayList<Entity>> entities;
		
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

				ArrayList<Entity> batch = entities.get(model);
				int batchSize = batch.size();
				for(int i = 0; i < batchSize; i++) {
					Entity entity = batch.get(i);
					
					if(isInFOV(entity.getPosition())) {
						if(entity.hasController) {
							entity.tick(dt);
						}
						prepareEntity(entity);
						//If controller is not working correctly then: batch.get(i) = entity;
						glDrawElements(model.getFaceType(), model.getIndicyCount(), GL_UNSIGNED_INT, 0);
					}
				
				}
				glDisableVertexAttribArray(0);
				glDisableVertexAttribArray(1);
				glDisableVertexAttribArray(2);
				
				glBindVertexArray(0);
			
			}
			
			glUseProgram(0);
		
		}
		
		public void prepareEntity(Entity entity) {
			float[] modelTransformMat = new float[16];
			Math.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale()).get(modelTransformMat);
			glUniformMatrix4fv(modelShader.uniformLocations.get("transformationMatrix"), false, modelTransformMat);
		}
		
		public boolean isInFOV(Vector3f position) {
			return true; //TODO: implement
		}
		public void addEntity(Entity entity) {
			Model model = entity.getModel();
			if(!entities.containsKey(model)) {
				entities.put(model, new ArrayList<Entity>());
			}
			entities.get(model).add(entity);
		}
		
		public void cleanUp() {
			glDeleteProgram(modelShader.getID());
			//TODO: cycle through all models and call a cleanup function defined within Model class
		}

}