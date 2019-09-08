package utils;

import org.joml.Vector3d;

public class Space {

	public static Vector3d[] createBasis(Vector3d normal) {
		
		normal.normalize();
		
		Vector3d[] out = new Vector3d[3];
		
		Vector3d uVec = new Vector3d();
		uVec.z = normal.x * 1;
		uVec.x = normal.y * 0;
		uVec.y = normal.z * 0;
		
		Vector3d vVec = new Vector3d();
		vVec.z = normal.x * uVec.y;
		vVec.x = normal.y * uVec.z;
		vVec.y = normal.z * uVec.x;
		
		out[0] = uVec;
		out[1] = vVec;
		out[2] = normal;
		
		return out;
	}
	
}
