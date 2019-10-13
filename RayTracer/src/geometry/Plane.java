package geometry;

import org.joml.Vector3d;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Plane extends Prop{

	public Vector3d normal = new Vector3d(0, 1, 0);

	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {

		double nDotRd = normal.x*ray.direction.x + normal.y*ray.direction.y + normal.z*ray.direction.z;
		
		if(nDotRd == 0) {
			return record;
		}
		
		double nDotP = normal.x*pos.x + normal.y*pos.y + normal.z*pos.z;
		double nDotRo = normal.x*ray.origin.x + normal.y*ray.origin.y + normal.z*ray.origin.z;
		
		record.addCollision(new Collision(this, ray, (nDotP - nDotRo)/nDotRd, normal, null));

		return record;
	}

	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");
			
			switch(com[0]) {
			case "mat":
				materialName = com[1];
				break;
			case "pos":
				String[] vals = com[1].split(":");
				pos.x = Float.parseFloat(vals[0]);
				pos.y = Float.parseFloat(vals[1]);
				pos.z = Float.parseFloat(vals[2]);
				break;
			case "normal":
				String[] vals2 = com[1].split(":");
				normal.x = Float.parseFloat(vals2[0]);
				normal.y = Float.parseFloat(vals2[1]);
				normal.z = Float.parseFloat(vals2[2]);
				break;
			}
		}
	}
	
}
