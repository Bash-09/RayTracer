package io;

import java.util.HashMap;

import camera.PinHoleCamera;
import geometry.Prop;
import materials.Material;
import world.Scene;

public class SceneBuilder {

	private HashMap<String, Material> materials = new HashMap<>();
	
	private Scene scene;
	
	public void addObject(Prop prop, String material) {
		if(material != null) {
			Material mat = materials.get(material);
			if(mat != null) {
				prop.mat = mat;
			}
		}
		
		scene.addObject(prop);
		
	}
	
	public SceneBuilder() {
		scene = new Scene(new PinHoleCamera(90));
	}
	
	
}
