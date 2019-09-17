package data;

import java.util.ArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;

import geometry.Prop;
import rays.Ray;
import world.Scene;

public class ShadeRec {

	private ArrayList<Collision> collisions = new ArrayList<>();
	
	private boolean collided = false;

	public Prop getObject() {
		return nearest().getObject();
	}
	
	public Vector3f getColour() {
		Collision closest = nearest();
		
		if(closest == null) {
			return null;
		}
		Vector3f col = closest.getObject().mat.col;
		
		return col;
	}
	
	public Collision nearest() {
		if(collisions.size() == 0) {
			return null;
		}
		
		
		double closestDist = -1;
		int closestInd = -1;
		
		for(int i = 0; i < collisions.size(); i++) {
			double temp = collisions.get(i).getDistance();
			
			if((closestDist < 0 || temp < closestDist) && temp > 0) {
				closestDist = temp;
				closestInd = i;
			}
		}
		
		if(closestInd < 0) {
			return null;
		}
		
		return collisions.get(closestInd);
	}
	
	public void addCollision(Collision collision) {
		collisions.add(collision);
	}
	
	public boolean collided() {
		return collided;
	}
	
}
