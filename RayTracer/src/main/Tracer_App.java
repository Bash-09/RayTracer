package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Tracer_App {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Window window = new Window();
		
		frame.add(window);
		
		window.setPreferredSize(new Dimension(600, 600));
		frame.pack();
		window.init();
		frame.setVisible(true);
		
		window.render();
		
	}
	
}
