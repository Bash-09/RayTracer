package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import camera.PinHoleCamera;
import geometry.Plane;
import geometry.Prop;
import geometry.Sphere;
import lights.DirectionalLight;
import lights.Light;
import lights.PointLight;
import materials.Material;
import world.Scene;

public class FileImporter {

	private BufferedReader reader;
	
	private HashMap<String, Material> materials;
	
	public Scene readFile(String filename) throws IOException {
		Scene scene = new Scene();
		materials = new HashMap<>();
		
		File file = new File("resources/"+filename);
		
		reader = new BufferedReader(new FileReader(file));
		
		
		String source = "";
		String out = "";
		while(out != null) {
			out = reader.readLine();
			source += out;
		}
		
		char[] chars = source.toCharArray();
		
		scene = parseSource(chars, scene);
		
		return scene;
	}
	
	private Scene parseSource(char[] chars, Scene scene) {
		
		boolean inTopic = false;
		String topic = "";
		String source = "";
		int depth = 0;
		
		String[] sceneSrc = null;
		String[] matSrc = null;
		String[] camSrc = null;
		String[] propSrc = null;
		String[] lightSrc = null;
		
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ' ') {
				continue;
			}
			
			//Split the source into topics by character
			switch(chars[i]) {
			//Opening of a topic
			case '{':
				depth++;
				//Exclude internal braces (like for json data)
				if(inTopic) {
					source += chars[i];
					break;
				}
				//Open topic
				inTopic = true;
				break;
			case '}':
				depth--;
				//Check if it's still in a topic
				if(depth != 0) {
					source += chars[i];
					break;
				}
				//Exit topic
				inTopic = false;
				
				String[] commands = source.split(";");
				//Put in the right topic source
				switch(topic) {
				case "scene":
					sceneSrc = commands;
					break;
				case "materials":
					matSrc = commands;
					break;
				case "cameras":
					camSrc = commands;
					break;
				case "props":
					propSrc = commands;
					break;
				case "lights":
					lightSrc = commands;
				
				}
				//Reset source stuff
				source = "";
				topic = "";
				break;
			default:
				if(inTopic) {
					source += chars[i];
				} else {
					topic += chars[i];
				}
				break;
			}
			
		}
		
		//Parse topic sources
		parseScene(sceneSrc, scene);
		parseMaterials(matSrc, scene);
		parseCameras(camSrc, scene);
		parseProps(propSrc, scene);
		parseLights(lightSrc, scene);
		
		return scene;
	}
	
	
	private void parseScene(String[] source, Scene scene) {
		scene.setup(source);
	}
	
	private String[] until(String in, char delimiter) {
		char[] chars = in.toCharArray();
		return until(chars, delimiter);
	}
	
	private String[] until(char[] chars, char delimiter) {
		String[] out = new String[2];
		out[0] = "";
		out[1] = "";
		boolean first = true;
		for(int i = 0; i < chars.length; i++) {
			if(first) {
				if(chars[i] != delimiter) {
					out[0] += chars[i];
				} else {
					first = false;
				}
			} else {
				out[1] += chars[i];
			}
		}
		return out;
	}
	
	private String[] getBracks(String in) {
		char[] chars = in.toCharArray();
		char[] chars2 = new char[chars.length-1];
		
		for(int i = 0; i < chars2.length; i++) {
			chars2[i] = chars[i];
		}
		
		return until(chars2, '(');
	}
	
	private String[] parseCommand(String source) {
		String[] com = until(source, '=');
		String name = com[0];
		
		String[] settings = getBracks(com[1]);
		String importType = settings[0];
		String importSettings = settings[1];
		
		String[] object = new String[3];
		object[0] = name;
		object[1] = importType;
		object[2] = importSettings;
		
		return object;
	}
	
	private void parseMaterials(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			String[] command = parseCommand(source[i]);
			
			if(command[1].equals("new")) {
				
				Material mat = new Material();
				String[] settings = command[2].split(",");
				mat.setup(settings);
				materials.put(command[0], mat);
				
			} else if(command[1].equals("json")) {
				
			} else if(command[1].equals("url")) {
				
			}
		}
	}
	
	private void parseCameras(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			String[] command = parseCommand(source[i]);
			
			if(command[1].equals("new")) {
				PinHoleCamera cam = new PinHoleCamera();
				String[] settings = command[2].split(",");
				cam.setup(settings);
				cam.name = command[0];
				System.out.println(cam.name);
				scene.addCamera(cam);
				
			} else if(command[1].equals("json")) {
				
			} else if(command[1].equals("url")) {
				
			}
		}
	}
	
	private void parseProps(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			
			String[] command = parseCommand(source[i]);
			
			if(command[1].equals("new")) {
				
				Prop prop;
				
				String[] typeName = command[0].split("-");
				
				switch(typeName[0]) {
				case "Sphere":
					prop = new Sphere();
					break;
				case "Plane":
					prop = new Plane();
					break;
				default:
						return;
				}
				
				prop.setup(command[2].split(","));
				prop.mat = materials.get(prop.materialName);
				scene.addObject(prop);
				
			} else if(command[1].equals("json")) {
				
			} else if(command[1].equals("url")) {
				
			}
			
		}
	}
	
	private void parseLights(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			
			String[] command = parseCommand(source[i]);
			
			if(command[1].equals("new")) {
				
				Light light;
				
				String[] typeName = command[0].split("-");
				
				switch(typeName[0]) {
				case "Directional":
					light = new DirectionalLight();
					break;
				case "Point":
					light = new PointLight();
					break;
				default:
						return;
				}
				
				light.setup(command[1].split(","));
				scene.addLight(light);
				
			} else if(command[1].equals("json")) {
				
			} else if(command[1].equals("url")) {
				
			}
			
		}
	}
	
}
