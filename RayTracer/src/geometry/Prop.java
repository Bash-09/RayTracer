package geometry;

import org.joml.Vector3f;

import data.ShadeRec;
import io.Settings;
import materials.Material;
import rays.Ray;
import utils.ID;

public abstract class Prop implements Settings{

	public String name;
	public String materialName;
	
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
	
}
