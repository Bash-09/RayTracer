package data;

import java.awt.Color;

import rays.Ray;

public class Collision {

	private Object collide;
	private Ray incoming;
	private Ray outgoing;
	
	public Collision(Object obj, Ray incRay) {
		this.collide = obj;
		incoming = incRay;
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
	public Object getObject() {
		return collide;
	}
	
	public Color getCol() {
		return collide.col;
	}
	
}
