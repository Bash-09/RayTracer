package utils;

import java.awt.Color;

import org.joml.Vector3f;

public class Colour {

	public static Vector3f addColour(float r, float g, float b, float r2, float g2, float b2) {
		r += r2;
		g += g2;
		b += b2;
		return col(r, g, b);
	}
	
	
	public static Vector3f mixColour(float r, float g, float b, float scale, float r2, float g2, float b2) {
		float or = r;
		float og = g;
		float ob = b;
		
		or *= scale;
		og *= scale;
		ob *= scale;
		
		float invScale = 1-scale;
		or += invScale*r2;
		og += invScale*g2;
		ob += invScale*b2;
		
		return col(or, og, ob);
	}
	
	public static Vector3f mixColour(Vector3f a, float scale, Vector3f b) {
		return mixColour(a.x, a.y, a.z, scale, b.x, b.y, b.z);
	}
	
	
	public static Vector3f col(float r, float g, float b) {
		return new Vector3f(Math.max(0, Math.min(1, r)), Math.max(0, Math.min(1, g)), Math.max(0, Math.min(1, b)));
	}
	public static Vector3f col(Vector3f col) {
		return col(col.x, col.y, col.z);
	}
	
	public static Color getCol(Vector3f colIn) {
		Vector3f col = col(colIn);
		return new Color(col.x, col.y, col.z);
	}
	
	public static Vector3f combine(Vector3f in, Vector3f in2) {
		return new Vector3f(in.x*in2.x, in.y*in2.y, in.z*in2.z);
	}
	public static Vector3f combine(float r1, float g1, float b1, float r2, float b2, float g2) {
		return combine(new Vector3f(r1, g1, b1), new Vector3f(r2, b2, g2));
	}
	
}
