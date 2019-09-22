package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileImporter {

	BufferedReader reader;
	
	public void readFile(String filename) throws IOException {
		File file = new File("resources/"+filename);
		
		reader = new BufferedReader(new FileReader(file));
		
		
		String source = "";
		String out = "";
		while(out != null) {
			out = reader.readLine();
			source += out;
		}
		
		char[] chars = source.toCharArray();
		
		parseSource(chars);
		
		
	}
	
	private void parseSource(char[] chars) {
		
		boolean inTopic = false;
		String topic = "";
		String source = "";
		
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ' ') {
				continue;
			}
			
			
			switch(chars[i]) {
			case '{':
				inTopic = true;
				break;
			case '}':
				inTopic = false;
				//Parse topic
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
	}
	
}
