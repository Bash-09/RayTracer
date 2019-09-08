package lights;

import org.joml.Vector3d;

import rays.Ray;
import samplers.Sample;

public class DirectionalLight extends Light{

	@Override
	public Ray getLightSampleRay(Sample samp, Vector3d point) {
		Ray out = new Ray(point.x, point.y, point.z, -dir.x, -dir.y, -dir.z);
		return out;
	}

}
