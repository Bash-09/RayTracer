package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import camera.Painter;
import camera.PinHoleCamera;
import camera.ViewingPlane;
import data.Shader;
import data.Shader.sample;
import io.FileImporter;
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
	
	
	//Lens lens = new Lens(11);
	Painter painter = new Painter(this);
	ViewingPlane view = new ViewingPlane(
			1, //w
			1, //h
			
			2000, //x-res
			2000);//y-res
	
	Shader shader = new Shader();
	Renderer rend = new Renderer(shader);
	
	Scene scene;
	
	public void init() {
		
		shader.samples = 30;
		shader.type = sample.JITTER;
		shader.maxRecursions = 10;
		
		FileImporter importer = new FileImporter();
		
		try {
			scene = importer.readFile("HallwayOrb.scene", painter);
		} catch (IOException e) {}
		
		scene.getCamera().setView(view);
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
	}
	
	private void animate() {
		
	}

	
}
