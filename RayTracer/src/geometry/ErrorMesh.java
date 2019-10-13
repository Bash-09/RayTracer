package geometry;

import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import io.TextureIn;
import materials.Material;

public class ErrorMesh extends Mesh{

	Material errorMat = new Material();
	
	public ErrorMesh() {
		
		errorMat.col = new Vector3f(1, 0, 0);
		errorMat.reflection = true;
		errorMat.reflectiveFactor = 0.3f;
		errorMat.specularFactor = 20;
		
		Triangle tri1 = new Triangle(this);
		tri1.p1 = new Vector3d(1, -1, 1);
		tri1.p2 = new Vector3d(-1, -1, 1);
		tri1.p3 = new Vector3d(-1, -1, -1);
		tri1.tc1 = new Vector2f(0.515829f, 0.258220f);
		tri1.tc2 = new Vector2f(0.515829f, 0.750612f);
		tri1.tc3 = new Vector2f(0.023438f, 0.750612f);

		Triangle tri2 = new Triangle(this);
		tri2.p1 = new Vector3d(1, -1, -1);
		tri2.p2 = new Vector3d(0, 1, 0);
		tri2.p3 = new Vector3d(1, -1, 1);
		tri2.tc1 = new Vector2f(0.370823f, 0.790246f);
		tri2.tc2 = new Vector2f(0.820312f, 0.388210f);
		tri2.tc3 = new Vector2f(0.820312f, 0.991264f);

		Triangle tri3 = new Triangle(this);
		tri3.p1 = new Vector3d(1, -1, 1);
		tri3.p2 = new Vector3d(0, 1, 0);
		tri3.p3 = new Vector3d(-1, -1, 1);
		tri3.tc1 = new Vector2f(0.566135f, 0.988689f);
		tri3.tc2 = new Vector2f(0.015625f, 0.742493f);
		tri3.tc3 = new Vector2f(0.566135f, 0.496298f);

		Triangle tri4 = new Triangle(this);
		tri4.p1 = new Vector3d(-1, -1, 1);
		tri4.p2 = new Vector3d(0, 1, 0);
		tri4.p3 = new Vector3d(-1, -1, -1);
		tri4.tc1 = new Vector2f(0.566135f, 0.496298f);
		tri4.tc2 = new Vector2f(0.015625f, 0.250102f);
		tri4.tc3 = new Vector2f(0.566135f, 0.003906f);

		Triangle tri5 = new Triangle(this);
		tri5.p1 = new Vector3d(0, 1, 0);
		tri5.p2 = new Vector3d(1, -1, -1);
		tri5.p3 = new Vector3d(-1, -1, -1);
		tri5.tc1 = new Vector2f(1, 0);
		tri5.tc2 = new Vector2f(1, 0.603054f);
		tri5.tc3 = new Vector2f(0.550510f, 0.402036f);

		Triangle tri6 = new Triangle(this);
		tri6.p1 = new Vector3d(1, -1, -1);
		tri6.p2 = new Vector3d(1, -1, 1);
		tri6.p3 = new Vector3d(-1, -1, -1);
		tri6.tc1 = new Vector2f(0.023438f, 0.258220f);
		tri6.tc2 = new Vector2f(0.515829f, 0.258220f);
		tri6.tc3 = new Vector2f(0.023438f, 0.750612f);

		tri1.mat = errorMat;
		tri2.mat = errorMat;
		tri3.mat = errorMat;
		tri4.mat = errorMat;
		tri5.mat = errorMat;
		tri6.mat = errorMat;
		
		tris = new Triangle[6];
		
		tris[0] = tri1;
		tris[1] = tri2;
		tris[2] = tri3;
		tris[3] = tri4;
		tris[4] = tri5;
		tris[5] = tri6;

		textured = true;
		tex = TextureIn.readTexture("castleroof.jpg");
		TextureIn.addNormalMap(tex, "castleroofNORMAL.jpg");
		
		transform();
	}
	
}
