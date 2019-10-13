package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Tracer_App {

	public static final boolean headless = false;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Window window = new Window();
		
		if(!headless) {
			frame.add(window);
			window.setPreferredSize(new Dimension(600, 600));
			frame.pack();
			frame.setVisible(true);
		}

		
		//window.exportMaterial("Format");
		
		window.init();
		window.render();
		//window.exportImage("Pyramid");
		

		//window.exportFrames("NormalMap");
		
	}
	
}
