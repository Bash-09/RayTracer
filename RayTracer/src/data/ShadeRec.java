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
	Scene scene;
	
	public ShadeRec(Scene scene) {
		this.scene = scene;
	}
	
	public Prop getObject() {
		return nearest().getObject();
	}
	
	public Vector3f getColour() {
		Collision closest = nearest();
		
		if(closest == null) {
			return scene.sky;
		}
		Vector3f col = closest.getObject().col;
		
		
		if(hasLight(closest)) {
			return col;
		} else {
			return new Vector3f(0.1f, 0.1f, 0.1f);
		}
		//Vector3f lightCol = getLightValue(closest);
		
		//col = Colour.mixColour(col.x, col.y, col.z, lightCol.length(),lightCol.x, lightCol.y, lightCol.z);
		
		//return col;
		
		
		
		/*
		Vector3f col = new Vector3f();
		Ray ray = closest.getInc();
		col.z = (float)Math.cos(ray.origin.z+ray.direction.z*closest.getDistance()*10);
		col.x = (float)Math.sin(ray.origin.x+ray.direction.x*closest.getDistance()*10);
		col.y = (float)Math.sin(ray.origin.z+ray.direction.z*closest.getDistance()*10);
		

		col = Colour.col(col);
		return col;
		*/
		
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
	
	private boolean hasLight(Collision collision) {
		Ray in = collision.getInc();
		Ray out = new Ray(in);
		
		out.move(collision.getDistance());
		
		return scene.sampleLights(out.origin);
	}
	
	public void addCollision(Collision collision) {
		collisions.add(collision);
	}
	
	public boolean collided() {
		return collided;
	}
	
}
