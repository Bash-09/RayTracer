package renderer;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import camera.Camera;
import camera.ViewingPlane;
import data.Shader;
import world.Scene;

public class Renderer implements Runnable{

	Thread[] threads;
	
	private BufferedImage img;
	private Shader sampler;
	
	private Camera cam;
	private ViewingPlane view;
	
	private ArrayList<Pixel> pixels = new ArrayList<>();
	
	private static final int MAX_THREADS = 2000;
	private ExecutorService pool;
	
	private ImageCompiler compiler;
	
	public Renderer(Shader sampler) {
		this.sampler = sampler;
	}

	public BufferedImage render(Scene scene) {
		double start = System.currentTimeMillis();
		
		pool = Executors.newFixedThreadPool(MAX_THREADS);
		
		sampler.setScene(scene);
		
		//Get required objects
		this.cam = scene.getCamera();
		this.view = scene.getCamera().getView();
		//Create image
		img = new BufferedImage(scene.getCamera().getView().w_r, scene.getCamera().getView().h_r, BufferedImage.TYPE_INT_ARGB);


		for(int i = 0; i < view.w_r; i++) {
			for(int j = 0; j < view.h_r; j++) {
				pixels.add(new Pixel(i, j));
			}
		}
		
		compiler = new ImageCompiler(img, cam.getPainter());
		compiler.start();
		
		//Render each line on a different Thread
		while(!pixels.isEmpty()) {
			
			pool.execute(this);
			
		}
		
		//Finish up rendering
		pool.shutdown();
		
		//Wait for threads to finish
		try {
			while(!pool.awaitTermination(10, TimeUnit.SECONDS)) {
				System.out.println("Rendering");
			}
		} catch(Exception e) {}
		compiler.finish();
		
		double finished = System.currentTimeMillis() - start;
		System.out.println(finished);
		
		return compiler.img;
	}
	
	
	
	
	//Push a pixel colour to the image
	private void renderPixel(Pixel pix) {
		Point pos = pix.getPos();
		pix.setColour(sampler.sample(pos.x, pos.y));
		
		compiler.pushPixel(pix);
	}
	
	
	
	
	//Get next line requiring rendering
	private synchronized Pixel getNext() {
		if(pixels.isEmpty()) {
			return null;
		}
		
		int index = (int)(Math.random()*pixels.size());
		Pixel pix = pixels.get(index);
		pixels.remove(index);
		return pix;
	}

	//Render a new chunk on the Thread
	@Override
	public void run() {
		for(int i = 0; i < 100; i++) {
			Pixel pix = getNext();
			if(pix == null) {
				return;
			}
			
			renderPixel(pix);
		}
	}
	
}
