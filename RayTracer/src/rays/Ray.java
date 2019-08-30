package rays;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Ray {

	public Ray() {}
	public Ray(double x, double y, double z, double dx, double dy, double dz) {
		origin = new Vector3d(x, y, z);
		direction = new Vector3d(dx, dy, dz);
	}
	
	public Vector3d origin = new Vector3d(0, 0, 0);
	public Vector3d direction = new Vector3d(0, 0, 1);
	
	public void normalize() {
		direction.normalize();
	}
	
	public void reflect(Vector3f normal) {
		double dot = direction.x*normal.x + direction.y*normal.y + direction.z*normal.z;
		double square = normal.x*normal.x + normal.y*normal.y + normal.z*normal.z;
		
		direction.x = dot*normal.x/square;
		direction.y = dot*normal.y/square;
		direction.z = dot*normal.z/square;
	}
	
	public void move(double t) {
		origin.add(direction.x*t, direction.y*t, direction.z*t);
		
	}
	
	
}
