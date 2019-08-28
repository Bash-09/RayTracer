package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Tracer_App {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		Window window = new Window();
		
		frame.add(window);
		
		window.setPreferredSize(new Dimension(600, 600));
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
}
