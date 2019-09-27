package renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Stack;

import camera.Painter;

public class ImageCompiler implements Runnable{

	BufferedImage img;
	Painter paint;
	
	public ImageCompiler(BufferedImage img, Painter paint) {
		this.img = img;
		this.paint = paint;
	}
	
	@Override
	public void run() {
		render();
	}
	
	private boolean running = false;
	Thread looper;
	
	public BufferedImage finish() {
		running = false;
		looper = null;
		
		while(rendering) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return img;
	}
	public void start() {
		running = true;
		looper = new Thread(this);
		looper.start();
	}
	
	private void update() {

		if(cols.empty()) {
			paint.repaint(img);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		int x = cols.pop();
		Color[] cols = colourmap.get(x);
		for(int i = 0; i < cols.length; i++) {
			
			img.setRGB(x, i, cols[i].getRGB());
			
		}

	}
	private HashMap<Integer, Color[]> colourmap = new HashMap<>();
	private Stack<Integer> cols = new Stack<>();
	
	public void addCol(int x, Color[] cols) {
		colourmap.put(x, cols);
		this.cols.push(x);
	}
	
	public boolean rendering = false;
	private void render() {
		rendering = true;
		while(running || !cols.empty()) {
			update();
		}
		rendering = false;
	}
	
}
