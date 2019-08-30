package canvas;

import java.awt.image.BufferedImage;

import org.joml.Vector3f;

import main.Painter;
import rays.Ray;

public class Camera {
	
	public Ray getRay(int i, int j) {
		
		//Static viewPlane, can only move camera, not turn
		float x = pos.x - view.w/2 + i*view.xs + view.xs*((float)Math.random());
		float y = pos.y - view.h/2 + j*view.ys + view.ys*((float)Math.random());
		float z = pos.z;
		
		return new Ray(x, y, z, 0, 0, 1);
		
	}

	protected ViewingPlane view = new ViewingPlane();
	
	protected Painter painter;
	
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
