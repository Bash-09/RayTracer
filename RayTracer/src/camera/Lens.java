package camera;

import org.joml.Vector2d;
import org.joml.Vector3d;

import rays.Ray;

public class Lens {

	public float focalLength;
	public float radius = 0.05f;
	
	public Lens(float focalLength) {
		this.focalLength = focalLength;
	}
	
	public Ray getRay(Ray ray, Vector3d x, Vector3d y, Vector3d z) {
		
		Ray outRay = new Ray(ray);
		
		double ang = getAngle();
		double dist = getDistance();
		
		double xOff = Math.cos(ang) * dist;
		double yOff = Math.sin(ang) * dist;
		
		//Set origin of ray to somewhere on the lens 
		outRay.origin.x = ray.origin.x + xOff * x.x;
		outRay.origin.y = ray.origin.y + xOff * x.y;
		outRay.origin.z = ray.origin.z + xOff * x.z;
		
		outRay.origin.x = ray.origin.x + yOff * y.x;
		outRay.origin.y = ray.origin.y + yOff * y.y;
		outRay.origin.z = ray.origin.z + yOff * y.z;
		
		
		Vector3d point = new Vector3d(ray.direction);
		
		point.mul(focalLength);
		point.add(ray.origin);
		
		outRay.direction.x = point.x - outRay.origin.x;
		outRay.direction.y = point.y - outRay.origin.y;
		outRay.direction.z = point.z - outRay.origin.z;
		
		outRay.normalize();

		return outRay;
	}
	
	private double getAngle() {
		return Math.random()*2*Math.PI;
	}
	private double getDistance() {
		return Math.random()*radius;
	}
	
}
