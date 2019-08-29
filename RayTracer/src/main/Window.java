package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import canvas.Camera;
import geometry.Sphere;
import world.Scene;

public class Window extends JPanel {

	Painter painter = new Painter(this);
	
	Scene scene;
	Sphere sphere = new Sphere(1);
	Camera cam = new Camera();
	
	public void init() {
		sphere.col = Color.green;
		
		cam.setPaint(painter);
		
		scene.addObject(sphere);
		scene.setCamera(cam);
	}
	
	
	public void render() {
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {	
		
		
		
	}
	
	
	
}
