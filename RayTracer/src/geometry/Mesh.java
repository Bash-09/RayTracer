package geometry;

import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4d;

import data.ShadeRec;
import data.Texture;
import io.ObjImporter;
import rays.Ray;

public class Mesh extends Prop{

	protected Triangle[] tris;
	protected BoundingBox box = new BoundingBox();
	
	private Triangle[] transTris;
	
	public Vector3f rot = new Vector3f();
	public float scale = 1;
	
	protected Texture tex;
	public boolean textured = false;
	
	public Texture getTexture() {
		return tex;
	}
	
	//Translate, Rotate, then scale
	
	@Override
	public void setup(String[] commands) {
		
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");

			switch(com[0]) {
			case "mat":
				materialName = com[1];
				break;
			case "obj":
				tris = ObjImporter.importObject(com[1]);
				break;
			case "pos":
				String[] vals = com[1].split(":");
				pos.x = Float.parseFloat(vals[0]);
				pos.y = Float.parseFloat(vals[1]);
				pos.z = Float.parseFloat(vals[2]);
				break;
			case "rot":
				String[] vals2 = com[1].split(":");
				rot.x = Float.parseFloat(vals2[0]);
				rot.y = Float.parseFloat(vals2[1]);
				rot.z = Float.parseFloat(vals2[2]);
				break;
			case "scale":
				scale = Float.parseFloat(com[1]);
				break;
				
			}
		}
		transform();
	}
	
	public void transform() {
		Matrix4f transform = new Matrix4f();
		transform.translate(pos);
		transform.rotateXYZ(rot);
		transform.scale(scale);
		
		transform(transform);
	}
	
	private void transform(Matrix4f tran) {
		transTris = new Triangle[tris.length];
		for(int i = 0; i < tris.length; i++) {
			Triangle it = new Triangle(this);
			Triangle that = tris[i];
			
			it.p1 = transVec(tran, that.p1);
			it.p2 = transVec(tran, that.p2);
			it.p3 = transVec(tran, that.p3);
			it.tc1 = that.tc1;
			it.tc2 = that.tc2;
			it.tc3 = that.tc3;
			
			it.mat = that.mat;
			it.calcNormal();
			
			
			transTris[i] = it;
		}
		
		createBB();
	}
	
	private Vector3d transVec(Matrix4f tran, Vector3d vec) {
		Vector4d temp = new Vector4d(vec, 1);
		temp.mul(tran);
		Vector3d out = new Vector3d(temp.x, temp.y, temp.z);
		return out;
	}
	
	protected void createBB() {
		double lx=0, hx=0, ly=0, hy=0, lz=0, hz = 0;
		boolean first = true;
		for(Triangle tri : transTris) {
			if(first) {
				lx = tri.p1.x;
				hx = lx;
				ly = tri.p1.y;
				hy = ly;
				lz = tri.p1.z;
				hz = lz;
			}
			
			first = false;
			
			if(tri.p1.x < lx) {
				lx = tri.p1.x;
			}
			if(tri.p1.x > hx) {
				hx = tri.p1.x;
			}
			
			if(tri.p1.y < ly) {
				ly = tri.p1.y;
			}
			if(tri.p1.y > hy) {
				hy = tri.p1.y;
			}
			
			if(tri.p1.z < lz) {
				lz = tri.p1.z;
			}
			if(tri.p1.z > hz) {
				hz = tri.p1.z;
			}
			
			
			
			if(tri.p2.x < lx) {
				lx = tri.p2.x;
			}
			if(tri.p2.x > hx) {
				hx = tri.p2.x;
			}
			
			if(tri.p2.y < ly) {
				ly = tri.p2.y;
			}
			if(tri.p2.y > hy) {
				hy = tri.p2.y;
			}
			
			if(tri.p2.z < lz) {
				lz = tri.p2.z;
			}
			if(tri.p2.z > hz) {
				hz = tri.p2.z;
			}
			
			
			
			
			if(tri.p3.x < lx) {
				lx = tri.p3.x;
			}
			if(tri.p3.x > hx) {
				hx = tri.p3.x;
			}
			
			if(tri.p3.y < ly) {
				ly = tri.p3.y;
			}
			if(tri.p3.y > hy) {
				hy = tri.p3.y;
			}
			
			if(tri.p3.z < lz) {
				lz = tri.p3.z;
			}
			if(tri.p3.z > hz) {
				hz = tri.p3.z;
			}
		}
		
		box.p1 = new Vector3d(lx, ly, lz);
		box.p2 = new Vector3d(hx, hy, hz);
		
	}

	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {
		
		if(!box.inBounds(ray)) {
			return record;
		}
		
		for(Triangle i : transTris) {
			i.trace(ray, record);
		}
		
		return record;
	}

}
