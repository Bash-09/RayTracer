package renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Stack;

public class ImageCompiler implements Runnable{

	BufferedImage img;
	Painter paint;
	Renderer rend;
	
	public ImageCompiler(BufferedImage img, Painter paint, Renderer rend) {
		this.img = img;
		this.paint = paint;
		this.rend = rend;
	}
	
	@Override
	public void run() {
		render();
	}
	
	Thread looper;
	
	public BufferedImage finish() {
		rendering = false;
		
		while(working) {
			System.out.println("Building Image...");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	public void start() {
		looper = new Thread(this);
		looper.start();
	}
	
	private void update() {

		if(cols.empty()) {
			paint.repaint(img);
			
			//rend.percentage();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		Chunk chunk = cols.pop();
		Color[] cols = chunk.cols;
		int x = chunk.x;
		for(int i = 0; i < cols.length; i++) {
			
			img.setRGB(x, i, cols[i].getRGB());
			
		}

	}
	private Stack<Chunk> cols;
	
	public synchronized void addCol(Chunk chunk) {
		cols.push(chunk);
	}
	
	private boolean rendering = false;
	private boolean working = false;
	
	private void render() {
		cols = new Stack<>();
		
		rendering = true;
		working = true;
		while(rendering || !cols.isEmpty()) {
			update();
		}
		working = false;
	}
	
}
