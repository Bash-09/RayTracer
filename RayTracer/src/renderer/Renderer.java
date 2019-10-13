package renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	
	private Camera cam;
	private ViewingPlane view;
	
	private Stack<Integer> cols = new Stack<>();
	
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
		
		pixel = 0;
		
		//Get required objects
		this.cam = scene.getCamera();
		this.view = scene.getCamera().getView();
		//Create image
		img = new BufferedImage(scene.getCamera().getView().w_r, scene.getCamera().getView().h_r, BufferedImage.TYPE_INT_ARGB);

		ArrayList<Integer> ints = new ArrayList<>();
		for(int i = 0; i < view.w_r; i++) {
			ints.add(i);
		}
		boolean shuffle = false;
		if(shuffle) {
			while(!ints.isEmpty()) {
				int i = (int)((float)ints.size()*Math.random());
				cols.push(ints.get(i));
				ints.remove(i);
			}
		} else {
			for(int i = 0; i < ints.size(); i++) {
				cols.push(ints.get(ints.size()-i-1));
			}
		}
		
		compiler = new ImageCompiler(img, cam.getPainter(), this);
		compiler.start();
		
		//Render each line on a different Thread
		while(!cols.isEmpty()) {
			
			pool.execute(this);
			
		}
		
		//Finish up rendering
		pool.shutdown();
		
		//Wait for threads to finish
		try {
			while(!pool.awaitTermination(2, TimeUnit.SECONDS)) {
				System.out.println("Working");
			}
		} catch(Exception e) {}
		compiler.finish();
		
		double finished = System.currentTimeMillis() - start;
		System.out.println("Rendered in: "+finished/1000+" seconds.");
		
		return compiler.img;
	}	
	
	private synchronized int getNextCol() {
		if(cols.empty()) {
			return -1;
		} else {
			return cols.pop();
		}
	}

	//Render a new chunk on the Thread
	@Override
	public void run() {
		int col = getNextCol();
		if(col < 0) return;
		
		Color[] cols = new Color[view.h_r];
		for(int i=0; i < cols.length; i++) {
			cols[i] = sampler.sample(col, i);
		}
		
		Chunk chunk = new Chunk(col, cols);
		
		compiler.addCol(chunk);
		doneCol();
		
		System.gc();
	}
	
	private synchronized void doneCol() {
		pixel+=view.h_r;
	}
	
	long pixel;
	public boolean percentage() {
		int num = view.w_r*view.h_r;
		String percentage = Float.toString((float)pixel/(float)num*100);
		String out = "";
		char[] perc = percentage.toCharArray();
		for(int i = 0; i < perc.length; i++) {
			if(i > 4) break;
			out += perc[i];
		}
		out += "%";
		
		System.out.println("Rendering: "+out);
		
		if(pixel == num) {
			return true;
		}
		return false;
	}
	
}
