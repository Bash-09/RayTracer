package canvas;

public class ViewingPlane {

	//Size in world
	public float w = 3;
	public float h = 3;
	//Resolution of screen
	public int w_r = 10000;
	public int h_r = 10000;
	
	public float xs = w/w_r;
	public float ys = h/h_r;
		
	public ViewingPlane() {
		
	}
	
	public ViewingPlane(float w, float h) {
		this.w = w;
		this.h = h;
		
		xs = w/w_r;
		ys = h/h_r;
	}
	
	public ViewingPlane(float w, float h, int w_r, int h_r) {
		this.w = w;
		this.h = h;
		
		this.w_r = w_r;
		this.h_r = h_r;
		
		xs = w/w_r;
		ys = h/h_r;
	}
	
	
}
