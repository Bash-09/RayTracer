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
	
	private Stack<Pixel> stack = new Stack<>();
	
	public void pushPixel(Pixel pix) {
		stack.push(pix);
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
		
		Pixel pix = stack.pop();
		img.setRGB(pix.getPos().x, pix.getPos().y, pix.getCol().getRGB());
		
		paint.repaint(img);
	}
	
	public boolean rendering = false;
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
