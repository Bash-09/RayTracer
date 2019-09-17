package geometry;

import org.joml.Vector3d;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Plane extends Prop{

	public Vector3d normal = new Vector3d(0, 1, 0);

	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {

		double nDotRd = normal.x*ray.direction.x + normal.y*ray.direction.y + normal.z*ray.direction.z;
		
		if(nDotRd == 0) {
			return record;
		}
		
		double nDotP = normal.x*pos.x + normal.y*pos.y + normal.z*pos.z;
		double nDotRo = normal.x*ray.origin.x + normal.y*ray.origin.y + normal.z*ray.origin.z;
		
		record.addCollision(new Collision(this, ray, (nDotP - nDotRo)/nDotRd));

		return record;
	}
	
	@Override
	public Vector3d getNormal(Vector3d point) {
		return normal;
	}
	
}
