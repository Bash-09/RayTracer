package canvas;

import org.joml.Vector3d;
import org.joml.Vector3f;

import rays.Ray;

public class PinHoleCamera extends Camera{

	double distance;
	double fov;
	
	private Vector3d viewX;
	private Vector3d viewZ = new Vector3d(0, 0, 1); //Also the direction vector of the camera
	private Vector3d viewY;

	public PinHoleCamera(double fov) {	
		this.fov = fov;
		init();
	}
	
	private void init() {
		double angleB = (180 - fov)/2;
		double lengthB = (view.w*Math.sin(Math.toRadians(angleB)))/Math.sin(Math.toRadians(fov));
		distance = Math.sqrt(lengthB*lengthB - (view.w/2)*(view.w/2));
		
		viewX = new Vector3d();
		viewY = new Vector3d();
		
		viewX.y = 0;
		viewX.x = -viewZ.z;
		viewX.z = viewZ.x;

		viewX.normalize();
		
		
		viewY.x = viewZ.y * viewX.z;
		viewY.y = viewZ.z * viewX.x;
		viewY.z = viewZ.x * viewX.y;
		
		viewY.normalize();
		
		
		
	}
	
	public Ray getRay(double i, double j) {
		Ray ray = new Ray();
		ray.origin = new Vector3d(this.pos);
		
		
		Vector3d origin = new Vector3d();
		
		//Get center of view window
		Vector3d viewZ = new Vector3d(this.viewZ);
		viewZ.mul(distance);
		viewZ.add(pos);
		Vector3d viewX = new Vector3d(this.viewX);
		Vector3d viewY = new Vector3d(this.viewY);
		
		double x = -view.w/2 + i*view.xs +view.xs/2;
		double y = -view.h/2 + j*view.ys + view.ys/2;
		viewX.mul(x);
		viewY.mul(y);
		
		origin.add(viewX);
		origin.add(viewY);
		origin.add(viewZ);
		
		Vector3d dir = new Vector3d();
		dir.x = origin.x - pos.x;
		dir.y = origin.y - pos.y;
		dir.z = origin.z - pos.z;
		dir.normalize();
		
		ray.direction = dir;
		return ray;
	}
	
	public void setDirection(double x, double y, double z) {
		viewZ = new Vector3d(x, y, z);
		viewZ.normalize();
		
		init();
	}
	public void setDirection(Vector3d in) {
		setDirection(in.x, in.y, in.z);
	}
	
}
