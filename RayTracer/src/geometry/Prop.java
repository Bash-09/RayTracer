package geometry;

import org.joml.Vector3d;
import org.joml.Vector3f;

import data.ShadeRec;
import rays.Ray;
import utils.Colour;

public abstract class Prop {

	public boolean mirror = false;
	
	public Vector3f pos = new Vector3f(0, 0, 0);
	
	public Vector3f col = new Vector3f(0.5f, 0.5f, 0.5f);
	
	public void setColour(float r, float g, float b) {
		col = Colour.col(r, g, b);
	}
	public void setColour(Vector3f col) {
		this.col = Colour.col(col);
	}
	
	public Vector3f getCol() {
		return new Vector3f(col.x, col.y, col.z);
	}
	
	public abstract ShadeRec trace(Ray ray, ShadeRec record);
	public abstract Vector3d getNormal(Vector3d point);
	
}
