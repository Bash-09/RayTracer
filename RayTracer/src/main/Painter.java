package main;

import javax.swing.JPanel;

public class Painter {

	private JPanel pane;
	
	public Painter(JPanel pane) {
		this.pane = pane;
	}
	
	public void repaint() {
		pane.repaint();
	}
	
}
