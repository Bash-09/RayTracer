package materials;

import org.joml.Vector3f;

public class Material {

	public float specularFactor = 15;
	public float specularReflection = 1;
	
	public Vector3f col = new Vector3f(1, 1, 1);
	
	public boolean reflection = false;
	public float reflectiveFactor = 0.2f;
	
}
