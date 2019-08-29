package geometry;

import java.awt.Color;

import org.joml.Vector3f;

import data.ShadeRec;
import rays.Ray;

public abstract class Prop {

	public Vector3f pos = new Vector3f(0, 0, 0);
	
	public Color col = Color.BLACK;	
	public Color getCol() {
		return col;
	}
	
	public abstract ShadeRec trace(Ray ray, ShadeRec record);
	
}
