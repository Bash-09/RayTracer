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
import data.Shader;
import geometry.Plane;
import geometry.Sphere;
import lights.DirectionalLight;
import lights.PointLight;
import renderer.Renderer;
import world.Scene;

public class Window extends JPanel {

	Painter painter = new Painter(this);
	
	Scene scene;
	
	PinHoleCamera cam = new PinHoleCamera(60);
	//Lens lens = new Lens(11);
	
	//DirectionalLight light = new DirectionalLight();
	PointLight light = new PointLight();
	DirectionalLight lightD = new DirectionalLight();
	DirectionalLight lightD2 = new DirectionalLight();

	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	Plane ground = new Plane();

	
	Sphere[] spheres = new Sphere[16];
	
	
	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		
		cam.setPaint(painter);
		scene.setCamera(cam);
		cam.pos = new Vector3f(25, 20, -25);
		cam.setDirection(new Vector3d(-0.35f, -0.6f, 1));

		//scene.addLight(light);
		scene.addLight(lightD);
		//scene.addLight(lightD2);
		
		
		scene.addObject(ground);
		
		
		light.dir = new Vector3f(16, 10, 16);
		lightD.dir = new Vector3f(1, -1, 1);
		lightD2.dir = new Vector3f(-1, -1, -1);
		
		ground.pos = new Vector3f(0, 1, 0);
		ground.offset = 1;
		ground.mat.col = new Vector3f(0.5f, 0.2f, 0.2f);
		ground.mat.reflection = true;
		
		
		for(int i = 0; i < spheres.length; i++) {
			
			spheres[i] = new Sphere(2.5f);
			spheres[i].pos = new Vector3f(i/4*8, 1, (i%4)*8);
			//spheres[i].mat.reflection = true;
			spheres[i].mat.col = new Vector3f(0.2f, 0.7f, 0.5f);
			spheres[i].mat.reflection = true;
			
			scene.addObject(spheres[i]);
		}
		
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
