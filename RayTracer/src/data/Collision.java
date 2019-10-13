package data;

import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import geometry.Prop;
import geometry.Triangle;
import rays.Ray;

public class Collision {

	private Prop collide;
	private Ray incoming;
	private Ray outgoing;
	private Vector3d normal;
	private Vector2f textureCoords;
	private double t;
	
	public Collision(Prop obj, Ray incRay, double t, Vector3d normal, Vector2f text) {
		this.normal = normal;
		this.collide = obj;
		incoming = incRay;
		this.t = t;
		this.textureCoords = text;
		calcOut();
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
		point.mul(t*0.9999);
		point.add(incoming.origin);
		return point;
	}
	
	private void calcOut() {
		outgoing = new Ray(incoming);
		outgoing.reflect(normal);
		outgoing.origin = new Vector3d(getPoint());
	}
	
	public Vector3d getNormal() {
		return normal;
	}
	
	public Vector3f getCol() {
		if(collide.getClass() == Triangle.class) {
			
			Triangle obj = (Triangle)collide;
			if(obj.getMesh() != null && obj.getMesh().textured) {
				Vector3f col = obj.getMesh().getTexture().getCol(textureCoords.x,textureCoords.y);
				return col;
			}
		}
		
		return collide.mat.col;
	}
	
}
