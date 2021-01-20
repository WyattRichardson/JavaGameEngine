package core.rendering.shaders;
import static org.lwjgl.opengl.GL30.*;

import core.application.Sandbox;
import core.utils.Math;
public class ModelShader extends ShaderProgram {
	
	private int programID;
	
	public ModelShader(String vertPath, String fragPath) {
		super();
		this.programID = glCreateProgram();
		int vertID = loadShader(vertPath, GL_VERTEX_SHADER);
		int fragID = loadShader(fragPath, GL_FRAGMENT_SHADER);
		glAttachShader(programID, vertID);
		glAttachShader(programID, fragID);
		glLinkProgram(programID);
		glValidateProgram(programID);
		glUseProgram(programID);
		getUniformLocations();
		glUseProgram(0);
		glDeleteShader(vertID);
		glDeleteShader(fragID);
		
	}

	
	
	public int getID() {
		return this.programID;
	}



	@Override
	public void getUniformLocations() {
		uniformLocations.put("projectionMatrix", glGetUniformLocation(this.programID, "projectionMatrix"));
		float[] projectionMat = new float[16];
		Math.createProjectionMatrix(Sandbox.currentVidMode).get(projectionMat);
		glUniformMatrix4fv(uniformLocations.get("projectionMatrix"), false, projectionMat);
		
		uniformLocations.put("transformationMatrix", glGetUniformLocation(this.programID, "transformationMatrix"));
	}
	

}
