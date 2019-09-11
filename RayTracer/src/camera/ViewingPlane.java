package camera;

public class ViewingPlane {

	//Size in world
	public float w = 1f;
	public float h = 1f;
	//Resolution of screen
	public int w_r = 400;
	public int h_r = 400;
	
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
