package lights;

import org.joml.Vector3f;

import geometry.Sphere;

public class Light extends Sphere{

	public LightType type = LightType.point;
	
	public Vector3f col = new Vector3f(1, 1, 1);

	public Light() {
		super(1f);
	}
	public Light(float rad) {
		super(rad);
	}
	
}
