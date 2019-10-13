package geometry;

import org.joml.Vector3d;

import rays.Ray;

public class BoundingBox{
	
	public Vector3d p1 = new Vector3d();
	public Vector3d p2 = new Vector3d();
	
	public boolean inBounds(Ray ray) {
		double ox = ray.origin.x;
		double oy = ray.origin.y;
		double oz = ray.origin.z;

		double dx = ray.direction.x;
		double dy = ray.direction.y;
		double dz = ray.direction.z;
		
		double tx_min, ty_min, tz_min;
		double tx_max, ty_max, tz_max;
		
		if(dx == 0) {
			dx = 0.0001f;
		}
		double a = 1.0 / dx;
		
		if (a >= 0) {
		tx_min = (p1.x - ox)
		 * a;
		tx_max = (p2.x - ox)
		 * a;
		}
		else {
		tx_min = (p2.x - ox)
		 * a;
		tx_max = (p1.x - ox)
		 * a;
		}
		
		
		
		if(dy == 0) {
			dy = 0.0001f;
		}
		double b = 1.0 / dy;
		
		if (b >= 0) {
		ty_min = (p1.y - oy)
		 * b;
		ty_max = (p2.y - oy)
		 * b;
		}
		else {
		ty_min = (p2.y - oy)
		 * b;
		ty_max = (p1.y - oy)
		 * b;
		}

		
		
		if(dz == 0) {
			dz = 0.0001f;
		}
		double c = 1.0 / dz;
		
		if (c >= 0) {
		tz_min = (p1.y - oz)
		 * c;
		tz_max = (p2.y - oz)
		 * c;
		}
		else {
		tz_min = (p2.y - oz)
		 * c;
		tz_max = (p1.y - oz)
		 * c;
		}
		
		
		
		double t0, t1;
		// find largest entering t value
		if (tx_min > ty_min)
		t0 = tx_min;
		else
		t0 = ty_min;
		if (tz_min > t0)
		t0 = tz_min;
		// find smallest exiting t value
		if (tx_max < ty_max)
		t1 = tx_max;
		else
		t1 = ty_max;
		if (tz_max < t1)
		t1 = tz_max;
		
		return (t0 < t1);
	}

}
