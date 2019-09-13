package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.joml.Vector3d;
import org.joml.Vector3f;

import camera.Painter;
import camera.PinHoleCamera;
import camera.ViewingPlane;
import data.Shader;
import geometry.Plane;
import geometry.Sphere;
import lights.PointLight;
import materials.Material;
import renderer.Renderer;
import world.Scene;

public class Window extends JPanel {
	private static final long serialVersionUID = 1L;

	/*
	 * Required for rendering
	 * 
	 * Create:
	 * Camera object (Pinhole camera forperspective viewing)
	 * Scene object (add the camera created earlier)
	 * Painter object and add the window to draw to
	 * Shader object
	 * Renderer object (with the shader created earlier)
	 * 
	 * Add painter to camera
	 * Add at least 1 light to the scene
	 * 
	 * Add any objects you wish to the scene
	 * 
	 * Call Renderer.rend() passing it the scene you wish to render
	 * 
	 * 
	 */
	
	
	Painter painter = new Painter(this);
	
	Scene scene;
	
	//Lens lens = new Lens(11);
	PinHoleCamera cam = new PinHoleCamera(60);
	ViewingPlane view = new ViewingPlane(
			16, //w
			9, //h
			
			1920, //x-res
			1080);//y-res
	
	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	Plane ground = new Plane();

	
	
	Plane leftWall = new Plane();
	Plane rightWall = new Plane();
	Plane roof = new Plane();
	Plane farWall = new Plane();
	
	Sphere sphere = new Sphere(1);


	PointLight light = new PointLight();
	//DirectionalLight light = new DirectionalLight();
	
	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		cam.setView(view);
		
		cam.setPaint(painter);
		scene.setCamera(cam);
		
		cam.pos = new Vector3f(0, 3, -4);
		cam.setDirection(new Vector3d(0, 0, 1));

		
		
		scene.addObject(ground);
		
		ground.pos = new Vector3f(0, 0, 0);
		ground.normal = new Vector3d(0, 1, 0);
		ground.mat.col = new Vector3f(0.5f, 0.2f, 0.2f);
		ground.mat.reflection = true;
		
		Material mat = new Material();
		mat.col = new Vector3f(0.5f, 0.9f, 0.2f);
		
		Material sMat = new Material();
		sMat.col = new Vector3f(0.1f, 0.8f, 0.4f);
		sMat.reflection = true;
		sMat.reflectiveFactor = 0.7f;
		sMat.specularFactor = 25;
		//sMat.specularReflection
		
		mat = ground.mat;
		
		leftWall	.normal = new Vector3d(-1, 0, 0);
		rightWall	.normal = new Vector3d(1, 0, 0);
		roof		.normal = new Vector3d(0, -1, 0);
		farWall		.normal = new Vector3d(0, 0, -1);

		leftWall	.pos = new Vector3f(-4, 0, 0); 
		rightWall	.pos = new Vector3f(4, 0, 0); 
		roof		.pos = new Vector3f(0, 10, 0);
		farWall		.pos = new Vector3f(0, 0, 100);

		leftWall	.mat = mat;
		rightWall	.mat = mat;
		farWall		.mat = mat;
		roof		.mat = mat;
		
		sphere.mat = sMat;
		sphere.pos = new Vector3f(0, 1, 7);
		
		scene.addObject(leftWall);
		scene.addObject(rightWall);
		scene.addObject(roof);
		scene.addObject(sphere);

		light.dir = new Vector3f(0, 5, 30);
		scene.addLight(light);
		
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
		
		int frames = 600;
		
		new File("exports/"+filenames).mkdirs();
		
		
		
		for(int i = 0; i < frames; i++) {
			
			if(i < 72) {
				animate();
				continue;
			}
			
			BufferedImage frame = render();
			File output = new File("exports/"+filenames+"/"+filenames+i+".png");
			try {
				ImageIO.write(frame,  "png", output);
			} catch (IOException e) {}
			
			animate();
		}
		
		
	}
	
	private void animate() {
		
		light.dir.z -= 0.1f;
		
	}

	
}
