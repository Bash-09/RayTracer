package canvas;

import org.joml.Vector3f;

public class Camera {

	private ViewingPlane view;
	
	private Vector3f pos = new Vector3f(0, 0, 0);
	private Vector3f rotation = new Vector3f(0, 0, 0);
	
	public ViewingPlane getPlane() {
		return view;
	}
}
