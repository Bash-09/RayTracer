package geometry;

import org.joml.Vector3d;
import org.joml.Vector3f;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Triangle extends Prop{

	Vector3d normal = new Vector3d();
	Vector3d p1, p2, p3;
	Plane plane = new Plane();
	
	public void setPoints(Vector3d p1, Vector3d p2, Vector3d p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		Vector3d bMa = new Vector3d(p2);
		bMa.sub(p1);
		bMa.normalize();
		Vector3d cMa = new Vector3d(p3);
		cMa.sub(p1);
		cMa.normalize();
		
		normal = new Vector3d(bMa);
		normal.cross(cMa);
		normal.normalize();
		
		plane.pos = new Vector3f((float)p1.x, (float)p1.y, (float)p1.z);
		plane.normal = new Vector3d(normal);
		normal.mul(-1);
	}
	
	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {

		
		double a,b,c,d,e,f,g,h,i;
		//Matrix
		a =	p2.x-p1.x;	b = p3.x-p1.x;	c = -ray.direction.x;
		d =	p2.y-p1.y;	e = p3.y-p1.y;	f = -ray.direction.y;
		g =	p2.z-p1.z;	h = p3.z-p1.z;	i = -ray.direction.z;
		
		Vector3d oMa = new Vector3d(ray.origin);
		oMa.sub(p1);
		//Determinant
		double det = a*(e*i - f*h) - b*(d*i - f*g) + c*(d*h - e*g);
		if(det == 0) {
			return record;
		}
		//Inverse matrix operations
		double sum1 = oMa.x*(e*i-f*h) + oMa.y*(h*c-b*i) + oMa.z*(b*f-c*d);
		double sum2 = oMa.x*(g*f-d*i) + oMa.y*(a*i-g*c) + oMa.z*(d*c-a*f);
		double sum3 = oMa.x*(d*h-e*g) + oMa.y*(g*b-a*h) + oMa.z*(a*e-b*d);
		
		double u = sum1/det;
		double v = sum2/det;
		double t = sum3/det;
		
		if(u < 0 || u > 1) {
			return record;
		}
		if(v < 0 || v > 1) {
			return record;
		}
		if(u+v < 0 || u+v > 1) {
			return record;
		}
		
		record.addCollision(new Collision(this, ray, t));
		
		
		
		/*
		Vector3d sideA = new Vector3d(p2);
		sideA.sub(p1);
		Vector3d sideB = new Vector3d(p3);
		sideA.sub(p2);
		Vector3d sideC = new Vector3d(p1);
		sideA.sub(p3);
		
		ShadeRec tempRec = plane.trace(ray, new ShadeRec());
		if(tempRec.nearest() == null) {
			return record;
		}
		
		Vector3d p = tempRec.nearest().getPoint();
		
		Vector3d c0 = new Vector3d(p).sub(p1);
		Vector3d c1 = new Vector3d(p).sub(p2);
		Vector3d c2 = new Vector3d(p).sub(p3);

		sideA.cross(c0);
		sideB.cross(c1);
		sideC.cross(c2);
		
		
		Vector3d v0v1 = new Vector3d(normal);
		
		double dot1 = v0v1.x*sideA.x + v0v1.y*sideA.y + v0v1.z*sideA.z;
		double dot2 = v0v1.x*sideB.x + v0v1.y*sideB.y + v0v1.z*sideB.z;
		double dot3 = v0v1.x*sideC.x + v0v1.y*sideC.y + v0v1.z*sideC.z;

		if(dot1 > 0 && dot2 > 0 && dot3 > 0) {
			record.addCollision(new Collision(this, ray, tempRec.nearest().getDistance()));
		}
		*/
		
		
		return record;
	}

	@Override
	public Vector3d getNormal(Vector3d point) {
		return normal;
	}

}
