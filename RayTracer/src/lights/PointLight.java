package lights;

import org.joml.Vector3d;
import org.joml.Vector3f;

import rays.Ray;
import samplers.Sample;

public class PointLight extends Light{

	@Override
	public Ray getLightSampleRay(Sample samp, Vector3d point) {
		Vector3d dif = new Vector3d(dir.x - point.x, dir.y - point.y, dir.z - point.z);
		Ray out = new Ray(point.x, point.y, point.z, dif.x, dif.y, dif.z);
		
		out.t = dif.length();
		out.hasLength = true;
		
		return out;
	}

	@Override
	public Vector3f getCol(Ray toLight) {
		
		double falloff = 250 * intensity / (4*Math.PI*toLight.t);
		
		Vector3f outCol = new Vector3f(col);
		outCol.mul((float)falloff);
		
		return outCol;
	}

}
