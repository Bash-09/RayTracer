package entities;

import org.joml.Vector3f;

import geometry.Sphere;

public class Light extends Sphere{

	public Vector3f col = new Vector3f(1, 1, 1);

	public Light() {
		super(1f);
	}
	
}
