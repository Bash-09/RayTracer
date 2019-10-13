package data;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Texture {

	private BufferedImage tex;
	private BufferedImage normalMap = null;
	private boolean hasNormalMap = false;
	
	public void setTexture(BufferedImage tex) {
		this.tex = tex;
	}
	public void setNormalMap(BufferedImage normalMap) {
		this.normalMap = normalMap;
		hasNormalMap = true;
	}
	
	public boolean hasNormalMap() {
		return hasNormalMap;
	}
	
	public Vector3f getCol(float tx, float ty) {
		int y = (int)(tx * (float)tex.getWidth());
		int x = (int)(ty * (float)tex.getHeight());
		
		x*= 2;
		y*= 2;
		
		x %= tex.getWidth();
		y %= tex.getHeight();
		
		Color col = new Color(tex.getRGB(x, y));
		
		Vector3f outCol = new Vector3f();
		outCol.x = col.getRed();
		outCol.y = col.getGreen();
		outCol.z = col.getBlue();
		
		outCol.div(255);
		
		return outCol;
	}
	
	public Vector3d getNormal(float tx, float ty) {
		int y = (int)(tx * (float)normalMap.getWidth());
		int x = (int)(ty * (float)normalMap.getHeight());
		
		x*= 2;
		y*= 2;
		
		x %= normalMap.getWidth();
		y %= normalMap.getHeight();
		
		Color col = Color.white;
		
		try {
			col = new Color(normalMap.getRGB(x, y));
		} catch (Exception e) {
			System.out.println(tx + ", " + ty);
		}
		
		Vector3d outCol = new Vector3d();
		outCol.x = col.getRed();
		outCol.y = col.getGreen();
		outCol.z = col.getBlue();
		
		outCol.div(255);
		
		return outCol;
	}
	
}
