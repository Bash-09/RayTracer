package data;

import org.joml.Vector3d;

import geometry.Prop;
import rays.Ray;

public class Collision {

	private Prop collide;
	private Ray incoming;
	private Ray outgoing;
	private double t;
	
	public Collision(Prop obj, Ray incRay, double t) {
		this.collide = obj;
		incoming = incRay;
		this.t = t;
	}
	
	public void setOutgoing(Ray out) {
		this.outgoing = out;
	}
	
	public Ray getInc() {
		return incoming;
	}
	public Ray getOut() {
		return outgoing;
	}
	public Prop getObject() {
		return collide;
	}
	public double getDistance() {
		return t;
	}
	
	public Vector3d getPoint() {
		Vector3d point = new Vector3d(incoming.direction);
		point.mul(t*0.999);
		point.add(incoming.origin);
		return point;
	}
	
}
