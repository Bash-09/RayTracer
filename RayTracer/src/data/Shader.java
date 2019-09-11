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
	
	Vector3f ambience = new Vector3f(0.0f, 0.0f, 0.0f);
	
	//Sample using the selected method
	private Color sampleJitter(int i, int j) {
		Vector3f outCol = new Vector3f();
		
		int samples = 5;
		
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
		out.add(sampleLensFlare(ray, sample));
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
	
	private Vector3f sampleLensFlare(Ray ray, Sample samp) {
		Vector3f out = new Vector3f();
		Vector3f specular = new Vector3f();
		
		for(Light i : lights) {
			if(i.visible) {
				Vector3f tempSpec = sampleSpecularLight(ray, i, samp, 100);
				specular.add(tempSpec);
			}
		}
		
		out.add(specular);
		
		out = Colour.col(out);
		return out;
	}
	
	private Vector3f sampleLight(Collision col, Sample samp) {
		Vector3f colour = col.getObject().mat.col;

		Vector3f shadow = sampleShadow(col, samp);
		Vector3f specular;
		
		if(shadow.x == ambience.x && shadow.y == ambience.y && shadow.z == ambience.z) {
			specular = new Vector3f();
		} else {
			specular = sampleSpecular(col, samp).mul(0.5f);
		}
		
		Vector3f outCol = Colour.combine(colour, shadow);
		//TODO combine outCol and diffuse lighting
		outCol.add(specular);
		
		
		return Colour.col(outCol);
	}
	
	private Vector3f calcDiffuse(Light light, Collision coll, Sample sample) {
		Ray sampleRay = light.getLightSampleRay(sample, coll.getPoint());
		
		Vector3d toLight = new Vector3d(sampleRay.direction);
		Vector3d norm = coll.getNormal();
		
		toLight.normalize();
		norm.normalize();

		Vector3f col = new Vector3f(light.getCol(sampleRay));

		col.div((float)Math.PI);
		
		float dot = (float)(toLight.x*norm.x + toLight.y*norm.y + toLight.z*norm.z);
		
		col.mul(dot);
		col.mul(light.intensity);
		
		return col;
	}
	
	private Vector3f sampleShadow(Collision col, Sample sample) {
		Vector3f intensity = new Vector3f(ambience);
		
		//Iterate through all of the lights
		for(Light i : lights) {
			//Get ray that points at light from a certain point
			Ray shadowRay = i.getLightSampleRay(sample, col.getPoint());
			ShadeRec record = scene.castRay(shadowRay);
			
			//If there is a nearest object
			if(record.nearest() != null) {
				//If there is a limited distance to the object
				if(shadowRay.hasLength) {
					//Check if closest object is closer than the light
					if(record.nearest().getDistance() > shadowRay.t) {
						
						intensity.add(calcDiffuse(i, col, sample));
						//intensity.add(i.getCol(shadowRay));
					}
				} 
				
			} 
			//If there is no object, return directional light colour
			else {
				intensity.add(calcDiffuse(i, col, sample));
				//intensity.add(i.getCol(shadowRay));
			}
			
			
		}
		
		return intensity;
	}
	
	private Vector3f sampleSpecular(Collision col, Sample samp) {
		Vector3f out = new Vector3f();
		Vector3f specular = new Vector3f();
		
		//Reflected ray from point
		Ray outgoing = col.getOut();

		for(Light i : lights) {
			specular.add(sampleSpecularLight(outgoing, i, samp, col.getObject().mat.specularFactor));
		}
		
		out.add(specular);
		
		out = Colour.col(out);
		return out;
	}
	
	private Vector3f sampleSpecularLight(Ray outgoing, Light light, Sample samp, float e) {
		
		Vector3d ref = outgoing.direction;
		Vector3d toL = light.getLightSampleRay(samp, outgoing.origin).direction;
		ref.normalize();
		toL.normalize();
		
		double rDotL = ref.x*toL.x + ref.y*toL.y + ref.z*toL.z;
		if(rDotL < 0 || Math.acos(rDotL) > Math.PI) {
			return new Vector3f(0, 0, 0);
		}
		float specular = (float)Math.pow(rDotL/(ref.length()*toL.length()), e);
		
		Vector3f out = new Vector3f(light.col).mul(specular);
		
		
		return out;
	}
		
}
