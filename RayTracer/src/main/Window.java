package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.joml.Vector3d;
import org.joml.Vector3f;

import camera.Lens;
import camera.Painter;
import camera.PinHoleCamera;
import data.Shader;
import geometry.Plane;
import geometry.Sphere;
import lights.Light;
import renderer.Renderer;
import world.Scene;

public class Window extends JPanel {

	Painter painter = new Painter(this);
	
	Scene scene;
	
	PinHoleCamera cam = new PinHoleCamera(60);
	//Lens lens = new Lens(6);
	
	Sphere sphere = new Sphere(1);
	Sphere sphere2 = new Sphere(0.5f);
	Plane plane = new Plane();
	Light light = new Light();
	
	Sphere sphere3 = new Sphere(1);
	Sphere sphere4 = new Sphere(1);
	
	Plane mirror = new Plane();
	
	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	
	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		
		sphere.setColour(0, 0.3f, 0);
		sphere2.setColour(0.5f, 0.5f, 1);
		
		sphere3.setColour(1, 1, 1);
		sphere4.setColour(1, 1, 1);
		
		plane.setColour(0.9f, 0.5f, 0.2f);
		
		plane.pos = new Vector3f(0, 1, 0);
		plane.offset = 2f;
		
		
		cam.setPaint(painter);
		cam.pos = new Vector3f(0, 5, -1);
		cam.setDirection(new Vector3d(0, -0.5f, 1));
		
		sphere.pos = new Vector3f(0, 2, 5);
		sphere2.pos = new Vector3f(-0.7f, 2f, 3);
		
		sphere3.pos = new Vector3f(-4, 1, 10);
		sphere4.pos = new Vector3f(4, 1, 7);
		scene.addObject(sphere3);
		scene.addObject(sphere4);
		
		//sphere.mirror = true;
		//sphere2.mirror = true;
		//sphere3.mirror = true;
		//sphere4.mirror = true;

		scene.addObject(sphere);
		scene.addObject(plane);
		scene.addObject(sphere2);
		scene.addObject(mirror);
		
		light.pos = new Vector3f(0, 10, 5);
		
		scene.addLight(light);
		
		scene.setCamera(cam);
		
		mirror.setColour(new Vector3f(1, 1, 1));
		mirror.pos = new Vector3f(0.6f, 0, 1);
		mirror.mirror = true;
		mirror.offset = -10;
	}
	
	
	public BufferedImage render() {
		return rend.render(scene);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {	
		if(!Tracer_App.headless) {
			g.drawImage(painter.getImg(), 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
	
	
	public void exportFrames(String filenames) {
		
		int frames = 1000;
		
		new File("exports/"+filenames).mkdirs();
		
		
		
		for(int i = 0; i < frames; i++) {
			
			BufferedImage frame = render();
			File output = new File("exports/"+filenames+"/"+filenames+i+".png");
			try {
				ImageIO.write(frame,  "png", output);
			} catch (IOException e) {}
			
			animate();
		}
		
		
	}
	
	private void animate() {
		
		//lens.focalLength += 0.02f;
		
	}

	
}
