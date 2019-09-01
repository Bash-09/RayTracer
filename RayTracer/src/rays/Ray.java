package rays;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Ray {

	public Ray() {}
	
	public Ray(double x, double y, double z, double dx, double dy, double dz) {
		init(x, y, z, dx, dy, dz);
	}
	public Ray(Ray ray) {
		init(ray.origin.x, ray.origin.y, ray.origin.z, ray.direction.x, ray.direction.y, ray.direction.z);
	}
	
	private void init(double x, double y, double z, double dx, double dy, double dz) {
		origin = new Vector3d(x, y, z);
		direction = new Vector3d(dx, dy, dz);
	}
	
	public Vector3d origin = new Vector3d(0, 0, 0);
	public Vector3d direction = new Vector3d(0, 0, 1);
	
	public void normalize() {
		direction.normalize();
	}
	
	public void reflect(Vector3d normal) {
		double dot = direction.x*normal.x + direction.y*normal.y + direction.z*normal.z;
		double square = normal.x*normal.x + normal.y*normal.y + normal.z*normal.z;
		
		Vector3d projection = new Vector3d();
		projection.x = dot*-normal.x/square;
		projection.y = dot*-normal.y/square;
		projection.z = dot*-normal.z/square;
		
		direction.add(projection);
		direction.add(projection);
	}
	
	public void move(double d) {
		double t = d*0.9999;
		origin.add(direction.x*t, direction.y*t, direction.z*t);
	}
	
	
}
