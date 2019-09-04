package renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;
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
	
	private Scene scene;
	private Camera cam;
	private ViewingPlane view;
	
	private Stack<Integer> stack = new Stack<>();
	
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
		this.scene = scene;
		this.cam = scene.getCamera();
		this.view = scene.getCamera().getView();
		//Create image
		img = new BufferedImage(scene.getCamera().getView().w_r, scene.getCamera().getView().h_r, BufferedImage.TYPE_INT_ARGB);

		//Push lines required to be rendered to the stack
		for(int i = view.w_r-1; i >= 0; i--) {
			stack.push(i);
		}
		
		compiler = new ImageCompiler(img, cam.getPainter());
		compiler.start();
		
		//Render each line on a different Thread
		while(!stack.empty()) {
			
			pool.execute(this);
			
		}
		
		//Finish up rendering
		pool.shutdown();
		//Wait for threads to finish
		try {
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
			    pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compiler.finish();
		
		double finished = System.currentTimeMillis() - start;
		System.out.println(finished);
		
		return compiler.img;
	}
	
	
	
	//Push a pixel colour to the image
	private void pushChunk(int i, Color[] cols) {
		Chunk chunk = new Chunk(i, cols);
		compiler.pushChunk(chunk);
	}
	
	//Render a whole line of pixels
	private void renderChunk(int i) {
		Color[] chunk = new Color[view.h_r];
		
		for(int j = 0; j < view.h_r; j++) {
			chunk[j] = sampler.sample(i, j);
		}
		pushChunk(i, chunk);
	}
	
	//Get next line requiring rendering
	private synchronized int getNext() {
		if(stack.empty()) {
			return -1;
		}
		return stack.pop();
	}

	//Render a new chunk on the Thread
	@Override
	public void run() {
		
		int next = getNext();
		if(next < 0) {
			return;
		}
		renderChunk(next);
		
	}
	
}
