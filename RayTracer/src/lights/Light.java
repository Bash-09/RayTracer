package lights;

import org.joml.Vector3d;
import org.joml.Vector3f;

import geometry.Sphere;
import rays.Ray;
import samplers.Sample;

public abstract class Light {

	public Vector3f dir = new Vector3f(0, -1, 0);
	public Vector3f col = new Vector3f(1, 1, 1);
	
	public abstract Ray getLightSampleRay(Sample samp, Vector3d point);
	
	public boolean visible = false;
	
	public abstract Vector3f getCol(Ray toLight);
	
}
