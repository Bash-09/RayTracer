package geometry;

import java.awt.Color;

import org.joml.Vector3f;

import data.ShadeRec;
import rays.Ray;
import utils.Colour;

public abstract class Prop {

	public Vector3f pos = new Vector3f(0, 0, 0);
	
	public Vector3f col = new Vector3f(0.5f, 0.5f, 0.5f);
	
	public void setColour(float r, float g, float b) {
		col = Colour.col(r, g, b);
	}
	public void setColour(Vector3f col) {
		this.col = Colour.col(col);
	}
	
	public Color getCol() {
		return new Color(col.x, col.y, col.z);
	}
	
	public abstract ShadeRec trace(Ray ray, ShadeRec record);
	
}
