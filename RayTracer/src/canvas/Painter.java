package canvas;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.Tracer_App;

public class Painter {

	private JPanel pane;
	
	private BufferedImage img;
	
	public Painter(JPanel pane) {
		this.pane = pane;
	}
	
	public void repaint(BufferedImage img) {
		this.img = img;
		if(!Tracer_App.headless) {
			pane.repaint();
		}
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	
	
}
