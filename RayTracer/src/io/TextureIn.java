package io;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import data.Texture;

public class TextureIn {

	public static Texture readTexture(String filename) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File("resources/textures/"+filename));
		} catch(Exception e) {
			System.out.println("Error reading texture file: " + filename);
			System.exit(0);
		}
		
		Texture tex = new Texture();
		tex.setTexture(img);
		
		return tex;
	}
	
	public static void addNormalMap(Texture tex, String filename) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File("resources/textures/"+filename));
		} catch(Exception e) {
			System.out.println("Error reading texture file: " + filename);
			System.exit(0);
		}
		
		tex.setNormalMap(img);
		
		
	}
	
}
