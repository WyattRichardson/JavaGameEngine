package core.rendering;

import core.gameobjects.entity.Entity;
import core.gameobjects.entity.EntityComponent;
import core.gameobjects.lighting.Light;
import core.gameobjects.model.Model;
import core.rendering.shaders.ModelShader;
import core.utils.KeyInput;
import core.utils.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer {

	public HashMap<Model, ArrayList<Entity>> entities;
	public HashMap<Model, ArrayList<Entity>> lightEntities;

	private static Model lightEntityNoModel = new Model();

	private ModelShader modelShader;

	public EntityRenderer() {
		entities = new HashMap<Model, ArrayList<Entity>>();
		lightEntities = new HashMap<Model, ArrayList<Entity>>();
		lightEntities.put(lightEntityNoModel, new ArrayList<Entity>());

		modelShader = new ModelShader("src/main/resources/assets/shaders/modelVertShader.txt",
				"src/main/resources/assets/shaders/modelFragShader.txt");
	}

	public void render(float dt) {

		glUseProgram(modelShader.getID());
		renderLightEntities(dt);
		renderModelEntities(dt);

		glUseProgram(0);

	}

	public void renderLightEntities(float dt) { // primary purpose of separating into two methods is to ensure entities
		for (Model model : lightEntities.keySet()) {
			if (!model.equals(lightEntityNoModel)) {
				int vaoID = model.getVAO();

				glBindVertexArray(vaoID);

				glEnableVertexAttribArray(0);
				glEnableVertexAttribArray(1);
				glEnableVertexAttribArray(2);

				ArrayList<Entity> batch = entities.get(model);
				for (Entity entity: batch) {

					if (isInFOV(entity.getPosition())) {
						if (entity.hasController) {
							entity.tick(dt);
						}
						prepareLightEntity(entity);
						// If controller is not working correctly then: batch.get(i) = entity;
						glDrawElements(model.getFaceType(), model.getIndicyCount(), GL_UNSIGNED_INT, 0);
					}

				}
				glDisableVertexAttribArray(0);
				glDisableVertexAttribArray(1);
				glDisableVertexAttribArray(2);

				glBindVertexArray(0);
			} else {
				ArrayList<Entity> batch = lightEntities.get(lightEntityNoModel);
				for (Entity entity: batch) {
					if (entity.hasController) {
						entity.tick(dt);
					}
					prepareLightEntity(entity);

				}
			}
		}
	}

	public void renderModelEntities(float dt) {
		for (Model model : entities.keySet()) {

			int vaoID = model.getVAO();

			glBindVertexArray(vaoID);

			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);

			ArrayList<Entity> batch = entities.get(model);
			
			for (Entity entity: batch) {

				if (isInFOV(entity.getPosition())) {
					if (entity.hasController) {
						entity.tick(dt);
					}
					prepareEntity(entity);
					// If controller is not working correctly then: batch.get(i) = entity;
					glDrawElements(model.getFaceType(), model.getIndicyCount(), GL_UNSIGNED_INT, 0);
				}

			}
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glDisableVertexAttribArray(2);

			glBindVertexArray(0);

		}
	}

	public void prepareLightEntity(Entity entity) {
		Light light = (Light) entity.getComponentsByType(EntityComponent.TYPE_LIGHT).get(0);
		//TODO create for loop that loops through the list and updates uniforms respectively.
		glUniform3f(modelShader.uniformLocations.get("lightPosition"), light.getPosition().x, light.getPosition().y, light.getPosition().z);
		
	}

	public void prepareEntity(Entity entity) {
		float[] modelTransformMat = new float[16];
		Math.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale())
				.get(modelTransformMat);
		glUniformMatrix4fv(modelShader.uniformLocations.get("transformationMatrix"), false, modelTransformMat);
	}

	public boolean isInFOV(Vector3f position) {
		return true; // TODO: implement
	}

	public void addEntity(Entity entity) {
		Model model = (Model) entity.getComponentsByType(EntityComponent.TYPE_MODEL).get(0);
		if (!entities.containsKey(model)) {
			entities.put(model, new ArrayList<Entity>());
		}
		entities.get(model).add(entity);
	}

	public void addLightEntity(Entity entity) {

		List<EntityComponent> models = entity.getComponentsByType(EntityComponent.TYPE_MODEL);
		Model model = null;
		if(models.size() != 0) {
			model = (Model) models.get(0);
		}
		if (model != null && !lightEntities.containsKey(model)) {
			lightEntities.put(model, new ArrayList<Entity>());
		}
		if (model != null) {
			lightEntities.get(model).add(entity);
		} else {
			lightEntities.get(lightEntityNoModel).add(entity);
		}

	}

	public void cleanUp() {
		glDeleteProgram(modelShader.getID());
		// TODO: cycle through all models and call a cleanup function defined within
		// Model class
	}

}