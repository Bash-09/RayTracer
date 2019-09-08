package data;

import java.awt.Color;
import java.util.ArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;

import lights.Light;
import rays.Ray;
import samplers.Sample;
import utils.Colour;
import world.Scene;

public class Shader {

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
	
	private ArrayList<Light> lights;
	
	//Sample using the selected method
	private Color sampleJitter(int i, int j) {
		Vector3f outCol = new Vector3f();
		
		int samples = 1;
		
		lights = scene.getLights();
		Sample sample = new Sample(samples);
		
		for(int k = 0; k < samples; k++) {
			double x = i + Math.random();
			double y = j + Math.random();
			
			Vector3f col = samplePoint(x, y, sample);
			if(col == null) {
				continue;
			}
			
			outCol.add(col);
			sample.increment();
		}
		
		
		outCol.div(samples);
		
		return Colour.getCol(outCol);
	}
	
	private Color sampleNormal(int i , int j) {
		return Colour.getCol(samplePoint(i, j, new Sample(1)));
	}
	
	private Color sampleSurround(int i, int j) {
		return null;
	}
	
	
	
	
	private Vector3f samplePoint(double x, double y, Sample sample) {
		//Create ray and cast it in the world
		Ray ray = scene.getCamera().getRay(x, y);
		Vector3f out = sampleRay(ray, 0, sample);
		out.add(sampleLensFlare(ray));
		return out;
	}
	
	private Vector3f sampleRay(Ray ray, int depth, Sample sample) {
		
		Vector3f outCol = new Vector3f();
		
		ShadeRec record = scene.castRay(ray);
		
		//check if there was a collision
		Collision collision = record.nearest();
		
		if(collision == null) {
			Vector3f out = new Vector3f(scene.sky);
			return out;
		}
		
		//Sample collision
		outCol = sampleCollision(collision, depth, sample);
		
		return outCol;
	}
	
	
	private int maxRecursions = 5;
	
	private Vector3f sampleCollision(Collision col, int depth, Sample sample) {
		
		if(col.getObject().mat.reflection && depth < maxRecursions) {
			//Reflect ray on object
			Ray newRay = new Ray(col.getInc());
			newRay.reflect(col.getObject().getNormal(col.getPoint()));
			newRay.origin = col.getPoint();
			depth++;
			
			Vector3f reflection = sampleRay(newRay, depth, sample);
			
			Vector3f colour = sampleLight(col, sample);
			
			return Colour.mixColour(colour.x,colour.y,colour.z, 1-col.getObject().mat.reflectiveFactor, reflection.x, reflection.y, reflection.z);
			
		} else {
			return sampleLight(col, sample);
		}
		
	}
	
	private Vector3f sampleLensFlare(Ray ray) {
		Vector3f out = new Vector3f();
		Vector3f specular = new Vector3f();
		
		for(Light i : lights) {
			if(i.visible) {
				specular.add(sampleSpecularLight(ray, i, 100));
			}
		}
		
		out.add(specular);
		
		out = Colour.col(out);
		return out;
	}
	
	private Vector3f sampleLight(Collision col, Sample samp) {
		Vector3f colour = col.getObject().mat.col;

		Vector3f shadow = sampleShadow(col.getPoint(), samp);
		Vector3f specular = sampleSpecular(col).mul(0.5f);
		//TODO Add diffuse lighting
		
		Vector3f outCol = Colour.combine(colour, shadow);
		//TODO combine outCol and diffuse lighting
		outCol.add(specular);
		
		
		return Colour.col(outCol);
	}
	
	private Vector3f sampleShadow(Vector3d point, Sample sample) {
		Vector3f intensity = new Vector3f(0.05f, 0.05f, 0.05f);
		
		//Iterate through all of the lights
		for(Light i : lights) {
			//Get ray that points at light from a certain point
			Ray shadowRay = i.getLightSampleRay(sample, point);
			ShadeRec record = scene.castRay(shadowRay);
			
			
			if(record.nearest() != null) {
				if(shadowRay.hasLength) {
					if(record.nearest().getDistance() > shadowRay.t) {
						intensity.add(i.col);
					}
				} 
			} else {
				intensity.add(i.col);
			}
			
			
		}
		
		return intensity;
	}
	
	private Vector3f sampleSpecular(Collision col) {
		Vector3f out = new Vector3f();
		Vector3f specular = new Vector3f();
		
		//Reflected ray from point
		Ray outgoing = col.getOut();

		for(Light i : lights) {
			specular.add(sampleSpecularLight(outgoing, i, col.getObject().mat.specularFactor));
		}
		
		out.add(specular);
		
		out = Colour.col(out);
		return out;
	}
	
	private Vector3f sampleSpecularLight(Ray outgoing, Light light, float e) {
		
		Vector3d ref = outgoing.direction;
		Vector3d toL = new Vector3d(light.dir.x-outgoing.origin.x, light.dir.y-outgoing.origin.y, light.dir.z-outgoing.origin.z);
		ref.normalize();
		toL.normalize();
		
		double rDotL = ref.x*toL.x + ref.y*toL.y + ref.z*toL.z;
		if(rDotL < 0) {
			return new Vector3f(0, 0, 0);
		}
		//float specular = (float)Math.pow(rDotL/(ref.length()*toL.length()), specularConstant);
		float specular = (float)Math.pow(rDotL, e);
		
		//System.out.println(specular);
		return new Vector3f(light.col).mul(specular);
	}
		
}
