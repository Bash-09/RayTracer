package geometry;

import org.joml.Vector3d;
import org.joml.Vector3f;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Sphere extends Prop {
	
	protected float r;
	
	public Sphere(float r) {
		this.r = r;
	}
	public Sphere() {
		r = 1;
	}
	
	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {
		
		double rs = r*r;
		//Ray origin minus sphere origin
		Vector3d p1 = new Vector3d(ray.origin.x - pos.x, ray.origin.y - pos.y, ray.origin.z - pos.z);
		//Ray direction squared
		Vector3d va = new Vector3d(ray.direction.x*ray.direction.x, ray.direction.y*ray.direction.y, ray.direction.z*ray.direction.z);
		//p1 * 2 * ray direction
		Vector3d vb = new Vector3d(p1.x*2*ray.direction.x, p1.y*2*ray.direction.y, p1.z*2*ray.direction.z);
		//p1 squared
		Vector3d vc = new Vector3d(p1.x*p1.x, p1.y*p1.y, p1.z*p1.z);

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

	public Vector3d getNormal(Vector3d point) {
		Vector3d normal = new Vector3d();
		
		normal.x = point.x - pos.x;
		normal.y = point.y - pos.y;
		normal.z = point.z - pos.z;
		normal.normalize();
		
		return normal;
	}
	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");
			
			switch(com[0]) {
			case "mat":
				materialName = com[1];
				break;
			case "pos":
				String[] vals = com[1].split(":");
				pos.x = Float.parseFloat(vals[0]);
				pos.y = Float.parseFloat(vals[1]);
				pos.z = Float.parseFloat(vals[2]);
				break;
			case "radius":
				r = Float.parseFloat(com[1]);
				break;
			}
		}
	}
	
}
