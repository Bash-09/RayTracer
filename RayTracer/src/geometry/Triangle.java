package geometry;

import org.joml.Matrix3d;
import org.joml.Vector2f;
import org.joml.Vector3d;

import data.Collision;
import data.ShadeRec;
import rays.Ray;

public class Triangle extends Prop{

	private Mesh parent;
	
	private Vector3d normal = new Vector3d();
	
	protected Vector3d p1 = new Vector3d(), p2 = new Vector3d(), p3 = new Vector3d();
	protected Vector2f tc1 = new Vector2f(), tc2 = new Vector2f(), tc3 = new Vector2f();
	
	public Triangle() {
		
	}
	public Triangle(Mesh parent) {
		this.parent = parent;
	}
	
	public Mesh getMesh() {
		return parent;
	}
	
	public void setPoints(Vector3d p1, Vector3d p2, Vector3d p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		calcNormal();
	}
	
	public void calcNormal() {
		Vector3d bMa = new Vector3d(p2);
		bMa.sub(p1);
		bMa.normalize();
		Vector3d cMa = new Vector3d(p3);
		cMa.sub(p1);
		cMa.normalize();
		
		normal = new Vector3d(bMa);
		normal.cross(cMa);
		normal.normalize();
	}
	
	public void setTextureCoords(Vector2f tc1, Vector2f tc2, Vector2f tc3) {
		this.tc1 = tc1;
		this.tc2 = tc2;
		this.tc3 = tc3;
	}
	
	@Override
	public ShadeRec trace(Ray ray, ShadeRec record) {
		
		double u, v, t;
		
		Vector3d a = new Vector3d();
		a.x = ray.origin.x - p1.x;
		a.y = ray.origin.y - p1.y;
		a.z = ray.origin.z - p1.z;
		
		Vector3d x = new Vector3d();
		
		Vector3d twoMo = new Vector3d(p2);
		twoMo.sub(p1);
		
		Vector3d threeMo = new Vector3d(p3);
		threeMo.sub(p1);
		
		Matrix3d b = new Matrix3d();
		b._m00(twoMo.x);	b._m10(threeMo.x);		b._m20(-ray.direction.x);
		b._m01(twoMo.y);	b._m11(threeMo.y);		b._m21(-ray.direction.y);
		b._m02(twoMo.z);	b._m12(threeMo.z);		b._m22(-ray.direction.z);
		b.invert();

		x = a.mul(b);
		
		u = x.x;
		v = x.y;
		t = x.z;
		
		double w = 1-u-v;
		
		Vector2f texCoords = new Vector2f(new Vector2f(tc1).mul((float)u));
		
		texCoords.add(new Vector2f(tc2).mul((float)v));
		texCoords.add(new Vector2f(tc3).mul((float)w));
		
		if(u >= 0 && v >= 0 && u+v <= 1) {
			Vector3d normalChange = new Vector3d(1);
			if(parent.getTexture().hasNormalMap()) {
				normalChange = parent.getTexture().getNormal(texCoords.x, texCoords.y);
			}
			
			record.addCollision(new Collision(this, ray, t, new Vector3d(normal).mul(normalChange), texCoords));
		}
		
		return record;
	}


	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			String[] com = commands[i].split("=");
			System.out.println(com[0]);

			switch(com[0]) {
			case "mat":
				materialName = com[1];
				break;
			case "p1":
				String[] vals = com[1].split(":");
				p1.x = Float.parseFloat(vals[0]);
				p1.y = Float.parseFloat(vals[1]);
				p1.z = Float.parseFloat(vals[2]);
				break;
			case "p2":
				String[] vals2 = com[1].split(":");
				p2.x = Float.parseFloat(vals2[0]);
				p2.y = Float.parseFloat(vals2[1]);
				p2.z = Float.parseFloat(vals2[2]);
				break;
			case "p3":
				String[] vals3 = com[1].split(":");
				p3.x = Float.parseFloat(vals3[0]);
				p3.y = Float.parseFloat(vals3[1]);
				p3.z = Float.parseFloat(vals3[2]);
				break;
			}
		}
		setPoints(p1, p2, p3);
	}

}
