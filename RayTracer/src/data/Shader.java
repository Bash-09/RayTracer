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
		lights = scene.getLights();
		
		//Which type of rendering it would like
		switch(type) {
		//Render with one sample per pixel
		case NORMAL:
			return sampleNormal(i, j);
			
			//Render with numerous samples per pixel, slightly offset randomly each time
		case JITTER:
			return sampleJitter(i, j);
			
			//Not sure what this was supposed to do
		case SURROUND:
			return sampleSurround(i, j);
		}
		
		return null;
	}
	
	private ArrayList<Light> lights;
	public int samples = 50;
	
	//Default ambiaent light value
	Vector3f ambience = new Vector3f(0.05f, 0.05f, 0.05f);
	
	//Sample using the jittering method
	private Color sampleJitter(int i, int j) {
		Vector3f outCol = new Vector3f();
		
		//Create a new sample object
		Sample sample = new Sample(samples);
		
		//Loop for number of samples
		for(int k = 0; k < samples; k++) {
			//Slightly offset direction of each sample randomly
			double x = i + Math.random();
			double y = j + Math.random();
			
			//Sample that ray
			Vector3f col = samplePoint(x, y, sample);
			if(col == null) {
				continue;
			}
			
			//Add all the colours
			outCol.add(col);
			sample.increment();
		}
		
		//Average colour over all samples
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
		//TODO Fix lens flare sampling for visible light-sources
		out.add(sampleLensFlare(ray, sample));
		
		return out;
	}
	
	//Sample a ray
	private Vector3f sampleRay(Ray ray, int depth, Sample sample) {
		//Vector to store colour
		Vector3f outCol = new Vector3f();
		
		ShadeRec record = scene.castRay(ray); //Cast ray in scene
		Collision collision = record.nearest();
		
		if(collision == null) {		//If there was no collision, return sky colour
			Vector3f out = new Vector3f(scene.sky);
			return out;
		}
		
		//Otherwise
		//Sample collision
		outCol = sampleCollision(collision, depth, sample);
		
		return outCol;
	}
	
	//Max number of recursive raycasts
	private int maxRecursions = 5;
	
	//Sample a collision
	private Vector3f sampleCollision(Collision col, int depth, Sample sample) {
		//Check if material is reflective (will call another raycast for a reflection)
		if(col.getObject().mat.reflection && depth < maxRecursions) {

			Ray newRay = col.getOut();
			depth++; //Another layer deep in recursive raycasts
			
			//Get colours of reflection and the lighting
			Vector3f reflection = sampleRay(newRay, depth, sample);
			Vector3f colour = sampleLight(col, sample);
			
			//Mix the reflection and the colour from the light depending on the reflective factor of the material
			return Colour.mixColour(colour.x,colour.y,colour.z, 1-col.getObject().mat.reflectiveFactor, reflection.x, reflection.y, reflection.z);
			
		} else {
			//Otherwise just return value from a light sampling
			return sampleLight(col, sample);
		}
	}
	
	//
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

		col.mul(coll.getObject().mat.albedo/(float)Math.PI);
		
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
