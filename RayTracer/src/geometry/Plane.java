package geometry;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Plane extends Prop{

	public float offset = 0;

	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {
		
		double d = offset;
		
		double a = pos.x;
		double b = pos.y;
		double c = pos.z;
		
		double sum1 = -(d + a*ray.origin.x + b*ray.origin.y + c*ray.origin.z);
		double sum2 = a*ray.direction.x + b*ray.direction.y + c*ray.direction.z;
		
		if(sum2 == 0) {
			record.addCollision(new Collision(this, ray, 0));
		} else {
			record.addCollision(new Collision(this, ray, sum1/sum2));
		}
		
		return record;
	}
	
}
