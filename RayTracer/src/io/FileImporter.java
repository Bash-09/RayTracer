package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Vector3f;

import world.Scene;

public class FileImporter {

	private BufferedReader reader;
	private TextParser parser = new TextParser();
	
	public Scene readFile(String filename) throws IOException {
		Scene scene = new Scene();
		
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
		
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ' ') {
				continue;
			}
			
			
			switch(chars[i]) {
			case '{':
				depth++;
				if(inTopic) {
					source += chars[i];
					break;
				}
				inTopic = true;
				break;
			case '}':
				depth--;
				if(depth != 0) {
					source += chars[i];
					break;
				}
				inTopic = false;
				
				String[] commands = source.split(";");
				
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
				
				}
				
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
		
		parseScene(sceneSrc, scene);
		parseMaterials(matSrc, scene);
		parseCameras(camSrc, scene);
		parseProps(propSrc, scene);
		
		return scene;
	}
	
	
	private void parseScene(String[] source, Scene scene) {
		scene.setup(source);
	}
	
	
	private void parseMaterials(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			
			
			
		}
	}
	
	private void parseCameras(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			
			
			
		}
	}
	
	private void parseProps(String[] source, Scene scene) {
		for(int i = 0; i < source.length; i++) {
			
			
			
		}
	}
	
}
