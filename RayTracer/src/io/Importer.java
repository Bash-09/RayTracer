package io;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Importer {

	ObjectMapper mapper = new ObjectMapper();
	
	public Importer() {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	FileImporter reader = new FileImporter();
	
	public void importFile(String filename) throws IOException {
		reader.readFile(filename);
	}
	
}
