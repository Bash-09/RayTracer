package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.joml.Vector3d;
import org.joml.Vector3f;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import camera.Painter;
import camera.PinHoleCamera;
import camera.ViewingPlane;
import data.Shader;
import geometry.Plane;
import geometry.Sphere;
import geometry.Triangle;
import io.Importer;
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
	PinHoleCamera cam = new PinHoleCamera(90);
	ViewingPlane view = new ViewingPlane(
			1, //w
			1, //h
			
			800, //x-res
			800);//y-res
	
	Shader sampler = new Shader();
	Renderer rend = new Renderer(sampler);
	
	Sphere sphere = new Sphere(1);


	PointLight light = new PointLight();
	//DirectionalLight light = new DirectionalLight();
	
	Triangle tri = new Triangle();
	Plane ground = new Plane();
	
	Material refMat = new Material();
	Sphere leftS = new Sphere(0.5f);
	Sphere rightS = new Sphere(0.5f);

	Material sMat = new Material();
	
	
	public void init() {
		scene = new Scene(cam);
		//cam.setLens(lens);
		cam.setView(view);
		
		cam.setPaint(painter);
		scene.setCamera(cam);
		
		cam.pos = new Vector3f(0, 2, -3);
		cam.setDirection(new Vector3d(0, 0, 1));

		
		
		sphere.mat = sMat;
		sphere.pos = new Vector3f(0, 1, 7);
		
		//scene.addObject(sphere);

		//light.dir = new Vector3f(-0.3f, -1, 3f);
		light.dir = new Vector3f(1, 3, 1);
		light.intensity = 2;
		scene.addLight(light);
		
		Vector3d a = new Vector3d(-2, 1, 3);
		Vector3d b = new Vector3d(2, 1, 3);
		Vector3d c = new Vector3d(-2, 3, 3);
		
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
		
		refMat.col = new Vector3f(0.8549f, 0.6471f, 0.125490f);
		refMat.reflection = true;
		refMat.reflectiveFactor = 0.2f;
		refMat.specularFactor = 50;
		refMat.specularReflection = 0.8f;
		refMat.albedo = 0.25f;
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
	
	public void exportImage(String filename) {
		BufferedImage frame = render();
		File output = new File("exports/images/"+filename+".png");
		try {
			ImageIO.write(frame,  "png", output);
		} catch (IOException e) {}
	}
	
	public void exportFrames(String filenames) {
		
		int frames = 600;
		
		new File("exports/videos/"+filenames).mkdirs();
		
		
		
		for(int i = 0; i < frames; i++) {
			
			if(i < 72) {
				animate();
				continue;
			}
			
			BufferedImage frame = render();
			File output = new File("exports/videos/"+filenames+"/"+filenames+i+".png");
			try {
				ImageIO.write(frame,  "png", output);
			} catch (IOException e) {}
			
			animate();
		}
		
		
	}
	
	@JsonProperty("wrapper") @JsonIgnoreProperties(ignoreUnknown = true)
	public void exportMaterial(String filename) {
		
		/*
		String jsonString = "{\"name\":\""+filename+"\"}";
		
		new File("RayTracer/resources").mkdirs();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		sMat = new Material();
		
			File matFile = new File("resources/objects/"+filename+".json");
			try {
				
				//mapper.writeValue(matFile, sMat);
				
				sMat = mapper.readValue(matFile, Material.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		*/
		
		Importer importer = new Importer();
		try {
			importer.importFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void animate() {
		
		light.dir.z -= 0.1f;
		
	}

	
}
