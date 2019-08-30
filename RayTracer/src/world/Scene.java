package world;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;

import canvas.Camera;
import canvas.ViewingPlane;
import data.ShadeRec;
import entities.Light;
import geometry.Prop;
import geometry.Sphere;
import rays.Ray;
import utils.Colour;

public class Scene {

	private Camera camera;
	
	private ArrayList<Prop> objects = new ArrayList<>();
	private ArrayList<Light> lights = new ArrayList<>();
	
	public Vector3f sky = new Vector3f(0.6f, 0.6f, 0.9f);
	
	public void addObject(Prop obj) {
		objects.add(obj);
	}
	
	public void setCamera(Camera cam) {
		this.camera = cam;
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public BufferedImage render() {
		BufferedImage img = new BufferedImage(camera.getView().w_r, camera.getView().h_r, BufferedImage.TYPE_INT_ARGB);
		
		ViewingPlane view = camera.getView();
		
		boolean antiAliasing = false;
		boolean jittering = true;
		
		//Loop through every pixel on the view
		for(int i = 0; i < view.w_r; i++) {
			for(int j = 0; j < view.h_r; j++) {
				
				Vector3f col;
				
				if(!antiAliasing) {
					//Create and cast ray
					Ray ray = getRay(view, i ,j);
					ShadeRec record = castRay(ray);
					//Get colour from ray
					col = record.getColour();
					if(col == null) {
						col = sky;
					}
					record = null;
				} else {
				
					if(!jittering) {
						col = new Vector3f(0);
						//Antialiasing
						for(int k = i-1; k<i+2; k++) {
							for(int h = j-1; h < j+2; h++) {
								ShadeRec record = castRay(getRay(view, k, h));
								Vector3f recCol = record.getColour();
								if(recCol == null) {
									recCol = sky;
								}
								col.add(recCol);
								record = null;
							}
						}
						col.x /= 9;
						col.y /= 9;
						col.z /= 9;
					} else {
						
						col = new Vector3f(0);
						int samples = 100;
						
						for(int g = 0; g < samples; g++) {
							ShadeRec record = castRay(getJitteredRay(view, i, j));
							Vector3f recCol = record.getColour();
							if(recCol == null) {
								recCol = sky;
							}
							col.add(recCol);
							record = null;
						}
						col.x /= samples;
						col.y /= samples;
						col.z /= samples;
					}
				}
				
				
				//Put colour on image
				img.setRGB(i, j, Colour.getCol(col).getRGB());
				
				camera.repaint(img);
			}
			//System.gc();
		}
		
		System.gc();
		
		return img;
	}
	
	public ShadeRec castRay(Ray ray) {
		ShadeRec record = new ShadeRec(this);
		return castRay(ray, record);
	}
	
	public ShadeRec castRay(Ray ray, ShadeRec record) {
		
		for(Prop prop : objects) {
			record = prop.trace(ray, record);
		}
		
		return record;
	}
	
	private Ray getRay(ViewingPlane view, int i, int j) {
		return camera.getRay(i, j);
	}
	
	public boolean sampleLights(Vector3d point) {
		ShadeRec record = new ShadeRec(this);
		
		for(int i = 0; i < lights.size(); i++) {
			Sphere light = (Sphere)lights.get(i);
			Ray ray = new Ray(point.x, point.y, point.z, light.pos.x-point.x, light.pos.y-point.y, light.pos.z-point.z);
			ray.normalize();
			light.trace(ray, record);
			castRay(ray, record);
			
			if(record.nearest() != null) {
				if(record.nearest().getObject() == light) {
					record = null;
					ray = null;
					return true;
				}
			}
		}
 		
		return false;
	}
	
	
	private Ray getJitteredRay(ViewingPlane view, int i, int j) {		
		//Static viewPlane, can only move camera, not turn
		float x = camera.pos.x - view.w/2 + i*view.xs + view.xs*((float)Math.random());
		float y = camera.pos.y - view.h/2 + j*view.ys + view.ys*((float)Math.random());
		float z = camera.pos.z;
		
		return new Ray(x, y, z, 0, 0, 1);
	}
	
	
}
