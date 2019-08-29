package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import canvas.Camera;
import canvas.ViewingPlane;
import entities.Light;

public class Scene {

	private Camera camera;
	
	private ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Light> lights = new ArrayList<>();
	
	public void addObject(Object obj) {
		objects.add(obj);
	}
	
	public void setCamera(Camera cam) {
		this.camera = cam;
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public BufferedImage render(Graphics g) {
		BufferedImage img = new BufferedImage(camera.getView().w_r, camera.getView().h_r, BufferedImage.TYPE_INT_ARGB);
		
		ViewingPlane view = camera.getView();
		
		float xS = view.w/(float)view.w_r;
		float yS = view.h/(float)view.h_r;
		
		//Loop through every pixel on the view
		for(int i = 0; i < view.w_r; i++) {
			for(int j = 0; j < view.h_r; j++) {
				
				
				
			}
		}
		
		
		return img;
	}
	
}
