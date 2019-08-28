package canvas;

import org.joml.Vector2i;

public class ViewingPlane {

	private float w = 10;
	private float h = 10;
	
	private int w_r = 10;
	private int h_r = 10;
		
	public ViewingPlane() {
		
	}
	
	public ViewingPlane(float w, float h) {
		this.w = w;
		this.h = h;
	}
	
	public ViewingPlane(float w, float h, int w_r, int h_r) {
		this.w = w;
		this.h = h;
		
		this.w_r = w_r;
		this.h_r = h_r;
	}
	
	public Vector2i getRes() {
		return new Vector2i(w_r, h_r);
	}
	
}
