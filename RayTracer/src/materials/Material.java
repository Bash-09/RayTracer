package materials;

import org.joml.Vector3f;

import io.Settings;

public class Material implements Settings{

	//Spread of specular
	public float specularFactor = 15;
	public float specularReflection = 1;
	
	public Vector3f col = new Vector3f(1, 1, 1);
	
	public boolean reflection = false;
	public float reflectiveFactor = 0.2f;
	
	//Amount of light reflected
	public float albedo = 0.18f;

	@Override
	public void setup(String[] commands) {
		for(int i = 0; i < commands.length; i++) {
			
			String[] com = commands[i].split("=");
			
			switch(com[0]) {
			case "reflection":
				if(com[1].equals("true")) {
					reflection = true;
				}
				break;
			case "specularFactor":
				specularFactor = Float.parseFloat(com[1]);
				break;
			case "reflectiveFactor":
				reflectiveFactor = Float.parseFloat(com[1]);
				break;
			case "albedo":
				albedo = Float.parseFloat(com[1]);
				break;
			case "specularReflection":
				specularReflection = Float.parseFloat(com[1]);
				break;
			case "col":
				String[] vals = com[1].split(":");
				col = new Vector3f();
				col.x = Float.parseFloat(vals[0]);
				col.y = Float.parseFloat(vals[1]);
				col.z = Float.parseFloat(vals[2]);
				break;
				
			}
			
		}
	}
	
}
