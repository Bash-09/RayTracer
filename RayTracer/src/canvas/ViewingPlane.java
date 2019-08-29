package canvas;

import org.joml.Vector2i;

public class ViewingPlane {

	//Size in world
	public float w = 5;
	public float h = 5;
	//Resolution of screen
	public int w_r = 10;
	public int h_r = 10;
		
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
	
	
}
