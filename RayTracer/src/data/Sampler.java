package data;

import java.awt.Color;

import org.joml.Vector3f;

import rays.Ray;
import utils.Colour;
import world.Scene;

public class Sampler {

	//Sampler types
	public enum sample{
		NORMAL,
		JITTER,
		SURROUND
	}
	
	//Store which sampler to use, default = sample.JITTER
	public sample type = sample.JITTER;
	//public sample type = sample.NORMAL;
	
	private Scene scene;
	
	//Set scene for use in sampling
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	//Sample a pixel using the sampler type set
	public Color sample(int i, int j) {
		
		switch(type) {
		case NORMAL:
			return sampleNormal(i, j);
			
		case JITTER:
			return sampleJitter(i, j);
			
		case SURROUND:
			return sampleSurround(i, j);
			
		}
		
		return null;
	}
	
	
	
	//Sample using the selected method
	private Color sampleJitter(int i, int j) {
		Vector3f outCol = new Vector3f();
		
		int samples = 50;
		
		for(int k = 0; k < samples; k++) {
			double x = i + Math.random();
			double y = j + Math.random();
			
			outCol.add(samplePoint(x, y));
		}
		
		outCol.div(samples);
		
		return Colour.getCol(outCol);
	}
	
	private Color sampleNormal(int i , int j) {
		return Colour.getCol(samplePoint(i, j));
	}
	
	private Color sampleSurround(int i, int j) {
		return null;
	}
	
	
	
	
	private Vector3f samplePoint(double x, double y) {
		//Create ray and cast it in the world
		Ray ray = scene.getCamera().getRay(x, y);
		return sampleRay(ray, 0);
	}
	
	private Vector3f sampleRay(Ray ray, int depth) {
		
		Vector3f outCol = new Vector3f();
		
		ShadeRec record = scene.castRay(ray);
		
		//check if there was a collision
		Collision collision = record.nearest();
		if(collision == null) {
			return scene.sky;
		}
		
		//Sample collision
		outCol = sampleCollision(collision, depth);
		
		return outCol;
	}
	
	
	
	private int maxRecursions = 5;
	
	private Vector3f sampleCollision(Collision col, int depth) {
		
		if(col.getObject().mirror && depth < maxRecursions) {
			//Reflect ray on object
			Ray newRay = new Ray(col.getInc());
			newRay.reflect(col.getObject().getNormal(col.getPoint()));
			newRay.origin = col.getPoint();
			depth++;
			
			Vector3f reflection = sampleRay(newRay, depth);
			Vector3f colour = col.getObject().getCol();
			return Colour.mixColour(colour.x,colour.y,colour.z, 0.2f, reflection.x, reflection.y, reflection.z);
			
		} else {
			
			//Check for shadows
			if(!scene.sampleLights(col.getPoint())) {
				return new Vector3f(0.1f, 0.1f, 0.1f);
			}
			
			return col.getObject().col;
		}
		
	}
	
}
