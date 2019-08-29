package data;

import java.awt.Color;
import java.util.ArrayList;

import org.joml.Vector3f;

import rays.Ray;
import utils.Colour;

public class ShadeRec {

	private ArrayList<Collision> collisions = new ArrayList<>();
	
	public Vector3f getColour() {
		if(collisions.size() == 0) {
			return null;
		}
		
		
		double closestDist;
		int closestInd = 0;
		
		closestDist = collisions.get(0).getDistance();

		for(int i = 0; i < collisions.size(); i++) {
			double temp = collisions.get(i).getDistance();
			
			if(temp < closestDist && temp > 0) {
				closestDist = temp;
				closestInd = i;
			}
		}
		
		if(closestDist < 0) {
			return null;
		}
		
		/*
		Collision closest = collisions.get(closestInd);
		Vector3f col = new Vector3f();
		Ray ray = closest.getInc();
		col.z = (float)Math.cos(ray.origin.z+ray.direction.z*closest.getDistance()*10);
		col.x = (float)Math.sin(ray.origin.x+ray.direction.x*closest.getDistance()*10);
		col.y = (float)Math.sin(ray.origin.z+ray.direction.z*closest.getDistance()*10);


		col = Colour.col(col);
		return new Color(col.x, col.y, col.z);
		*/
		
		return collisions.get(closestInd).getObject().col;
	}
	
	public void addCollision(Collision col) {
		collisions.add(col);
	}
	
}
