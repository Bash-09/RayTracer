package main;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Painter {

	private JPanel pane;
	
	private BufferedImage img;
	
	public Painter(JPanel pane) {
		this.pane = pane;
	}
	
	public void repaint(BufferedImage img) {
		this.img = img;
		pane.repaint();
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
}
