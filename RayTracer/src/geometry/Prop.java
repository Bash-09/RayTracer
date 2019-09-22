package geometry;

import org.joml.Vector3d;
import org.joml.Vector3f;

import data.ShadeRec;
import materials.Material;
import rays.Ray;
import utils.Colour;
import utils.ID;

public abstract class Prop {

	public String name;
	
	public Prop(Material mat) {
		this.mat = mat;
	}
	public Prop() {
		name = Integer.toString(ID.getID());
		mat = new Material();
	}
	
	public Prop(String name) {
		this.name = name;
		mat = new Material();
	}
	
	public Material mat;
	
	public Vector3f pos = new Vector3f(0, 0, 0);
	
	public abstract ShadeRec trace(Ray ray, ShadeRec record);
	public abstract Vector3d getNormal(Vector3d point);
	
}
