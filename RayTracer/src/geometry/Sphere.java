package geometry;

import org.joml.Vector3f;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Sphere extends Prop{
	
	float r;
	
	public Sphere(float r) {
		this.r = r;
	}
	
	public ShadeRec trace(Ray ray, ShadeRec record) {
		
		double rs = r*r;
		//Ray origin minus sphere origin
		Vector3f p1 = new Vector3f(ray.origin.x-pos.x, ray.origin.y-pos.y, ray.origin.z - pos.z);
		//Ray direction squared
		Vector3f va = new Vector3f(ray.direction.x*ray.direction.x, ray.direction.y*ray.direction.y, ray.direction.z*ray.direction.z);
		//p1 * 2 * ray direction
		Vector3f vb = new Vector3f(p1.x*2*ray.direction.x, p1.y*2*ray.direction.y, p1.z*2*ray.direction.z);
		//p1 squared
		Vector3f vc = new Vector3f(p1.x*p1.x, p1.y*p1.y, p1.z*p1.z);

		double a = va.x + va.y + va.z;
		double b = vb.x + vb.y + vb.z;
		double c = vc.x + vc.y + vc.z - rs;
		
		double disc = b*b - 4*a*c;

		if(a == 0 || disc < 0) {
			return record;
		}
		
		record.addCollision(new Collision(this, ray, (-b + Math.sqrt(disc))/2*a));
		record.addCollision(new Collision(this, ray, (-b - Math.sqrt(disc))/2*a));
		
		return record;
	}

}
