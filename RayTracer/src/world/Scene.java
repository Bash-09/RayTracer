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
	
	public String activeCamera;
	public String name = "";
		
	public Scene() {}
	
	public HashMap<String, Material> materials = new HashMap<>();
	public HashMap<String, Prop> objects = new HashMap<>();
	public HashMap<String, Light> lights = new HashMap<>();
	public HashMap<String, Camera> cameras = new HashMap<>();
	
	private ArrayList<Light> lightsList = new ArrayList<Light>();

	public Vector3f sky = new Vector3f(0.1f, 0.1f, 0.15f);
	public Vector3f ambience = new Vector3f();
	
	public void addObject(Prop obj) {
		objects.put(obj.name, obj);
	}
	
	public void addCamera(Camera cam) {
		cameras.put(cam.name, cam);
	}
	
	public void addLight(Light light) {
		lights.put(light.name, light);
		lightsList.add(light);
	}
	
	public Camera getCamera() {
		return cameras.get(activeCamera);
	}		
	
	public ShadeRec castRay(Ray ray) {
		ShadeRec record = new ShadeRec();
		return castRay(ray, record);
	}
	
	public ShadeRec castRay(Ray ray, ShadeRec record) {

		for(String key : objects.keySet()) {
			record = objects.get(key).trace(ray, record);
		}
		
		return record;
	}
	
	public ArrayList<Light> getLights() {
		return lightsList;
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
				break;
			case "active":
				activeCamera = settings[1];
				break;
			case "ambience":
				float[] nums2 = TextParser.parseVector(settings[1]);
				ambience = new Vector3f(nums2[0], nums2[1], nums2[2]);
			}
			
		}
	}

	
}
