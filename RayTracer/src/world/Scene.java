package world;

import java.util.ArrayList;

import org.joml.Vector3d;
import org.joml.Vector3f;

import camera.Camera;
import data.Shader;
import data.ShadeRec;
import geometry.Prop;
import geometry.Sphere;
import lights.Light;
import rays.Ray;

public class Scene {
	
	private Camera camera;
		
	public Scene(Camera cam) {
		camera = cam;
	}
	
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
	
	public Camera getCamera() {
		return camera;
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
	
	
	
	////****************************SOON TO BE DEPRECATED
	
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

	
}
