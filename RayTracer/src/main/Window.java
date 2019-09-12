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
import lights.DirectionalLight;
import materials.Material;
import renderer.Renderer;
import world.Scene;

public class Window extends JPanel {
	private static final long serialVersionUID = 1L;

	Painter painter = new Painter(this);
	
	Scene scene;
	
	//Lens lens = new Lens(11);
	PinHoleCamera cam = new PinHoleCamera(60);
	ViewingPlane view = new ViewingPlane(
			1, //w
			1, //h
			
			400, //x-res
			400);//y-res
	
	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	Plane ground = new Plane();

	
	
	Plane leftWall = new Plane();
	Plane rightWall = new Plane();
	Plane roof = new Plane();

	//PointLight light = new PointLight();
	DirectionalLight light = new DirectionalLight();
	
	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		cam.setView(view);
		
		cam.setPaint(painter);
		scene.setCamera(cam);
		
		cam.pos = new Vector3f(0, 0, 0);
		cam.setDirection(new Vector3d(0, 0, -1));

		
		
		scene.addObject(ground);
		
		ground.pos = new Vector3f(0, 1, 0);
		ground.mat.col = new Vector3f(0.5f, 0.2f, 0.2f);
		ground.mat.reflection = true;
		
		Material mat = new Material();
		mat.col = new Vector3f(0.5f, 0.9f, 0.2f);
		
		leftWall	.pos = new Vector3f(1, 0, 0);
		rightWall	.pos = new Vector3f(1, 0, 0);
		roof		.pos = new Vector3f(0, -1, 0);
		
		leftWall	.mat = mat;
		rightWall	.mat = mat;
		roof		.mat = mat;
		
		
		//leftWall	.offset = -10;
		rightWall	.offset = 0.000001f;
		//roof	    .offset = 7;
		
		//scene.addObject(leftWall);
		scene.addObject(rightWall);
		//scene.addObject(roof);

		light.dir = new Vector3f(0, -1, 0);
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
