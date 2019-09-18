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
import geometry.Triangle;
import lights.DirectionalLight;
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
	PinHoleCamera cam = new PinHoleCamera(90);
	ViewingPlane view = new ViewingPlane(
			1, //w
			1, //h
			
			600, //x-res
			600);//y-res
	
	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	Sphere sphere = new Sphere(1);


	//PointLight light = new PointLight();
	DirectionalLight light = new DirectionalLight();
	
	Triangle tri = new Triangle();
	Plane ground = new Plane();
	
	Material refMat = new Material();
	Sphere leftS = new Sphere(0.5f);
	Sphere rightS = new Sphere(0.5f);

	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		cam.setView(view);
		
		cam.setPaint(painter);
		scene.setCamera(cam);
		
		cam.pos = new Vector3f(0, 2, -3);
		cam.setDirection(new Vector3d(0, 0, 1));

		Material sMat = new Material();
		sMat.col = new Vector3f(0.1f, 0.8f, 0.4f);
		//sMat.reflection = true;
		sMat.reflectiveFactor = 0.7f;
		sMat.specularFactor = 10;
		//sMat.specularReflection
		
		sphere.mat = sMat;
		sphere.pos = new Vector3f(0, 1, 7);
		
		//scene.addObject(sphere);

		light.dir = new Vector3f(-0.3f, -1, 3f);
		light.intensity = 10;
		scene.addLight(light);
		
		/*
		Vector3d a = new Vector3d(0, 3f, 0);
		Vector3d b = new Vector3d(-1, 1f, 1);
		Vector3d c = new Vector3d(1, 1f, -1);
		*/
		
		Vector3d a = new Vector3d(-2, 1, 1);
		Vector3d b = new Vector3d(2, 1, 1);
		Vector3d c = new Vector3d(0, 3, 0);
		
		tri.setPoints(c, a, b);
		tri.mat = sMat;
		scene.addObject(tri);

		
		ground.pos = new Vector3f(0, 0, 0);
		ground.normal = new Vector3d(0, 1, 0);
		ground.mat.reflection = true;
		ground.mat.reflectiveFactor = 0.2f;
		ground.mat.col = new Vector3f(0.8f, 0.6f, 0.4f);
		scene.addObject(ground);
		
		
		
		leftS.pos = new Vector3f(-2, 1, 0);
		rightS.pos = new Vector3f(2, 1, 0);
		
		leftS.mat = refMat;
		rightS.mat = refMat;

		scene.addObject(leftS);
		scene.addObject(rightS);
		
		refMat.col = new Vector3f(0.7f, 0.2f, 0);
		refMat.reflection = true;
		refMat.reflectiveFactor = 0.5f;
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
