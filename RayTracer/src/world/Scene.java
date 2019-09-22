package world;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector3f;

import camera.Camera;
import data.ShadeRec;
import geometry.Prop;
import io.Settings;
import io.TextParser;
import lights.Light;
import materials.Material;
import rays.Ray;

public class Scene implements Settings{
	
	private String activeCamera;
	public String name = "";
		
	public Scene() {}
	
	public HashMap<String, Material> materials = new HashMap<>();
	public HashMap<String, Prop> objects = new HashMap<>();
	public HashMap<String, Light> lights = new HashMap<>();
	public HashMap<String, Camera> cameras = new HashMap<>();

	public Vector3f sky = new Vector3f(0.1f, 0.1f, 0.15f);
	
	public void addObject(Prop obj) {
		objects.put(obj.name, obj);
	}
	
	public void addCamera(Camera cam) {
		cameras.put(cam.name, cam);
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public Camera getCamera() {
		return camera;
	}
		
	
	public ShadeRec castRay(Ray ray) {
		ShadeRec record = new ShadeRec();
		return castRay(ray, record);
	}
	
	public ShadeRec castRay(Ray ray, ShadeRec record) {
		
		for(Prop prop : objects) {
			record = prop.trace(ray, record);
		}
		
		return record;
	}
	
	public ArrayList<Light> getLights() {
		return lights;
	}
	
	@Override
	public void setup(String[] source) {
		for(int i = 0; i < source.length; i++) {
			
			String[] settings = source[i].split("=");
			
			switch(settings[0]) {
			case "name":
				name = settings[1];
				break;
			case "sky":
				float[] nums = TextParser.parseVector(settings[1]);
				sky = new Vector3f(nums[0], nums[1], nums[2]);
			}
			
		}
	}

	
}
