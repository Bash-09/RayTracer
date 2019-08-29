package main;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.joml.Vector3f;

import canvas.Camera;
import geometry.Plane;
import geometry.Sphere;
import world.Scene;

public class Window extends JPanel {

	Painter painter = new Painter(this);
	
	Scene scene = new Scene();
	Camera cam = new Camera();
	
	
	Sphere sphere = new Sphere(1);
	Plane plane = new Plane();
	
	
	public void init() {
		sphere.setColour(0, 1, 0);
		
		plane.setColour(0.9f, 0.5f, 0.2f);
		
		plane.pos = new Vector3f(0, 1f, 0.2f);
		plane.offset = 0.3f;
		
		
		cam.setPaint(painter);
		cam.pos = new Vector3f(0, 0, -5);
		
		sphere.pos = new Vector3f(0, 0, 0);
		
		scene.addObject(sphere);
		scene.addObject(plane);
		
		scene.setCamera(cam);
	}
	
	
	public void render() {
		scene.render();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {	
		
		g.drawImage(painter.getImg(), 0, 0, this.getWidth(), this.getHeight(), null);
		
	}
	
	
	
}
