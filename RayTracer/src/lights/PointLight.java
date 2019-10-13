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
		
		double falloff = 150 * intensity / (4*Math.PI*toLight.t);
		//double falloff = 1;
		
		Vector3f outCol = new Vector3f(col);
		outCol.mul((float)falloff);
		
		return outCol;
	}

	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");
			
			switch(com[0]) {
			case "intensity":
				intensity = Float.parseFloat(com[1]);
				break;
			case "pos":
				String[] vals = com[1].split(":");
				dir.x = Float.parseFloat(vals[0]);
				dir.y = Float.parseFloat(vals[1]);
				dir.z = Float.parseFloat(vals[2]);
				break;
			case "col":
				String[] vals2 = com[1].split(":");
				col.x = Float.parseFloat(vals2[0]);
				col.y = Float.parseFloat(vals2[1]);
				col.z = Float.parseFloat(vals2[2]);
				break;
			}
		}
	}

}
