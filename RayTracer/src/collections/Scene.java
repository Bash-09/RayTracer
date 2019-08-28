package collections;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import canvas.Camera;
import objects.Light;

public class Scene {

	Camera camera;
	
	ArrayList<Object> objects = new ArrayList<Object>();
	ArrayList<Light> lights = new ArrayList<>();
	
	
	
	BufferedImage render(Graphics g) {
		BufferedImage img = new BufferedImage(camera.getPlane().getRes().x, camera.getPlane().getRes().y, BufferedImage.TYPE_INT_ARGB);
		
		
		
		return img;
	}
	
}
