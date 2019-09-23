package lights;

import org.joml.Vector3d;
import org.joml.Vector3f;

import rays.Ray;
import samplers.Sample;

public class DirectionalLight extends Light{

	@Override
	public Ray getLightSampleRay(Sample samp, Vector3d point) {
		Ray out = new Ray(point.x, point.y, point.z, -dir.x, -dir.y, -dir.z);
		return out;
	}

	@Override
	public Vector3f getCol(Ray toLight) {
		return col;
	}

	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");
			
			switch(com[0]) {
			case "intensity":
				intensity = Float.parseFloat(com[1]);
				break;
			case "dir":
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
