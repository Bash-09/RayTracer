package canvas;

import java.awt.image.BufferedImage;

import org.joml.Vector3f;

import main.Painter;

public class Camera {

	private ViewingPlane view = new ViewingPlane();
	
	private Painter painter;
	
	public void setPaint(Painter paint) {
		painter = paint;
	}
	
	public Vector3f pos = new Vector3f(0, 0, 0);
	public Vector3f direction = new Vector3f(0, 0, 0);
	
	public ViewingPlane getView() {
		return view;
	}
	
	public void repaint(BufferedImage img) {
		painter.repaint(img);
	}
}
