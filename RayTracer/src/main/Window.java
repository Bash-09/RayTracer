package main;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.joml.Vector3f;

import canvas.Camera;
import canvas.PinHoleCamera;
import entities.Light;
import geometry.Plane;
import geometry.Sphere;
import world.Scene;

public class Window extends JPanel {

	Painter painter = new Painter(this);
	
	Scene scene = new Scene();
	PinHoleCamera cam = new PinHoleCamera(60);
	
	
	Sphere sphere = new Sphere(1);
	Sphere sphere2 = new Sphere(0.7f);
	Plane plane = new Plane();
	Light light = new Light();
	
	
	public void init() {
		sphere.setColour(0, 1, 0);
		sphere2.setColour(0.5f, 0.5f, 1);
		
		plane.setColour(0.9f, 0.5f, 0.2f);
		
		plane.pos = new Vector3f(0, 1, 0);
		plane.offset = 5f;
		
		
		cam.setPaint(painter);
		cam.pos = new Vector3f(0, 0, -5);
		
		sphere.pos = new Vector3f(0, 0, 5);
		sphere2.pos = new Vector3f(-0.7f, 0.4f, 5);
		
		scene.addObject(sphere);
		scene.addObject(plane);
		scene.addObject(sphere2);
		
		light.pos = new Vector3f(0, 10, -2);
		
		scene.addLight(light);
		
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
