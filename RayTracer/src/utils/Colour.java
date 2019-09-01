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
	
	
}
