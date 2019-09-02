package renderer;

import java.awt.image.BufferedImage;
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
	
	private Stack<Chunk> stack = new Stack<>();
	
	public void pushChunk(Chunk chunk) {
		stack.push(chunk);
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
		if(stack.empty()) {
			return;
		}
		
		Chunk chunk = stack.pop();
		for(int j = 0; j < chunk.chunk.length; j++) {
			img.setRGB(chunk.getInd(), j, chunk.chunk[j].getRGB());
		}
		
		paint.repaint(img);
	}
	
	boolean rendering = false;
	private void render() {
		rendering = true;
		while(running || !stack.empty()) {
			update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rendering = false;
	}
	
}
