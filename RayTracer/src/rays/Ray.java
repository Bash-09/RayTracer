package rays;

import org.joml.Vector3f;

public class Ray {

	public Ray() {}
	public Ray(float x, float y, float z, float dx, float dy, float dz) {
		origin = new Vector3f(x, y, z);
		direction = new Vector3f(dx, dy, dz);
	}
	
	public Vector3f origin = new Vector3f(0, 0, 0);
	public Vector3f direction = new Vector3f(0, 0, 1);
	
	public void normalize() {
		float dist = (float)Math.sqrt(direction.x*direction.x + direction.y*direction.y + direction.z*direction.z);
		direction.x /= dist;
		direction.y /= dist;
		direction.z /= dist;
	}
	
	
	
}
